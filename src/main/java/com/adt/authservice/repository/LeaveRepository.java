package com.adt.authservice.repository;

import com.adt.authservice.model.LeaveModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveRepository extends JpaRepository<LeaveModel, Integer> {

}
