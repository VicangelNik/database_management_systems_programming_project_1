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
@Table(name = "mo_codes")
public class MoCodesEntity {

  @Id
  private Integer id;

  @Column(name = "mo_code")
  private String moCode;
}
