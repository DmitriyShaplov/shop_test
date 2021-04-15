package ru.test.junior.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class RequestSearchCriteria {
    private List<Map<String, String>> criterias;
}
