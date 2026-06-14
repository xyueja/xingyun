package com.xingyun.ppt.ppt.entity;


import lombok.Getter;
import lombok.Setter;

/**
 * SlideConfig
 *
 * @author yxuej
 * @since 2026-06-13
 */
@Getter
@Setter
public class SlideConfig {
    private int slideIndex;

    private String name;

    private SlideConfigType slideType;

    public <T extends SlideConfig> T build(int index, String key, SlideConfigType slideConfigType) {
        this.slideIndex = index;
        this.name = key;
        this.slideType = slideConfigType;
        return (T) this;
    }
}
