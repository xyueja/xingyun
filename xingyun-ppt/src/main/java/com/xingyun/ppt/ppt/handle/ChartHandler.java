package com.xingyun.ppt.ppt.handle;

import com.xingyun.ppt.ppt.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.Units;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFChart;
import org.apache.poi.xslf.usermodel.XSLFGraphicFrame;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.chart.*;

import java.awt.geom.Rectangle2D;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * ChartHandle
 *
 * @author yxuej
 * @since 2026-06-13
 */
@Slf4j
public class ChartHandler {
    private static double fromCM(double cm) {
        return Math.rint(cm * Units.EMU_PER_POINT);
    }

    public static void handle(XMLSlideShow ppt, XSLFSlide slide, XSLFGraphicFrame frameShape, Map<String, SlideConfig> chartConfigs) {
        XSLFChart oldChart = frameShape.getChart();
        String chartTitle = oldChart.getTitleShape().getText();
        SlideConfig slideConfig = chartConfigs.get(chartTitle);
        if (!(slideConfig instanceof ChartData chartData)) {
            return;
        }

        // 记录模板图表位置
        Rectangle2D rect2D = frameShape.getAnchor();

        // 移除之前的图表
        slide.removeShape(frameShape);

        // 创建新的图表
        XSLFChart chart = ppt.createChart(slide);
        rect2D = new Rectangle2D.Double(fromCM(rect2D.getX()), fromCM(rect2D.getY()), fromCM(rect2D.getWidth()), fromCM(rect2D.getHeight()));
        slide.addChart(chart, rect2D);

//        String[] series = chartData.getSeries();
//        String[] categories = chartData.getCategories();
//        Double[] values1 = chartData.getValues()[0];
//        Double[] values2 = chartData.getValues()[1];
//        setBarData(chart, chartTitle, series, categories, values1, values2);
        copyChartStyles(oldChart, chart, chartData);
    }

    private static void copyChartStyles(XSLFChart oldChart, XSLFChart newChart, ChartData chartData) {
        // 复制图表标题
        CTChart oldChartXml = oldChart.getCTChart();
        CTChart newChartXml = newChart.getCTChart();
        newChartXml.setTitle(oldChartXml.getTitle());
//        newChartXml.setPlotArea(oldChartXml.getPlotArea());

        // 复制轴并创建bar
//        newChart.clearChartSeries();
        XDDFBarChartData bar = copyChartAxisAndCreateBar(oldChart, newChart);
        if (bar == null) {
            log.error("bar is null");
            return;
        }

        String[] categories = chartData.getCategories();
        final int numOfPoints = categories.length;

        final String categoryDataRange = newChart.formatRange(new CellRangeAddress(1, numOfPoints, 0, 0));
        final XDDFDataSource<?> categoriesData = XDDFDataSourcesFactory.fromArray(categories, categoryDataRange, 0);

        // 复制数据系列的颜色
        String[] series = chartData.getSeries();
        Double[][] values = chartData.getValues();
        List<XDDFChartData> oldChartData = oldChart.getChartSeries();
        if (CollectionUtils.isNotEmpty(oldChartData) && oldChartData.getFirst() instanceof XDDFBarChartData oldBar) {
            bar.setGapWidth(oldBar.getGapWidth());
            bar.setOverlap(oldBar.getOverlap());
            bar.setBarDirection(oldBar.getBarDirection());
        }

        for (int i = 0; i < series.length; i++) {
            int columnCountries = i + 1;
            final String valuesDataRange = newChart.formatRange(new CellRangeAddress(1, numOfPoints, columnCountries, columnCountries));
            final XDDFNumericalDataSource<? extends Number> valuesData = XDDFDataSourcesFactory.fromArray(values[i], valuesDataRange, columnCountries);
            valuesData.setFormatCode("General");

            XDDFBarChartData.Series seriesData = (XDDFBarChartData.Series) bar.addSeries(categoriesData, valuesData);
            seriesData.setTitle(series[i], newChart.setSheetTitle(series[columnCountries - 1], columnCountries));
        }

        bar.setVaryColors(true);
        newChart.plot(bar);

        // 复制图例
        XDDFChartLegend oldLegend = oldChart.getOrAddLegend();
        XDDFChartLegend newLegend = newChart.getOrAddLegend();
        newLegend.setPosition(oldLegend.getPosition());
        newLegend.setOverlay(oldLegend.isOverlay());
        newLegend.setShapeProperties(oldLegend.getShapeProperties());
    }

