package com.app.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.warehouse.model.WhUserType;

@Repository
public interface WhUserTypeRepository extends JpaRepository<WhUserType, Integer> {

}
