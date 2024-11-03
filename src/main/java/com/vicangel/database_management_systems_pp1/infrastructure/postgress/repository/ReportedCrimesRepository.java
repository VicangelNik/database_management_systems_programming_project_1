package com.vicangel.database_management_systems_pp1.infrastructure.postgress.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vicangel.database_management_systems_pp1.infrastructure.postgress.entity.ReportedCrimesEntity;

@Repository
public interface ReportedCrimesRepository extends JpaRepository<ReportedCrimesEntity, Long> {
}
