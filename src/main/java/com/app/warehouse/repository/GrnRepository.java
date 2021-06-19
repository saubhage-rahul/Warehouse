package com.app.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.warehouse.model.Grn;

@Repository
public interface GrnRepository extends JpaRepository<Grn, Integer> {

}
