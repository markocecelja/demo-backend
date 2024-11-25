package com.example.domain;

import com.example.domain.codebook.CustomerStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "update customer SET deleted_date_time = (SELECT CURRENT_TIMESTAMP) where id=?")
@SQLRestriction("deleted_date_time IS NULL")
@NamedEntityGraph(name = "customer_graph",
		attributeNodes = {
				@NamedAttributeNode(value = "status")
		}
)
@Table(indexes = {
		@Index(name = "idx_customer_id", columnList = "id", unique = true),
		@Index(name = "idx_customer_first_name", columnList = "first_name"),
		@Index(name = "idx_customer_last_name", columnList = "last_name"),
		@Index(name = "idx_customer_status_id", columnList =  "status_id"),
})
public class Customer extends AbstractBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String firstName;

	private String lastName;

	@ManyToOne(fetch = FetchType.LAZY)
	private CustomerStatus status;
}
