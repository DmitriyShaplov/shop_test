package ru.test.junior.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StatRequestDto {

    LocalDate startDate;
    LocalDate endDate;
}
