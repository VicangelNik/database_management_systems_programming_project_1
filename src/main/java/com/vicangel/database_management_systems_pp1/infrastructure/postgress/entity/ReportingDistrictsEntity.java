package com.vicangel.database_management_systems_pp1.infrastructure.postgress.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A four-digit code that represents a sub-area within a Geographic Area.
 * All crime records reference the "RD" that it occurred in for statistical comparisons.
 * Find LAPD Reporting Districts on the LA City GeoHub at
 *
 * @see <a href="https://geohub.lacity.org/datasets/lahub::lapd-reporting-districts/about">lapd-reporting-districts</a>
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "reporting_districts")
public class ReportingDistrictsEntity {

  @Id
  @Column(name = "fid")
  private Integer fid;

  @Column(name = "rep_dist") // reporting District Number
  private Integer repDist;

  @Column(name = "prec")
  private Integer prec;

  @Column(name = "aprec")
  private String apRec;

  @Column(name = "bureau")
  private String bureau;

  @Column(name = "basic_car")
  private String basicCar;

  @Column(name = "agency")
  private String agency;

  @Column(name = "name")
  private String name;

  @Column(name = "shape_leng")
  private Double shapeLeng;

  @Column(name = "abbrev_apr")
  private String abbrevApr;

  @Column(name = "shape_area")
  private Double shapeArea;

  @Column(name = "shape_length")
  private Double shapeLength;
}
