package ru.test.junior.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;
import java.util.List;

@SqlResultSetMapping(
        name = "StatResultSet",
        entities = {
                @EntityResult(
                        entityClass = StatModel.class,
                        fields = {
                                @FieldResult(name = "name", column = "cname")
                        }
                ),
                @EntityResult(
                        entityClass = StatModel.PurchaseModel.class,
                        fields = {@FieldResult(name = "name", column = "name"),
                        @FieldResult(name = "expenses", column = "expenses")}
                )
        }
)
@NamedNativeQuery(
        name = "StatModel.customerStat",
        query = "select concat(c.last_name, c.first_name) as cname, pr.name, " +
                "sum(pr.price) as expenses from customer c " +
                "join purchase p on p.customer_id = c.id " +
                "join Product pr on p.product_id = pr.id " +
                "where p.date between :from and :to group by c.id, pr.id order by expenses desc ",
        resultSetMapping = "StatResultSet"
)
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class StatModel {

    @Id
    private String name;

    @OneToMany
    private List<PurchaseModel> purchases;

    @Data
    @Entity
    public static class PurchaseModel {
        @Id
        private String name;
        private double expenses;
    }
}
