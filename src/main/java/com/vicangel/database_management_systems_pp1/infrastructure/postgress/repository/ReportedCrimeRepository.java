package com.vicangel.database_management_systems_pp1.infrastructure.postgress.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.vicangel.database_management_systems_pp1.model.ReportedCrime;
import com.vicangel.database_management_systems_pp1.rest.dto.response.CommonCrimePerAreaForDate;
import com.vicangel.database_management_systems_pp1.rest.dto.response.TotalReportsForCrimeCodeBetweenReportedDateResponse;
import com.vicangel.database_management_systems_pp1.rest.dto.response.TotalReportsPerCrimeBetweenTimeOccurrenceResponse;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReportedCrimeRepository {

  private final JdbcTemplate jdbcTemplate;

  public List<ReportedCrime> findReportedCrimes() {
    return jdbcTemplate
      .query("SELECT * from reported_crimes", BeanPropertyRowMapper.newInstance(ReportedCrime.class));
  }

  public List<TotalReportsPerCrimeBetweenTimeOccurrenceResponse> findTotalReportsPerCrimeBetweenTimeOccurrence(
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
               """, BeanPropertyRowMapper.newInstance(TotalReportsPerCrimeBetweenTimeOccurrenceResponse.class), from, to);
  }

  public List<TotalReportsForCrimeCodeBetweenReportedDateResponse> findTotalReportsForCrimeCodeBetweenReportedDate(
    Integer crimeCode, LocalDateTime from, LocalDateTime to) {
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
}
