package com.vicangel.database_management_systems_pp1.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Response8 {

  private Integer crimeCode1;
  private Integer crimeCode2;
  private String crimeDescription1;
  private String crimeDescription2;
  private Integer total;
}
