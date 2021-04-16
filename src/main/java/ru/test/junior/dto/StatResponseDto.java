package ru.test.junior.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Data
public class StatResponseDto {
    private String type;
    private int totalDays;
    private List<CustomerResult> customers;
    private double totalExpenses;
    private double avgExpenses;

    public void setAvgExpenses(double avgExpenses) {
        this.avgExpenses = new BigDecimal(avgExpenses).setScale(2, RoundingMode.HALF_UP).doubleValue();;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerResult {

        private String name;
        private List<PurchaseResult> purchases;
        private double totalExpenses;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class PurchaseResult {
            private String name;
            private double expenses;
        }
    }
}
