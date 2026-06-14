package com.xingyun.ppt.ppt.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * PptTemplate
 *
 * @author yxuej
 * @since 2026-06-13
 */
@Getter
@Setter
public class PptTemplate {
    private String templateId;

    private String templatePath;

    private String templateName;

    private List<SlideConfig> slideConfigs = new ArrayList<>();
}
