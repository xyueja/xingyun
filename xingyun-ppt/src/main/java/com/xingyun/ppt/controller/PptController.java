package com.xingyun.ppt.controller;

import com.xingyun.ppt.ppt.PptGenerator;
import com.xingyun.ppt.ppt.entity.ChartData;
import com.xingyun.ppt.ppt.entity.ChartType;
import com.xingyun.ppt.ppt.entity.PptTemplate;
import com.xingyun.ppt.ppt.entity.SlideConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * PptController
 *
 * @author yxuej
 * @since 2026-06-13
 */
@Slf4j
@RestController
@RequestMapping("/ppt")
public class PptController {
    @Autowired
    private PptGenerator pptGenerator;

    @GetMapping("/generate")
    public void generate(HttpServletRequest request, HttpServletResponse response) {
        PptTemplate template = new PptTemplate();
        template.setTemplateId(request.getParameter("templateId"));
        template.setTemplateName("QBR-report.pptx");
        template.setTemplatePath("/templates/ppt/QBR.pptx");
        List<SlideConfig> slideConfigs = new ArrayList<>();
        String[] categories = new String[]{"金", "木", "水", "火", "土"};
        String[] series = new String[]{"天干", "地支"};
        Double[][] values = new Double[2][4];
        values[0] = new Double[]{1.0, 2.0, 3.0, 4.0, 5.0};
        values[1] = new Double[]{1.75, 2.25, 2.75, 3.75, 4.25};
        slideConfigs.add(ChartData.of(0, "柱形图", ChartType.BAR).setBarData(categories, series, values));
        template.setSlideConfigs(slideConfigs);
        byte[] bytes = null;
        try {
            bytes = pptGenerator.generatePpt(template);
            response.setContentType("application/vnd.openxmlformats-officedocument.presentationml.presentation");
            response.setHeader("Content-Disposition", "attachment; filename=" + template.getTemplateName());
            try (BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream())) {
                bos.write(bytes);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
