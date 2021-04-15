package ru.test.junior.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.test.junior.model.Customer;

import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Customer, Long> {

    List<Customer> findCustomerByLastName(String lastName);

    @Query("select c from Customer c " +
            "join Purchase p on p.customer = c join Product pr on p.product = pr where pr.name = :name " +
            "group by c.id having count(p.id) > :count")
    List<Customer> findCustomersByPurchaseCount(@Param("name") String name, @Param("count") long count);

    @Query("select c from Customer c join Purchase p on p.customer = c join Product pr on pr = p.product group by c.id " +
            "having sum(pr.price) between :minExpenses and :maxExpenses")
    List<Customer> findByExpense(@Param("minExpenses") double minExpenses, @Param("maxExpenses") double maxExpenses);

    @Query("select c, count(p) as cnt from Customer c join Purchase p on p.customer = c group by c.id order by cnt asc")
    List<Customer> findBadass(Pageable pageable);
}