    private static XDDFBarChartData copyChartAxisAndCreateBar(XSLFChart oldChart, XSLFChart newChart) {
        XDDFChartAxis newAxis = null;
        XDDFValueAxis newValueAxis = null;
        for (XDDFChartAxis oldAxis : oldChart.getAxes()) {
            AxisPosition axisPosition = oldAxis.getPosition();
            if (axisPosition == AxisPosition.BOTTOM) {
                newAxis = newChart.createCategoryAxis(AxisPosition.BOTTOM);
            }

            if (axisPosition == AxisPosition.TOP) {
                newAxis = newChart.createCategoryAxis(AxisPosition.TOP);
            }

            if (axisPosition == AxisPosition.LEFT) {
                newValueAxis = newChart.createValueAxis(AxisPosition.LEFT);
            }

            if (axisPosition == AxisPosition.RIGHT) {
                newValueAxis = newChart.createValueAxis(AxisPosition.RIGHT);
            }

            if (newAxis != null) {
                newAxis.setCrosses(oldAxis.getCrosses());
                newAxis.setMajorTickMark(oldAxis.getMajorTickMark());
                newAxis.setMinorTickMark(oldAxis.getMinorTickMark());
                newAxis.setVisible(oldAxis.isVisible());
            }

            if (newValueAxis != null && oldAxis instanceof XDDFValueAxis oldValueAxis) {
                newValueAxis.setCrosses(oldValueAxis.getCrosses());
                newValueAxis.setCrossBetween(oldValueAxis.getCrossBetween());
                newValueAxis.setMajorTickMark(oldValueAxis.getMajorTickMark());
                newValueAxis.setMinorTickMark(oldValueAxis.getMinorTickMark());
                newValueAxis.setVisible(oldValueAxis.isVisible());
                newValueAxis.setOrientation(oldValueAxis.getOrientation());
                newValueAxis.setVisible(oldValueAxis.isVisible());
            }
        }

        if (newAxis == null || newValueAxis == null) {
            log.error("chartAxis or valueAxis is null");
            return null;
        }

        return (XDDFBarChartData) newChart.createData(ChartTypes.BAR, newAxis, newValueAxis);
    }

    /**
     * 更新图表
     *
     * @param frameShape
     * @param chartConfigs
     */
    public static void updateChart(XSLFGraphicFrame frameShape, Map<String, SlideConfig> chartConfigs) {
        XSLFChart chart = frameShape.getChart();
        String chartTitle = chart.getTitleShape().getText();
        SlideConfig slideConfig = chartConfigs.get(chartTitle);
        if (!(slideConfig instanceof ChartGraphData chartData)) {
            return;
        }

        updateChart(chart, chartData);
    }

