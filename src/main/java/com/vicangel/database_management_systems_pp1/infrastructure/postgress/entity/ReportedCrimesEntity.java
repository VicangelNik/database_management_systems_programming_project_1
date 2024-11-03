package com.vicangel.database_management_systems_pp1.infrastructure.postgress.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "reported_crimes")
public class ReportedCrimesEntity {

  @Id
  @Column(name = "rd_no")
  private Long rdNo;
}
