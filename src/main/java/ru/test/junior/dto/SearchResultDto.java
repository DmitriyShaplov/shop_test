package ru.test.junior.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SearchResultDto {
    private String type;

    private List<Result> results;

    @Data
    public static class Result {
        private Map<String, String> criteria;
        private List<CustomerResult> results;

        @Data
        public static class CustomerResult {
            private String lastName;
            private String firstName;
        }
    }
}