    /**
     * 更新图表 支持组合图(折线+柱状) 单种多系列图
     * <p>参考：https://blog.csdn.net/qq_53665559/article/details/135821085
     *
     * @param chart
     * @throws Exception
     */
    public static void updateChart(XSLFChart chart, ChartGraphData chartData) {
        String chartTitle = chart.getTitleShape().getText();
        //设置图形标题
        XSSFWorkbook workbook;
        try {
            workbook = chart.getWorkbook();
            if (Objects.isNull(workbook)) {
                workbook = new XSSFWorkbook();
            }
            XSSFSheet sheetAt = workbook.getSheetAt(0);
            if (Objects.isNull(sheetAt)) {
                sheetAt = workbook.createSheet();
            }

            //图表区
            CTChart ctChart = chart.getCTChart();
            ChartType chartType = chartData.getChartType();
            Map<String, SeriesData> seriesDataMap = chartData.getSeriesDataMap();

            //单图：柱状图、折线图  可以用多个系列数据
            if (chartType == ChartType.BAR) {
                //执行更新图表中的bar的数据refreshBarGraph
                refreshBarGraph(ctChart, sheetAt, seriesDataMap.get(ChartTypes.BAR.name()));
            }
            if (chartType == ChartType.LINE) {
                //执行更新图表中的line的数据refreshLineGraph
                refreshLineGraph(ctChart, sheetAt, seriesDataMap.get(ChartTypes.LINE.name()));
            }

            //组合图
            if (chartType == ChartType.COMBO) {
                //执行更新图表中的bar的数据refreshBarGraph
                refreshBarGraph(ctChart, sheetAt, seriesDataMap.get(ChartTypes.BAR.name()));

                //执行更新图表中的line的数据refreshLineGraph
                refreshLineGraph(ctChart, sheetAt, seriesDataMap.get(ChartTypes.LINE.name()));
                XDDFChartData xddfChartData = chart.getChartSeries().getFirst();
                //设置Y轴最小值  间隔自动调整
                xddfChartData.getValueAxes().getFirst().setMinimum(0);
            }

            //更新嵌入的workbook
            POIXMLDocumentPart xlsPart = chart.getRelations().getFirst();
            OutputStream xlsOut = xlsPart.getPackagePart().getOutputStream();
            workbook.write(xlsOut);
            xlsOut.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 刷新柱状图
     *
     */
    private static void refreshBarGraph(CTChart ctChart, XSSFSheet sheetAt, SeriesData chartData) {
        //绘图区
        CTPlotArea plotArea = ctChart.getPlotArea();
        CTBarChart ctBarChart = plotArea.getBarChartList().getFirst();
        if (ctBarChart != null) {
            List<String> series = chartData.getSeries();
            List<String> categories = chartData.getCategories();
            List<List<Double>> values = chartData.getValues();
            CTBarSer[] serArray = ctBarChart.getSerArray();
            for (int i = 0; i < serArray.length; i++) {
                //设置系列名称
                CTSerTx tx = serArray[i].getTx();
                tx.setV(series.get(i));
                // 获取轴
                CTAxDataSource cat = serArray[i].getCat();
                // 获取图表的值
                CTNumDataSource val = serArray[i].getVal();
                refreshGraphContent(sheetAt, cat, val, categories, values.get(i), i + 1);
            }
        }

    }

    /**
     * 刷新折线图
     */
    public static void refreshLineGraph(CTChart ctChart, XSSFSheet sheetAt, SeriesData chartData) {
        //绘图区
        CTPlotArea plotArea = ctChart.getPlotArea();
        CTLineChart lineChart = plotArea.getLineChartList().getFirst();
        if (lineChart != null) {
            List<String> series = chartData.getSeries();
            List<String> categories = chartData.getCategories();
            List<List<Double>> values = chartData.getValues();
            CTLineSer[] serArray = lineChart.getSerArray();
            for (int i = 0; i < serArray.length; i++) {
                //设置系列名
                CTSerTx tx = serArray[i].getTx();
                tx.setV(series.get(i));
                // 获取轴
                CTAxDataSource cat = serArray[i].getCat();
                // 获取图表的值
                CTNumDataSource val = serArray[i].getVal();
                refreshGraphContent(sheetAt, cat, val, categories, values.get(i), i + 1);
            }
        }
    }

    /**
     * 具体刷新图表内容操作
     */
    public static void refreshGraphContent(Sheet sheet, CTAxDataSource cat, CTNumDataSource val, List<String> category, List<Double> seriesData, int column) {
        CTStrData axisData = cat.getStrRef().getStrCache();
        CTNumData numData = val.getNumRef().getNumCache();
        // 将轴置空
        axisData.setPtArray(null);
        // 将值置空
        numData.setPtArray(null);
        // 重新设置数据
        int idx = 0;
        int rownum = 1;

        for (Double pointData : seriesData) {
            String categoryName = category.get(idx);
            CTNumVal numVal = numData.addNewPt();
            numVal.setIdx(idx);
            numVal.setV(String.valueOf(pointData));

            CTStrVal sVal = axisData.addNewPt();
            sVal.setIdx(idx);
            sVal.setV(categoryName);

            idx++;
            Row row = sheet.getRow(rownum);
            if (row == null) {
                row = sheet.createRow(rownum++);
            } else {
                rownum++;
            }
            row.createCell(0).setCellValue(categoryName);
            row.createCell(column).setCellValue(pointData);
        }
        numData.getPtCount().setVal(idx);
        axisData.getPtCount().setVal(idx);
        //更新数据源的公式 框定工作簿的范围
        String numDataRange = new CellRangeAddress(1, rownum - 1, column, column).formatAsString(sheet.getSheetName(), true);
        val.getNumRef().setF(numDataRange);
        String axisDataRange = new CellRangeAddress(1, rownum - 1, 0, 0).formatAsString(sheet.getSheetName(), true);
        cat.getStrRef().setF(axisDataRange);
    }
}
