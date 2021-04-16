package ru.test.junior.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
@Data
public class Product {

    @Id
    private Long id;

    private String name;

    private Double price;

    @Transient
    private Double expenses;
}
