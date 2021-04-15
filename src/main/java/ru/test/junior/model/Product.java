package ru.test.junior.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Product {

    @Id
    private Long id;

    private String name;

    private Double price;
}
