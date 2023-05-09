package com.alphadot.authservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alphadot.authservice.model.APIRoleMaping;
@Repository
public interface APIRoleMapingRepository extends JpaRepository<APIRoleMaping, Integer> {

	public List<APIRoleMaping> findByApiId(Integer apiId);
}
