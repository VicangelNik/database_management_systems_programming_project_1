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
import com.vicangel.database_management_systems_pp1.rest.dto.response.Response1;
import com.vicangel.database_management_systems_pp1.rest.dto.response.Response2;
import com.vicangel.database_management_systems_pp1.rest.dto.response.Response3;
import com.vicangel.database_management_systems_pp1.rest.dto.response.Response4;
import com.vicangel.database_management_systems_pp1.rest.dto.response.Response5;
import com.vicangel.database_management_systems_pp1.rest.dto.response.Response6;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
final class ReportedCrimeController implements ReportedCrimeControllerOpenApi {

  private static final DateTimeFormatter OCC_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private static final DateTimeFormatter REPORTED_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  private final ReportedCrimeRepository repository;

  @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<List<ReportedCrime>> findAllReportedCrimes() {

    final List<ReportedCrime> reportedCrimes = repository.findReportedCrimes();

    return new ResponseEntity<>(reportedCrimes, HttpStatus.OK);
  }

  @PostMapping(value = "/total-per-crime", produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<List<Response1>> findTotalReportsPerCrimeBetweenTimeOccurrence(
    @RequestBody final BetweenDateRequest request) {

    return ResponseEntity.ok(repository.findTotalReportsPerCrimeBetweenTimeOccurrence(LocalDateTime.parse(request.from(), OCC_FORMATTER),
                                                                                      LocalDateTime.parse(request.to(), OCC_FORMATTER)));
  }

  @PostMapping(value = "/crime/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<List<Response2>> findTotalReportsPerCrimeBetweenTimeOccurrence(
    @PathVariable final Integer code,
    @RequestBody final BetweenDateRequest request) {

    return ResponseEntity.ok(repository.findTotalReportsForCrimeCodeBetweenReportedDate(code,
                                                                                        LocalDate.parse(request.from(), REPORTED_DATE_FORMATTER),
                                                                                        LocalDate.parse(request.to(), REPORTED_DATE_FORMATTER)));
  }

  @GetMapping(value = "/crime/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<List<Response3>> findTotalReportsPerCrimeBetweenTimeOccurrence(
    @PathVariable final String date) {

    return ResponseEntity.ok(repository.findCommonCrimePerAreaForDate(LocalDate.parse(date, REPORTED_DATE_FORMATTER)));
  }

  @PostMapping(value = "/crime/per-hour", produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<List<Response4>> findAverageCrimeOccurrencePerHour(
    @RequestBody final BetweenDateRequest request) {

    return ResponseEntity.ok(repository.findAverageCrimeOccurrencePerHour(
      LocalDateTime.parse(request.from(), OCC_FORMATTER),
      LocalDateTime.parse(request.to(), OCC_FORMATTER)));
  }

  @PostMapping(value = "/crime/bounding-box-coordinates", produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<List<Response5>> findMostCommonCrimeInCoordinatesBoundingBox(
    @RequestBody final BoundingBoxRequest request) {

    return ResponseEntity.ok(repository.findMostCommonCrimeInCoordinatesBoundingBox(LocalDateTime.parse(request.day(), OCC_FORMATTER), request));
  }

  @PostMapping(value = "/crime/area/top5", produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<List<Response6>> findTop5AreasWithMostCrimesReportedForSpecificDateRange(
    @RequestBody final BetweenDateRequest request) {

    return ResponseEntity.ok(repository.findTop5AreasWithMostCrimesReportedForSpecificDateRange(LocalDate.parse(request.from(), REPORTED_DATE_FORMATTER),
                                                                                                LocalDate.parse(request.to(), REPORTED_DATE_FORMATTER)));
  }

  @PostMapping(value = "/crime/district/top5", produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<List<Response6>> findTop5DistrictsWithMostCrimesReportedForSpecificDateRange(
    @RequestBody final BetweenDateRequest request) {

    return ResponseEntity.ok(repository.findTop5DistrictsWithMostCrimesReportedForSpecificDateRange(LocalDate.parse(request.from(), REPORTED_DATE_FORMATTER),
                                                                                                    LocalDate.parse(request.to(), REPORTED_DATE_FORMATTER)));
  }
}
