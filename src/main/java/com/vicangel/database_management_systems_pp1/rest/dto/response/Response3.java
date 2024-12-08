package com.vicangel.database_management_systems_pp1.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Response3 {

  private Integer crimeCode;
  private String crimeDescription;
  private String areaName;
  private Integer total;
}
