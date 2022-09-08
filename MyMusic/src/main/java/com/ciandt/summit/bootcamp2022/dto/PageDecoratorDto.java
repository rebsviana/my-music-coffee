package com.ciandt.summit.bootcamp2022.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@AllArgsConstructor
public class PageDecoratorDto<T> {
    private final Page<T> page;

    @JsonProperty("data")
    public List<T> getContent() {
        return this.page.getContent();
    }
}
