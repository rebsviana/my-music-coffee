package com.ciandt.summit.bootcamp2022.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;

import java.util.List;

public class PageDecoratorDto<T> {

    private final Page<T> page;

    public PageDecoratorDto(Page<T> page) {
        this.page = page;
    }

    @JsonProperty("data") // override property name in json
    public List<T> getContent() {
        return this.page.getContent();
    }

}
