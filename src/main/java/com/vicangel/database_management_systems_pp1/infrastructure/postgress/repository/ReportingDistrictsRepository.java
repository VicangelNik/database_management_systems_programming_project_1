package com.vicangel.database_management_systems_pp1.infrastructure.postgress.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vicangel.database_management_systems_pp1.infrastructure.postgress.entity.ReportingDistrictsEntity;

@Repository
public interface ReportingDistrictsRepository extends JpaRepository<ReportingDistrictsEntity, Long> {
}
