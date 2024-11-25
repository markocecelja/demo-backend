package com.example.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractBaseEntity implements Serializable {

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdDateTime;

	@UpdateTimestamp
	private LocalDateTime updatedDateTime;

	private LocalDateTime deletedDateTime;
}
