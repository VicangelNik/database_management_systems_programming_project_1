package com.vicangel.database_management_systems_pp1.infrastructure.postgress.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.vicangel.database_management_systems_pp1.model.ReportedCrime;
import com.vicangel.database_management_systems_pp1.rest.dto.request.BoundingBoxRequest;
import com.vicangel.database_management_systems_pp1.rest.dto.response.CommonCrimePerAreaForDate;
import com.vicangel.database_management_systems_pp1.rest.dto.response.CrimeCodeDescriptionWithTotalResponse;
import com.vicangel.database_management_systems_pp1.rest.dto.response.TotalCrimesPerHourResponse;
import com.vicangel.database_management_systems_pp1.rest.dto.response.TotalReportsForCrimeCodeBetweenReportedDateResponse;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReportedCrimeRepository {

  private final JdbcTemplate jdbcTemplate;

  public List<ReportedCrime> findReportedCrimes() {
    return jdbcTemplate
      .query("SELECT * from reported_crimes", BeanPropertyRowMapper.newInstance(ReportedCrime.class));
  }

  public List<CrimeCodeDescriptionWithTotalResponse> findTotalReportsPerCrimeBetweenTimeOccurrence(
    LocalDateTime from,
    LocalDateTime to) {
    return jdbcTemplate
      .query("""
               SELECT cc.crm_cd crime_code, cc.crm_cd_desc crime_description, count(*) total
               FROM reported_crimes rc
                        inner join public.reported_crimes_crime_codes rccc ON rc.dr_no = rccc.dr_no
                        inner join public.crime_codes cc ON cc.crm_cd = rccc.crm_cd
               WHERE rc.occ_date_time BETWEEN ? AND ?
               GROUP BY cc.crm_cd
               """, BeanPropertyRowMapper.newInstance(CrimeCodeDescriptionWithTotalResponse.class), from, to);
  }

  public List<TotalReportsForCrimeCodeBetweenReportedDateResponse> findTotalReportsForCrimeCodeBetweenReportedDate(
    Integer crimeCode, LocalDate from, LocalDate to) {
    return jdbcTemplate
      .query("""
               SELECT count(*) total
               FROM reported_crimes rc
                        inner join public.reported_crimes_crime_codes rccc ON rc.dr_no = rccc.dr_no
                        inner join public.crime_codes cc ON cc.crm_cd = rccc.crm_cd
               WHERE cc.crm_cd = ? AND
                   rc.date_reported BETWEEN ? AND ?
               """, BeanPropertyRowMapper.newInstance(TotalReportsForCrimeCodeBetweenReportedDateResponse.class), crimeCode, from, to);
  }

  public List<CommonCrimePerAreaForDate> findCommonCrimePerAreaForDate(@NonNull final LocalDate date) {
    return jdbcTemplate
      .query("""
               SELECT cc.crm_cd crime_code, cc.crm_cd_desc crime_description, a.area_name, count(cc.crm_cd) total
               FROM reported_crimes rc
                        inner join public.reported_crimes_crime_codes rccc ON rc.dr_no = rccc.dr_no
                        inner join public.crime_codes cc ON cc.crm_cd = rccc.crm_cd
                        inner join public.area a on rc.area = a.area
               WHERE rc.date_reported = ?
               GROUP BY a.area, cc.crm_cd
               ORDER BY total DESC
               limit 1
               """, BeanPropertyRowMapper.newInstance(CommonCrimePerAreaForDate.class), date);
  }

  public List<TotalCrimesPerHourResponse> findAverageCrimeOccurrencePerHour(final LocalDateTime from,
                                                                            final LocalDateTime to) {
    return jdbcTemplate
      .query(
        """
                SELECT DATE_TRUNC('hour', rc.occ_date_time) per_hour, count(cc.crm_cd) total_crimes
          FROM reported_crimes rc
          inner join public.reported_crimes_crime_codes rccc on rc.dr_no = rccc.dr_no
          inner join public.crime_codes cc on cc.crm_cd = rccc.crm_cd
          WHERE rc.occ_date_time BETWEEN ? AND ?
          GROUP BY per_hour
          ORDER BY per_hour
          """, BeanPropertyRowMapper.newInstance(TotalCrimesPerHourResponse.class), from, to);
  }

  public List<CrimeCodeDescriptionWithTotalResponse> findMostCommonCrimeInCoordinatesBoundingBox(LocalDateTime date,
                                                                                                 BoundingBoxRequest request) {
    return jdbcTemplate
      .query(
        """
                  SELECT cc.crm_cd, cc.crm_cd_desc, count(cc.crm_cd) total_crimes
                  FROM reported_crimes rc
                    INNER JOIN public.reported_crimes_crime_codes rccc ON rc.dr_no = rccc.dr_no
                    INNER JOIN public.crime_codes cc ON cc.crm_cd = rccc.crm_cd
                    INNER JOIN public.location l ON rc.location = l.dr_no
                  WHERE  rc.occ_date_time = ?
                    AND l.latitude >= ? AND l.latitude <= ?
                    AND l.longitude >= ? AND l.longitude <= ?
                  GROUP BY cc.crm_cd
                  ORDER BY total_crimes DESC
                  LIMIT 1;
          """, BeanPropertyRowMapper.newInstance(CrimeCodeDescriptionWithTotalResponse.class),
        date,
        request.southLatitude(),
        request.northLatitude(),
        request.westLongitude(),
        request.eastLongitude());
  }
}
