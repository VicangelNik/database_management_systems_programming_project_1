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
  private String crime1Description;
  private String crime2Description;
  private Integer coOccurrenceCount;
}
