package ru.test.junior.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
public class Customer {

    @Id
    private Long id;

    private String firstName;

    private String lastName;

    @OneToMany(mappedBy = "customer")
    private List<Purchase> purchases;
}
