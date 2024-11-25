package com.example.specification;

import com.example.domain.Customer;
import com.example.specification.criteria.CustomerSearchCriteria;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CustomerSearchSpecification {

	public static Specification<Customer> findCustomers(final CustomerSearchCriteria criteria) {

		return (root, criteriaQuery, criteriaBuilder) -> {

			List<Predicate> restrictions = new ArrayList<>();

			String name = criteria.getName();
			Long status = criteria.getStatus();

			if (StringUtils.hasLength(name)) {
				restrictions.add(
						criteriaBuilder.or(
								criteriaBuilder.like(
										criteriaBuilder.concat(
												criteriaBuilder.concat(criteriaBuilder.lower(root.get("firstName")), " "),
												criteriaBuilder.lower(root.get("lastName"))), "%" + name.toLowerCase() + "%"
								),
								criteriaBuilder.like(
										criteriaBuilder.concat(
												criteriaBuilder.concat(criteriaBuilder.lower(root.get("lastName")), " "),
												criteriaBuilder.lower(root.get("firstName"))), "%" + name.toLowerCase() + "%"
								),
								criteriaBuilder.like(
										criteriaBuilder.lower(root.get("firstName")), "%" + name.toLowerCase() + "%"
								),
								criteriaBuilder.like(
										criteriaBuilder.lower(root.get("lastName")), "%" + name.toLowerCase() + "%"
								)
						)
				);
			}

			if (status != null) {
				restrictions.add(criteriaBuilder.equal(root.get("status").get("id"), status));
			}

			criteriaQuery.distinct(true);

			return criteriaBuilder.and(restrictions.toArray(new Predicate[0]));
		};
	}
}
