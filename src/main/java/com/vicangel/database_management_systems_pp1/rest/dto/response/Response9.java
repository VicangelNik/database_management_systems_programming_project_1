package com.vicangel.database_management_systems_pp1.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Response9 {

  private Integer ageGroupStart;
  private Integer ageGroupEnd;
  private Integer weaponCode;
  private String weaponDescription;
  private Integer weaponCount;
}
