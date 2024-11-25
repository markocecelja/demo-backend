package com.example.repositories;

import com.example.domain.codebook.CustomerStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerStatusRepository extends JpaRepository<CustomerStatus, Long> {
}
