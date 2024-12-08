package com.vicangel.database_management_systems_pp1.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Response10 {

  private Integer area;
  private String areaName;
  private String gapStartTime;
  private String gapEndTime;
  private Integer durationInHours;
}
