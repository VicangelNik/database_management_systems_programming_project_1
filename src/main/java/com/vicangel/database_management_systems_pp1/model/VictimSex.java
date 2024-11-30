package com.vicangel.database_management_systems_pp1.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @apiNote empty values are considered Unknown(X)
 */
@Getter
@RequiredArgsConstructor
public enum VictimSex {
  FEMALE('F'),
  MALE('M'),
  UNKNOWN('X');

  private final char sexCode;
}
