package com.task.CustomerManagementApplication.specification;

import org.springframework.data.jpa.domain.Specification;

import com.task.CustomerManagementApplication.entities.Customer;

public class CustomerSpecification {

    private CustomerSpecification() {
    }

    public static Specification<Customer> withNameContaining(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Customer> withEmailContaining(String email) {
        return (root, query, criteriaBuilder) -> {
            if (email == null || email.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("email")),
                    "%" + email.toLowerCase() + "%");
        };
    }

    public static Specification<Customer> withPhoneContaining(String phone) {
        return (root, query, criteriaBuilder) -> {
            if (phone == null || phone.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("phone"), "%" + phone + "%");
        };
    }

    public static Specification<Customer> filterByNameEmailPhone(String name, String email, String phone) {
        return Specification.where(withNameContaining(name))
                .and(withEmailContaining(email))
                .and(withPhoneContaining(phone));
    }
}
