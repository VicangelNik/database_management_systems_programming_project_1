package com.vicangel.database_management_systems_pp1.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class ReportedCrime {

  private Integer drNo;
  private LocalDate dateReported;
  private LocalDateTime occDateTime;
  private Integer part12;
  private Integer area;
  private Integer premis;
  private Integer reportingDistrict;
  private String status;
  private String weapon;
}
