package com.adt.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adt.authservice.model.ApiDetails;

public interface ApiDetailsRepository extends JpaRepository<ApiDetails, Integer>{

	Optional<ApiDetails> findByApiId(Integer apiId);

	public Optional<ApiDetails> findByApiName(String apiName);

	public void deleteByApiName(String apiname);

}
