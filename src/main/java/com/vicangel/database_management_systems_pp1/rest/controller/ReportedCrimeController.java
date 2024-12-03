package com.vicangel.database_management_systems_pp1.rest.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vicangel.database_management_systems_pp1.infrastructure.postgress.repository.ReportedCrimeRepository;
import com.vicangel.database_management_systems_pp1.model.ReportedCrime;
import com.vicangel.database_management_systems_pp1.rest.dto.request.BetweenDateRequest;
import com.vicangel.database_management_systems_pp1.rest.dto.request.BoundingBoxRequest;
import com.vicangel.database_management_systems_pp1.rest.dto.response.CommonCrimePerAreaForDate;
import com.vicangel.database_management_systems_pp1.rest.dto.response.CrimeCodeDescriptionWithTotalResponse;
import com.vicangel.database_management_systems_pp1.rest.dto.response.TotalCrimesPerHourResponse;
import com.vicangel.database_management_systems_pp1.rest.dto.response.TotalReportsForCrimeCodeBetweenReportedDateResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
final class ReportedCrimeController {

  private static final DateTimeFormatter OCC_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private static final DateTimeFormatter REPORTED_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  private final ReportedCrimeRepository repository;

  @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<ReportedCrime>> findAllReportedCrimes() {

    final List<ReportedCrime> reportedCrimes = repository.findReportedCrimes();

    return new ResponseEntity<>(reportedCrimes, HttpStatus.OK);
  }

  /**
   * Find the total reports per “Crm Cd” that occurred within a specified time range and sort
   * them in descending order.
   */
  @PostMapping(value = "/per-crimes", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<CrimeCodeDescriptionWithTotalResponse>> findTotalReportsPerCrimeBetweenTimeOccurrence(
    @RequestBody final BetweenDateRequest request) {

    return ResponseEntity.ok(repository.findTotalReportsPerCrimeBetweenTimeOccurrence(LocalDateTime.parse(request.from(), OCC_FORMATTER),
                                                                                      LocalDateTime.parse(request.to(), OCC_FORMATTER)));
  }

  /**
   * Find the total reports per day for a specific “Crm Cd” and time range.
   */
  @PostMapping(value = "/crime/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<TotalReportsForCrimeCodeBetweenReportedDateResponse>> findTotalReportsPerCrimeBetweenTimeOccurrence(
    @PathVariable final Integer code,
    @RequestBody final BetweenDateRequest request) {

    return ResponseEntity.ok(repository.findTotalReportsForCrimeCodeBetweenReportedDate(code,
                                                                                        LocalDate.parse(request.from(), REPORTED_DATE_FORMATTER),
                                                                                        LocalDate.parse(request.to(), REPORTED_DATE_FORMATTER)));
  }

  /**
   * Find the most common crime committed regardless of code 1, 2, 3, and 4, per area for a
   * specific day.
   */
  @GetMapping(value = "/crime/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<CommonCrimePerAreaForDate>> findTotalReportsPerCrimeBetweenTimeOccurrence(
    @PathVariable final String date) {

    return ResponseEntity.ok(repository.findCommonCrimePerAreaForDate(LocalDate.parse(date, REPORTED_DATE_FORMATTER)));
  }

  /**
   * Find the average number of crimes occurred per hour (24 hours) for a specific date range.
   */
  @PostMapping(value = "/crime/per-hour", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<TotalCrimesPerHourResponse>> findAverageCrimeOccurrencePerHour(
    @RequestBody final BetweenDateRequest request) {

    return ResponseEntity.ok(repository.findAverageCrimeOccurrencePerHour(
      LocalDateTime.parse(request.from(), OCC_FORMATTER),
      LocalDateTime.parse(request.to(), OCC_FORMATTER)));
  }

  /**
   * Find the most common “Crm Cd” in a specified bounding box (as designated by GPS-coordinates)
   * for a specific day.
   */
  @PostMapping(value = "/crime/bounding", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<CrimeCodeDescriptionWithTotalResponse>> findMostCommonCrimeInCoordinatesBoundingBox(
    @RequestBody final BoundingBoxRequest request) {

    return ResponseEntity.ok(repository.findMostCommonCrimeInCoordinatesBoundingBox(LocalDateTime.parse(request.day(), OCC_FORMATTER), request));
  }
}
