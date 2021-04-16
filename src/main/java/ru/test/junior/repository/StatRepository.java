package ru.test.junior.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.test.junior.model.Customer;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StatRepository extends JpaRepository<Customer, Long> {

    @Query(value = "select c.id, concat(c.last_name, ' ', c.first_name) as cname, pr.name, " +
            "sum(pr.price) as expenses from customer c " +
            "join purchase p on p.customer_id = c.id " +
            "join Product pr on p.product_id = pr.id " +
            "where p.date between :from and :to group by c.id, pr.id order by expenses desc ", nativeQuery = true)
    List<Object[]> customerStat(@Param("from") LocalDate from, @Param("to") LocalDate to);
}
