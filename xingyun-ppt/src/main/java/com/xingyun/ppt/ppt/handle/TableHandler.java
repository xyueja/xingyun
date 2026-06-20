/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.ppt.ppt.handle;

import com.xingyun.ppt.ppt.entity.SlideConfig;
import com.xingyun.ppt.ppt.entity.TableData;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.poi.sl.usermodel.StrokeStyle;
import org.apache.poi.sl.usermodel.TableCell;
import org.apache.poi.xslf.usermodel.*;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * TableHandler
 *
 * @author yxuej
 * @since 2026-06-20
 */
public class TableHandler {
    private TableHandler() {
    }

    public static void handle(XSLFTable table, Map<String, SlideConfig> tableConfigs) {
        // 1. 获取表格数据
        TableData tableData = getTableData(table, tableConfigs).orElse(null);
        if (tableData == null) {
            return;
        }

        // 2.填充表头
        List<String> headers = tableData.getHeaders();
        if (CollectionUtils.isNotEmpty(headers)) {
            int headerIndex = 0;
            for (String header : headers) {
                table.getCell(0, headerIndex++).setText(header);
            }
        }

        // 3.填充行
        List<List<String>> rows = tableData.getRows();
        int startRowIndex = tableData.getStartRowIndex();

        // 3.1 找到模板引用行，若找不到直接return
        XSLFTableRow referenceRow = table.getRows().get(startRowIndex);
        if (referenceRow == null) {
            return;
        }

        // 3.2 若行没有数据，清空模板引用行数据
        List<XSLFTableCell> referenceCells = referenceRow.getCells();
        if (CollectionUtils.isEmpty(rows)) {
            for (XSLFTableCell tableCell : referenceCells) {
                tableCell.setText(null);
            }
            return;
        }

        // 3.3 行有数据，填充每一行数据
        boolean isStartRow = true;
        for (List<String> rowValue : rows) {
            XSLFTableRow tableRow = getOrCreateRow(table, startRowIndex);

            int cellIndex = 0;
            for (String cellValue : rowValue) {
                XSLFTableCell currentCell = getOrCreateCell(tableRow, cellIndex);
                if (isStartRow) {
                    // 起始行模板已自带样式，只需设置文本样式，不设置边框样式
                    copyCellStyleWithNoBorder(referenceCells.get(cellIndex), currentCell, cellValue);
                } else {
                    copyCellStyle(referenceCells.get(cellIndex), currentCell, cellValue);
                }
                cellIndex++;
            }

            // 行索引递增
            isStartRow = false;
            startRowIndex++;
        }

    }

    private static Optional<TableData> getTableData(XSLFTable table, Map<String, SlideConfig> tableConfigs) {
        if (MapUtils.isEmpty(tableConfigs)) {
            return Optional.empty();
        }

        String tableName = table.getShapeName();
        if (tableConfigs.get(tableName) instanceof TableData tableData) {
            return Optional.of(tableData);
        }
        return Optional.empty();
    }

    private static XSLFTableRow getOrCreateRow(XSLFTable table, int startRowIndex) {
        List<XSLFTableRow> tableRows = table.getRows();

        // 行已存在直接返回，否则创建行
        if (startRowIndex < tableRows.size()) {
            return tableRows.get(startRowIndex);
        }

        return table.addRow();
    }

    private static XSLFTableCell getOrCreateCell(XSLFTableRow currentRow, int cellIndex) {
        // 获取当前行的所有单元格，如果存在指定下标的单元格就直接返回，不存在则创建
        List<XSLFTableCell> tableCells = currentRow.getCells();
        if (cellIndex < tableCells.size()) {
            return tableCells.get(cellIndex);
        }
        return currentRow.addCell();
    }

    private static void copyCellStyle(XSLFTableCell from, XSLFTableCell to, String cellValue) {
        copyCellStyleWithNoBorder(from, to, cellValue);
        copyBorderStyle(from, to);
    }

    private static void copyCellStyleWithNoBorder(XSLFTableCell from, XSLFTableCell to, String cellValue) {
        // 垂直对其
        to.setVerticalAlignment(from.getVerticalAlignment());

        // 复制文本样式并设置文本
        copyTextStyle(from, to, cellValue);
    }

    private static void copyTextStyle(XSLFTableCell from, XSLFTableCell to, String cellValue) {
        List<XSLFTextParagraph> fromParagraphs = from.getTextParagraphs();
        if (CollectionUtils.isEmpty(fromParagraphs)) {
            to.setText(cellValue);
            return;
        }

        XSLFTextParagraph fromParagraph = fromParagraphs.getFirst();
        if (fromParagraph == null || fromParagraph.getTextRuns().isEmpty()) {
            to.setText(cellValue);
            return;
        }

        List<XSLFTextParagraph> toParagraphs = to.getTextParagraphs();
        XSLFTextParagraph toParagraph;
        if (CollectionUtils.isEmpty(toParagraphs)) {
            toParagraph = to.addNewTextParagraph();
        } else {
            toParagraph = toParagraphs.getFirst();
        }

        XSLFTextRun fromRun = fromParagraph.getTextRuns().getFirst();
        List<XSLFTextRun> toRuns = toParagraph.getTextRuns();
        XSLFTextRun toRun;
        if (CollectionUtils.isEmpty(toRuns)) {
            toRun = toParagraph.addNewTextRun();
        } else {
            toRun = toRuns.getFirst();
        }

        // 复制文本（字体、大小、颜色、是否加粗、是否斜体等）
        toRun.setText(cellValue);
        toRun.setFontColor(fromRun.getFontColor());
        toRun.setFontSize(fromRun.getFontSize());
        toRun.setFontFamily(fromRun.getFontFamily());
        toRun.setBold(fromRun.isBold());
        toRun.setItalic(fromRun.isItalic());

        // 设置文本对其方式
        toParagraph.setTextAlign(fromParagraph.getTextAlign());
    }

    private static void copyBorderStyle(XSLFTableCell from, XSLFTableCell to) {
        setBorderStyle(from, to, TableCell.BorderEdge.top);
        setBorderStyle(from, to, TableCell.BorderEdge.bottom);
        setBorderStyle(from, to, TableCell.BorderEdge.left);
        setBorderStyle(from, to, TableCell.BorderEdge.right);
    }

    private static void setBorderStyle(XSLFTableCell from, XSLFTableCell to, TableCell.BorderEdge edge) {
        StrokeStyle fromBorderStyle = from.getBorderStyle(edge);
        if (fromBorderStyle != null) {
            to.setBorderStyle(edge, fromBorderStyle);

            Color fromBorderColor = from.getBorderColor(edge);
            if (fromBorderColor != null) {
                to.setBorderColor(edge, fromBorderColor);
            }

            if (from.getFillColor() != null) {
                to.setFillColor(from.getFillColor());
            }
        }
    }
}
