package ru.test.junior.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Data
public class Purchase {

    @Id
    private Long id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Product product;

    private LocalDate date;
}
