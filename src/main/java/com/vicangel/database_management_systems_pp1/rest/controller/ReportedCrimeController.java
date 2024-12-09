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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vicangel.database_management_systems_pp1.infrastructure.postgress.repository.ReportedCrimeRepository;
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

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
class ReportedCrimeController implements ReportedCrimeControllerOpenApi {

  private static final DateTimeFormatter OCC_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private static final DateTimeFormatter REPORTED_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  private final ReportedCrimeRepository repository;

  @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<List<ReportedCrime>> findAllReportedCrimes() {

    final List<ReportedCrime> reportedCrimes = repository.findReportedCrimes();

    return new ResponseEntity<>(reportedCrimes, HttpStatus.OK);
  }

  @GetMapping(value = "/total-per-crime", produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<List<Response1>> findTotalReportsPerCrimeBetweenTimeOccurrence(
    @RequestParam final String from,
    @RequestParam final String to) {

    return ResponseEntity.ok(repository.findTotalReportsPerCrimeBetweenTimeOccurrenceQuery1(
      LocalDateTime.parse(from, OCC_FORMATTER),
      LocalDateTime.parse(to, OCC_FORMATTER)));
  }

  @GetMapping(value = "/crime/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<List<Response2>> findTotalReportsPerCrimeBetweenTimeOccurrence(
    @PathVariable final Integer code,
    @RequestParam final String from,
    @RequestParam final String to) {

    return ResponseEntity.ok(repository.findTotalReportsForCrimeCodePerDayBetweenReportedDateQuery2(
      code,
      LocalDate.parse(from, REPORTED_DATE_FORMATTER),
      LocalDate.parse(to, REPORTED_DATE_FORMATTER)));
  }

  @GetMapping(value = "/crime/area/common", produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<List<Response3>> findCommonCrimePerAreaForDate(
    @RequestParam final String day) {

    return ResponseEntity.ok(repository.findCommonCrimePerAreaForDateQuery3(LocalDate.parse(day, REPORTED_DATE_FORMATTER)));
  }

  @GetMapping(value = "/crime/per-hour", produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<List<Response4>> findAverageCrimeOccurrencePerHour(
    @RequestParam final String from,
    @RequestParam final String to) {

    return ResponseEntity.ok(repository.findAverageCrimeOccurrencePerHourQuery4(
      LocalDate.parse(from, REPORTED_DATE_FORMATTER),
      LocalDate.parse(to, REPORTED_DATE_FORMATTER)));
  }

  @PostMapping(value = "/crime/bounding-box-coordinates", produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<List<Response5>> findMostCommonCrimeInCoordinatesBoundingBox(
    @RequestParam final String day,
    @RequestBody final BoundingBoxRequest request) {

    return ResponseEntity.ok(repository.findMostCommonCrimeInCoordinatesBoundingBoxQuery5(
      LocalDate.parse(day, REPORTED_DATE_FORMATTER), request));
  }

  @GetMapping(value = "/crime/area/top5", produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<List<AreaResponse6>> findTop5AreasWithMostCrimesReportedForSpecificDateRange(
    @RequestParam final String from,
    @RequestParam final String to) {

    return ResponseEntity.ok(repository.findTop5AreasWithMostCrimesReportedForSpecificDateRangeQuery6(
      LocalDate.parse(from, REPORTED_DATE_FORMATTER),
      LocalDate.parse(to, REPORTED_DATE_FORMATTER)));
  }

  @GetMapping(value = "/crime/district/top5", produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<List<DistrictResponse6>> findTop5DistrictsWithMostCrimesReportedForSpecificDateRange(
    @RequestParam final String from,
    @RequestParam final String to) {

    return ResponseEntity.ok(repository.findTop5DistrictsWithMostCrimesReportedForSpecificDateRangeQuery6(
      LocalDate.parse(from, REPORTED_DATE_FORMATTER),
      LocalDate.parse(to, REPORTED_DATE_FORMATTER)));
  }

  @GetMapping(value = "/crime/area/most-co-occurred", produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<Response7> findPairOfCrimesCoOccurredMostInMostReportedIncidentsArea(
    @RequestParam final String from,
    @RequestParam final String to) {
    return ResponseEntity.ok(repository.findPairOfCrimesCoOccurredMostInMostReportedIncidentsAreaQuery7(
      LocalDate.parse(from, REPORTED_DATE_FORMATTER),
      LocalDate.parse(to, REPORTED_DATE_FORMATTER)));
  }

  @GetMapping(value = "/crime/second/most-co-occurred", produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<Response8> findSecondMostCommonCrimeCoOccurredWithParticularCrime(
    @RequestParam final String from,
    @RequestParam final String to) {
    return ResponseEntity.ok(repository.findSecondMostCommonCrimeCoOccurredWithParticularCrimeQuery8(
      LocalDate.parse(from, REPORTED_DATE_FORMATTER),
      LocalDate.parse(to, REPORTED_DATE_FORMATTER)));
  }

  @GetMapping(value = "/crime/victim/weapon/most-common-used", produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<List<Response9>> findMostCommonWeaponTypeUsedAgainstVictimsDependingOnAgeGroup() {
    return ResponseEntity.ok(repository.findMostCommonWeaponTypeUsedAgainstVictimsDependingOnAgeGroupQuery9());
  }

  @GetMapping(value = "/crime/{code}/area", produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<List<Response10>> findAreaWithLongestTimeRangeWithoutSpecificCrimeOccurrence(
    @PathVariable final Integer code) {
    return ResponseEntity.ok(repository.findAreaWithLongestTimeRangeWithoutSpecificCrimeOccurrenceQuery10(code));
  }

  @GetMapping(value = "/crime/area/all", produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<List<Response11>> findAllAreasThatHaveReceivedAtLeast1ReportInTheSameDayFor2GivenCrimes(
    @RequestParam final Integer crimeCode1,
    @RequestParam final Integer crimeCode2) {
    return ResponseEntity.ok(repository.findAllAreasThatHaveReceivedAtLeast1ReportInTheSameDayFor2GivenCrimesQuery11(
      crimeCode1, crimeCode2));
  }

  @GetMapping(value = "/crime/area/weapon", produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<List<Response12>> findRecordsForCrimesReportedOnTheSameDayForDifferentAreasUsingSameWeapon(
    @RequestParam final String from,
    @RequestParam final String to) {
    return ResponseEntity.ok(repository.findRecordsForCrimesReportedOnTheSameDayForDifferentAreasUsingSameWeaponQuery12(
      LocalDate.parse(from, REPORTED_DATE_FORMATTER),
      LocalDate.parse(to, REPORTED_DATE_FORMATTER)));
  }
}
