package com.vicangel.database_management_systems_pp1.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Response12 {

  private String reportDate;
  private Integer weaponCode;
  private String weaponDescription;
  private Integer areaCount;
  private Integer recordCount;
}
