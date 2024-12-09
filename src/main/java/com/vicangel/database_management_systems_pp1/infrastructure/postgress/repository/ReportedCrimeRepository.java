package com.vicangel.database_management_systems_pp1.infrastructure.postgress.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.vicangel.database_management_systems_pp1.model.ReportedCrime;
import com.vicangel.database_management_systems_pp1.rest.dto.request.BoundingBoxRequest;
import com.vicangel.database_management_systems_pp1.rest.dto.response.AreaResponse6;
import com.vicangel.database_management_systems_pp1.rest.dto.response.DistrictResponse6;
import com.vicangel.database_management_systems_pp1.rest.dto.response.Response1;
import com.vicangel.database_management_systems_pp1.rest.dto.response.Response10;
import com.vicangel.database_management_systems_pp1.rest.dto.response.Response11;
import com.vicangel.database_management_systems_pp1.rest.dto.response.Response12;
import com.vicangel.database_management_systems_pp1.rest.dto.response.Response2;
import com.vicangel.database_management_systems_pp1.rest.dto.response.Response3;
import com.vicangel.database_management_systems_pp1.rest.dto.response.Response4;
import com.vicangel.database_management_systems_pp1.rest.dto.response.Response5;
import com.vicangel.database_management_systems_pp1.rest.dto.response.Response7;
import com.vicangel.database_management_systems_pp1.rest.dto.response.Response8;
import com.vicangel.database_management_systems_pp1.rest.dto.response.Response9;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReportedCrimeRepository {

  private static final RowMapper<Response1> mapperResponseQuery1 = BeanPropertyRowMapper.newInstance(Response1.class);
  private static final RowMapper<Response2> mapperResponseQuery2 = BeanPropertyRowMapper.newInstance(Response2.class);
  private static final RowMapper<Response3> mapperResponseQuery3 = BeanPropertyRowMapper.newInstance(Response3.class);
  private static final RowMapper<Response4> mapperResponseQuery4 = BeanPropertyRowMapper.newInstance(Response4.class);
  private static final RowMapper<Response5> mapperResponseQuery5 = BeanPropertyRowMapper.newInstance(Response5.class);
  private static final RowMapper<AreaResponse6> mapperAreaResponseQuery6 = BeanPropertyRowMapper.newInstance(AreaResponse6.class);
  private static final RowMapper<DistrictResponse6> mapperDistrictResponseQuery6 = BeanPropertyRowMapper.newInstance(DistrictResponse6.class);
  private static final RowMapper<Response7> mapperResponseQuery7 = BeanPropertyRowMapper.newInstance(Response7.class);
  private static final RowMapper<Response8> mapperResponseQuery8 = BeanPropertyRowMapper.newInstance(Response8.class);
  private static final RowMapper<Response9> mapperResponseQuery9 = BeanPropertyRowMapper.newInstance(Response9.class);
  private static final RowMapper<Response10> mapperResponseQuery10 = BeanPropertyRowMapper.newInstance(Response10.class);
  private static final RowMapper<Response11> mapperResponseQuery11 = BeanPropertyRowMapper.newInstance(Response11.class);
  private static final RowMapper<Response12> mapperResponseQuery12 = BeanPropertyRowMapper.newInstance(Response12.class);
  private final JdbcTemplate jdbcTemplate;

  public List<ReportedCrime> findReportedCrimes() {
    return jdbcTemplate
      .query("SELECT * FROM reported_crimes", BeanPropertyRowMapper.newInstance(ReportedCrime.class));
  }

  public List<Response1> findTotalReportsPerCrimeBetweenTimeOccurrenceQuery1(
    LocalDateTime from,
    LocalDateTime to) {
    return jdbcTemplate
      .query("""
               SELECT cc.crm_cd crime_code, cc.crm_cd_desc crime_description, COUNT(*) total
               FROM reported_crimes rc
                        INNER JOIN public.reported_crimes_crime_codes rccc ON rc.dr_no = rccc.dr_no
                        INNER JOIN public.crime_codes cc ON cc.crm_cd = rccc.crm_cd
               WHERE rc.occ_date_time BETWEEN ? AND ?
               GROUP BY cc.crm_cd
               """, mapperResponseQuery1, from, to);
  }

  public List<Response2> findTotalReportsForCrimeCodePerDayBetweenReportedDateQuery2(
    Integer crimeCode, LocalDate from, LocalDate to) {
    return jdbcTemplate
      .query("""
               SELECT rc.date_reported, COUNT(*) total
               FROM reported_crimes rc
                        INNER JOIN public.reported_crimes_crime_codes rccc ON rc.dr_no = rccc.dr_no
                        INNER JOIN public.crime_codes cc ON cc.crm_cd = rccc.crm_cd
               WHERE cc.crm_cd = ? AND
                   rc.date_reported BETWEEN ? AND ?
               GROUP BY rc.date_reported
               """, mapperResponseQuery2, crimeCode, from, to);
  }

  public List<Response3> findCommonCrimePerAreaForDateQuery3(@NonNull final LocalDate day) {
    return jdbcTemplate
      .query("""
               SELECT cc.crm_cd crime_code, cc.crm_cd_desc crime_description, a.area_name, COUNT(*) total
               FROM reported_crimes rc
                        INNER JOIN public.reported_crimes_crime_codes rccc ON rc.dr_no = rccc.dr_no
                        INNER JOIN public.crime_codes cc ON cc.crm_cd = rccc.crm_cd
                        INNER JOIN public.area a ON rc.area = a.area
               WHERE DATE(rc.occ_date_time) = ?
               GROUP BY a.area, cc.crm_cd
               ORDER BY total DESC
               """, mapperResponseQuery3, day);
  }

  public List<Response4> findAverageCrimeOccurrencePerHourQuery4(final LocalDate from,
                                                                 final LocalDate to) {
    return jdbcTemplate
      .query(
        """
                SELECT DATE_TRUNC('hour', rc.occ_date_time) per_hour, count(cc.crm_cd) total_crimes
          FROM reported_crimes rc
          inner join public.reported_crimes_crime_codes rccc on rc.dr_no = rccc.dr_no
          inner join public.crime_codes cc on cc.crm_cd = rccc.crm_cd
          WHERE DATE(rc.occ_date_time) BETWEEN ? AND ?
          GROUP BY per_hour
          ORDER BY per_hour
          """, mapperResponseQuery4, from, to);
  }

  public List<Response5> findMostCommonCrimeInCoordinatesBoundingBoxQuery5(LocalDate date,
                                                                           BoundingBoxRequest request) {
    return jdbcTemplate
      .query(
        """
                  SELECT cc.crm_cd crime_code, cc.crm_cd_desc crime_code_description, count(cc.crm_cd) total_crimes
                  FROM reported_crimes rc
                    INNER JOIN public.reported_crimes_crime_codes rccc ON rc.dr_no = rccc.dr_no
                    INNER JOIN public.crime_codes cc ON cc.crm_cd = rccc.crm_cd
                    INNER JOIN public.location l ON rc.location = l.dr_no
                  WHERE  rc.date_reported = ?
                    AND l.latitude >= ? AND l.latitude <= ?
                    AND l.longitude >= ? AND l.longitude <= ?
                  GROUP BY cc.crm_cd
                  ORDER BY total_crimes DESC
                  LIMIT 1;
          """, mapperResponseQuery5,
        date,
        request.southLatitude(),
        request.northLatitude(),
        request.westLongitude(),
        request.eastLongitude());
  }

  public List<AreaResponse6> findTop5AreasWithMostCrimesReportedForSpecificDateRangeQuery6(final LocalDate from,
                                                                                           final LocalDate to) {
    return jdbcTemplate
      .query(
        """
          SELECT a.area_name, COUNT(cc.crm_cd) total_crimes
          FROM reported_crimes rc
                   INNER JOIN public.reported_crimes_crime_codes rccc ON rc.dr_no = rccc.dr_no
                   INNER JOIN public.crime_codes cc ON cc.crm_cd = rccc.crm_cd
                   INNER JOIN public.area a on a.area = rc.area
          WHERE rc.date_reported BETWEEN ? AND ?
          GROUP BY rc.date_reported, a.area_name
          ORDER BY total_crimes DESC
          LIMIT 5;
          """, mapperAreaResponseQuery6, from, to);
  }

  public List<DistrictResponse6> findTop5DistrictsWithMostCrimesReportedForSpecificDateRangeQuery6(
    final LocalDate from,
    final LocalDate to) {
    return jdbcTemplate
      .query(
        """
          SELECT rd.rep_dist AS reporting_district, COUNT(cc.crm_cd) total_crimes
          FROM reported_crimes rc
                   INNER JOIN public.reported_crimes_crime_codes rccc ON rc.dr_no = rccc.dr_no
                   INNER JOIN public.crime_codes cc ON cc.crm_cd = rccc.crm_cd
                   INNER JOIN public.reporting_districts rd on rc.reporting_district = rd.rep_dist
          WHERE rc.date_reported BETWEEN ? AND ?
          GROUP BY rc.date_reported, rd.rep_dist
          ORDER BY total_crimes DESC
          LIMIT 5;
          """, mapperDistrictResponseQuery6, from, to);
  }

  public List<Response7> findPairOfCrimesCoOccurredMostInMostReportedIncidentsAreaQuery7(
    final LocalDate from,
    final LocalDate to) {
    return jdbcTemplate
      .query(
        """
          WITH AreaIncidentCounts AS (
               SELECT
                   rc.area,
                   COUNT(*) AS incident_count
               FROM
                   reported_crimes rc
               WHERE
                   rc.occ_date_time BETWEEN ? AND ?
               GROUP BY
                   rc.area
               ORDER BY
                   incident_count DESC
               LIMIT 1
           ),
          
           -- Step 2: Find all pairs of crimes within the top area
               CrimePairs AS (
                   SELECT
                       rcc1.crm_cd AS crime1,
                       rcc2.crm_cd AS crime2,
                       rc.area,
                       rc.dr_no
                   FROM
                       reported_crimes rc
                           INNER JOIN
                       reported_crimes_crime_codes rcc1 ON rc.dr_no = rcc1.dr_no
                           INNER JOIN
                       reported_crimes_crime_codes rcc2 ON rc.dr_no = rcc2.dr_no
                           INNER JOIN
                       AreaIncidentCounts aic ON rc.area = aic.area
                   WHERE
                       rcc1.crm_cd < rcc2.crm_cd  -- Avoid self-pairing and duplication
               ),
          
           -- Step 3: Count occurrences of each pair
               CrimePairCounts AS (
                   SELECT
                       crime1,
                       crime2,
                       COUNT(*) AS co_occurrence_count
                   FROM
                       CrimePairs
                   GROUP BY
                       crime1, crime2
               )
          
           -- Step 4: Find the pair with the highest co-occurrence count
           SELECT
               cp.crime1,
               cp.crime2,
               c1.crm_cd_desc AS crime1_description,
               c2.crm_cd_desc AS crime2_description,
               cp.co_occurrence_count
           FROM
               CrimePairCounts cp
                   INNER JOIN
               crime_codes c1 ON cp.crime1 = c1.crm_cd
                   INNER JOIN
               crime_codes c2 ON cp.crime2 = c2.crm_cd
           ORDER BY
               cp.co_occurrence_count DESC
           LIMIT 1;
          """, mapperResponseQuery7, from, to);
  }

  public List<Response8> findSecondMostCommonCrimeCoOccurredWithParticularCrimeQuery8(
    final LocalDateTime from,
    final LocalDateTime to) {
    return jdbcTemplate
      .query(
        """
                     WITH CrimePairs AS (
                  SELECT
                      rcc1.crm_cd AS crime1,
                      rcc2.crm_cd AS crime2,
                      rc.dr_no
                  FROM
                      reported_crimes rc
                          INNER JOIN
                      reported_crimes_crime_codes rcc1 ON rc.dr_no = rcc1.dr_no
                          INNER JOIN
                      reported_crimes_crime_codes rcc2 ON rc.dr_no = rcc2.dr_no
                  WHERE
                      rc.occ_date_time BETWEEN ? AND ?
                      AND rcc1.crm_cd < rcc2.crm_cd  -- Avoid self-pairing and duplication
              ),
          
          -- Step 3: Count occurrences of each pair
              CrimePairCounts AS (
                  SELECT
                      crime1,
                      crime2,
                      COUNT(*) AS co_occurrence_count
                  FROM
                      CrimePairs
                  GROUP BY
                      crime1, crime2
              )
          
          -- Step 4: Find the pair with the highest co-occurrence count
          SELECT
              cp.crime1,
              cp.crime2,
              c1.crm_cd_desc AS crime1_description,
              c2.crm_cd_desc AS crime2_description,
              cp.co_occurrence_count
          FROM
              CrimePairCounts cp
                  INNER JOIN
              crime_codes c1 ON cp.crime1 = c1.crm_cd
                  INNER JOIN
              crime_codes c2 ON cp.crime2 = c2.crm_cd
          ORDER BY
              cp.co_occurrence_count DESC
          LIMIT 1
          OFFSET 1;
          """, mapperResponseQuery8, from, to);
  }

  public List<Response9> findMostCommonWeaponTypeUsedAgainstVictimsDependingOnAgeGroupQuery9() {
    return jdbcTemplate
      .query(
        """
          WITH AgeGroups AS (SELECT (v.vict_age / 5) * 5 AS age_group_start, -- Calculate the lower bound of the group
                                 (v.vict_age / 5) * 5 + 5 AS age_group_end, -- Calculate the upper bound of the group
                                 v.dr_no,
                                 rc.weapon
                             FROM victim_info v
                                      INNER JOIN
                             reported_crimes rc ON v.dr_no = rc.dr_no
                             WHERE v.vict_age IS NOT NULL -- Exclude records with null age
          ),
          -- Step 2: Count weapon usage per age group
              WeaponCounts AS (
                  SELECT
                      ag.age_group_start,
                      ag.age_group_end,
                      ag.weapon,
                      COUNT(*) AS weapon_count
                  FROM
                      AgeGroups ag
                  GROUP BY
                      ag.age_group_start, ag.age_group_end, ag.weapon
              ),
          
          -- Step 3: Find the most common weapon for each age group
              MostCommonWeapons AS (
                  SELECT
                      wc.age_group_start,
                      wc.age_group_end,
                      wc.weapon,
                      wc.weapon_count,
                        RANK() OVER (
                          PARTITION BY wc.age_group_start, wc.age_group_end
                          ORDER BY wc.weapon_count DESC
                          ) AS rank
                  FROM
                      WeaponCounts wc
              )
          
          -- Step 4: Select the top-ranked weapon for each age group
          SELECT
              mcw.age_group_start,
              mcw.age_group_end,
              mcw.weapon,
              w.weapon_desc,
              mcw.weapon_count
          FROM
              MostCommonWeapons mcw
                  INNER JOIN
              weapons w ON mcw.weapon = w.weapon_used_cd
          WHERE
              mcw.rank = 1
          ORDER BY
              mcw.age_group_start;
          """, mapperResponseQuery9);
  }

  public List<Response10> findAreaWithLongestTimeRangeWithoutSpecificCrimeOccurrenceQuery10(
    final Integer crimeCode) {
    return jdbcTemplate
      .query(
        """
                    WITH CrimeOccurrences AS (
              SELECT
                  rc.area,
                  rc.occ_date_time
              FROM
                  reported_crimes rc
                      INNER JOIN
                  reported_crimes_crime_codes rcc ON rc.dr_no = rcc.dr_no
              WHERE
                  rcc.crm_cd = ? -- Replace with the specific crime code
          ),
          
              TimeGaps AS (
                  SELECT
                      area,
                      occ_date_time AS start_time,
                        LEAD(occ_date_time) OVER (
                          PARTITION BY area
                          ORDER BY occ_date_time
                          ) AS next_time
                  FROM
                      CrimeOccurrences
              ),
          
              GapsWithDuration AS (
                  SELECT
                      area,
                      start_time,
                      next_time,
                      EXTRACT(EPOCH FROM next_time - start_time) AS gap_duration -- Duration in seconds
                  FROM
                      TimeGaps
                  WHERE
                      next_time IS NOT NULL -- Exclude the last occurrence as it has no "next"
              ),
          
              LongestGap AS (
                  SELECT
                      area,
                      start_time,
                      next_time,
                      gap_duration
                  FROM
                      GapsWithDuration
                  ORDER BY
                      gap_duration DESC
                  LIMIT 1
              )
          
          SELECT
              lg.area,
              a.area_name,
              lg.start_time AS gap_start_time,
              lg.next_time AS gap_end_time,
              lg.gap_duration / 3600 AS gap_duration_hours -- Convert to hours
          FROM
              LongestGap lg
                  INNER JOIN
              area a ON lg.area = a.area;
          """, mapperResponseQuery10, crimeCode);
  }

  public List<Response11> findAllAreasThatHaveReceivedAtLeast1ReportInTheSameDayFor2GivenCrimesQuery11(
    final Integer crimeCode1, final Integer crimeCode2) {
    return jdbcTemplate
      .query(
        """
                       WITH FilteredCrimes AS (
              SELECT
                  rc.area,
                  rcc.crm_cd AS crime_code,
                  rc.date_reported AS report_date
              FROM
                  reported_crimes rc
                      INNER JOIN
                  reported_crimes_crime_codes rcc ON rc.dr_no = rcc.dr_no
              WHERE
                  rcc.crm_cd IN (510, 998) -- Replace with the 2 crime codes
          ),
          
              CrimeCounts AS (
                  SELECT
                      area,
                      crime_code,
                      report_date,
                      COUNT(*) AS report_count
                  FROM
                      FilteredCrimes
                  GROUP BY
                      area, crime_code, report_date
                  HAVING
                      COUNT(*) > 1 -- More than 1 report for the crime
              ),
          
              AggregatedAreas AS (
                  SELECT
                      c1.area,
                      c1.report_date
                  FROM
                      CrimeCounts c1
                          INNER JOIN
                      CrimeCounts c2
                      ON
                          c1.area = c2.area
                              AND c1.report_date = c2.report_date
                              AND c1.crime_code = ? -- Match crime 1
                              AND c2.crime_code = ? -- Match crime 2
              )
          
          SELECT
              aa.area,
              a.area_name,
              aa.report_date
          FROM
              AggregatedAreas aa
                  INNER JOIN
              area a ON aa.area = a.area
          ORDER BY
              aa.report_date, aa.area;
          """, mapperResponseQuery11, crimeCode1, crimeCode2);
  }

  public List<Response12> findRecordsForCrimesReportedOnTheSameDayForDifferentAreasUsingSameWeaponQuery12(
    final LocalDate from,
    final LocalDate to) {
    return jdbcTemplate
      .query(
        """
                                 WITH FilteredCrimes AS (
              SELECT
                  rc.area,
                  rc.date_reported AS report_date,
                  rc.weapon,
                  rc.dr_no
              FROM
                  reported_crimes rc
              WHERE
                  rc.date_reported >= ? AND rc.date_reported < ? -- Replace with your time range
                AND rc.weapon IS NOT NULL -- Exclude cases without a weapon
          ),
          
              CrimesGrouped AS (
                  SELECT
                      f1.report_date,
                      f1.weapon,
                      COUNT(DISTINCT f1.area) AS area_count,
                      COUNT(*) AS record_count
                  FROM
                      FilteredCrimes f1
                          JOIN
                      FilteredCrimes f2
                      ON
                          f1.weapon = f2.weapon
                              AND f1.report_date = f2.report_date
                              AND f1.area <> f2.area -- Ensure the areas are different
                  GROUP BY
                      f1.report_date, f1.weapon
                  HAVING
                      COUNT(DISTINCT f1.area) > 1 -- More than one area involved
              )
          
          SELECT
              report_date,
              weapon,
              w.weapon_desc,
              area_count,
              record_count
          FROM
              CrimesGrouped cg
                  LEFT JOIN
              weapons w ON cg.weapon = w.weapon_used_cd
          ORDER BY
              report_date, record_count DESC;
          """, mapperResponseQuery12, from, to);
  }
}
