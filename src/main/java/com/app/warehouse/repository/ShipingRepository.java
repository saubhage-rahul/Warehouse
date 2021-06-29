package com.app.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.warehouse.model.Shiping;

@Repository
public interface ShipingRepository extends JpaRepository<Shiping, Integer> {

}
