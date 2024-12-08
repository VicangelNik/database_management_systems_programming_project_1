package com.vicangel.database_management_systems_pp1.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Response6 {

  private String areasOrDistricts;
  private Integer totalCrimes;
}
