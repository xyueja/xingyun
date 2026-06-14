package com.xingyun.ppt.ppt;

import com.xingyun.ppt.ppt.entity.*;
import com.xingyun.ppt.ppt.handle.ChartHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xslf.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * PptGenerator
 *
 * @author yxuej
 * @since 2026-06-13
 */
@Slf4j
@Component
public class PptGenerator {
    public byte[] generatePpt(PptTemplate pptTemplate) throws IOException {
        File templateFile = copyToTemp(pptTemplate.getTemplatePath());

        List<SlideConfig> slideConfigs = pptTemplate.getSlideConfigs();
        Map<Integer, List<SlideConfig>> slideConfigsMap = slideConfigs.stream().collect(Collectors.groupingBy(SlideConfig::getSlideIndex));
        try (InputStream in = new FileInputStream(templateFile); XMLSlideShow ppt = new XMLSlideShow(in)) {
            List<XSLFSlide> slides = ppt.getSlides();
            for (int i = 0; i < slides.size(); i++) {
                XSLFSlide slide = slides.get(i);
                List<SlideConfig> templateConfigs = slideConfigsMap.get(i);
                if (i == 1) {
                    testUpdate(ppt, slide);
                    continue;
                }

                if (i == 2) {
                    testCombo(ppt, slide);
                    continue;
                }

                if (CollectionUtils.isEmpty(templateConfigs)) {
                    continue;
                }

                dealSlide(ppt, slide, templateConfigs, false);

            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ppt.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new IOException(e);
        } finally {
            FileUtils.deleteQuietly(templateFile);
        }
    }

    private void testCombo(XMLSlideShow ppt, XSLFSlide slide) {
        List<String> barSeries = List.of("水系", "火系");
        List<String> categories = List.of("类别1", "类别2", "类别3", "类别4");
        List<List<Double>> barData = List.of(List.of(1.0, 2.0, 3.0, 4.0), List.of(4.0, 3.0, 2.0, 1.0));
        List<String> lineSeries = List.of("土系");
        List<List<Double>> lineData = List.of(List.of(2.0, 2.5, 3.0, 4.0));

        SeriesData barSeriesData = SeriesData.builder().series(barSeries).categories(categories).values(barData).build();
        SeriesData lineSeriesData = SeriesData.builder().series(lineSeries).categories(categories).values(lineData).build();
        SlideConfig chartConfig = ChartGraphData.of(2, "组合图", ChartType.COMBO).setSeries(ChartTypes.BAR, barSeriesData).setSeries(ChartTypes.LINE, lineSeriesData);
        dealSlide(ppt, slide, Collections.singletonList(chartConfig), true);
    }

    private void testUpdate(XMLSlideShow ppt, XSLFSlide slide) {
        String[] categories = new String[]{"类别1", "类别2", "类别3", "类别4", "类别5"};
        String[] series = new String[]{"系列1", "系列2"};
        Double[][] values = new Double[2][4];
        values[0] = new Double[]{1.0, 2.0, 3.0, 4.0, 5.0};
        values[1] = new Double[]{1.75, 2.25, 2.75, 3.75, 4.25};
//        SlideConfig slideConfig = ChartData.of(1, "柱形图2", ChartTypes.BAR).setBarData(categories, series, values);

        List<List<Double>> valuesList = Stream.of(values).map(row -> new ArrayList<>(List.of(row))).collect(Collectors.toList());
        SeriesData seriesData = SeriesData.builder().series(Arrays.asList(series)).categories(Arrays.asList(categories)).values(valuesList).build();
        SlideConfig chartConfig = ChartGraphData.of(1, "柱形图2", ChartType.BAR).setSeries(ChartTypes.BAR, seriesData);
        dealSlide(ppt, slide, Collections.singletonList(chartConfig), true);
    }

    private void dealSlide(XMLSlideShow ppt, XSLFSlide slide, List<SlideConfig> templateConfigs, boolean isUpdate) {
        Map<String, SlideConfig> chartConfigs = templateConfigs.stream().filter(config -> config.getSlideType() == SlideConfigType.CHART)
                .collect(Collectors.toMap(SlideConfig::getName, config -> config, (v1, v2) -> v2));
        List<XSLFShape> shapesCopy = new ArrayList<>(slide.getShapes());
        for (XSLFShape shape : shapesCopy) {
            switch (shape) {
                case XSLFTextShape textShape:
                    handler(textShape);
                    break;
                case XSLFTable tableShape:
                    handler(tableShape);
                    break;
                case XSLFPictureShape pictureShape:
                    handler(pictureShape);
                    break;
                case XSLFGraphicFrame frameShape:
                    if (frameShape.hasChart()) {
                        if (isUpdate) {
                            ChartHandler.updateChart(frameShape, chartConfigs);
                        } else {
                            ChartHandler.handle(ppt, slide, frameShape, chartConfigs);
                        }
                    }
                    break;
                default:
                    handler(null);
            }
        }
    }

    private void handler(XSLFShape shape) {
    }

    private File copyToTemp(String templatePath) throws IOException {
        try (InputStream in = getClass().getResourceAsStream(templatePath)) {
            if (in == null) {
                throw new IOException("template not found: " + templatePath);
            }
            File templateFile = File.createTempFile("ppt_template_" + System.currentTimeMillis(), ".ppt");
            FileUtils.copyInputStreamToFile(in, templateFile);
            templateFile.deleteOnExit();
            return templateFile;
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}
