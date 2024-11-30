package com.vicangel.database_management_systems_pp1.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @apiNote empty values are considered Unknown(X)
 */
@Getter
@RequiredArgsConstructor
public enum VictimDescent {

  OTHER_ASIAN('A'),
  BLACK('B'),
  CHINESE('C'),
  CAMBODIAN('D'),
  FILIPINO('F'),
  GUAMANIAN('G'),
  HISPANIC_lATIN_MEXICAN('H'),
  AMERICAN_INDIAN_ALASKAN_NATIVE('I'),
  JAPANESE('J'),
  KOREAN('K'),
  LAOTIAN('L'),
  OTHER('O'),
  PACIFIC_ISLANDER('P'),
  SAMOAN('S'),
  HAWAIIAN('U'),
  VIETNAMESE('V'),
  WHITE('W'),
  UNKNOWN('X'),
  ASIAN_INDIAN('Z');

  private final char descentCode;
}
