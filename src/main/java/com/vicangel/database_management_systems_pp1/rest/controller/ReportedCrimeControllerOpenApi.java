package com.vicangel.database_management_systems_pp1.rest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.vicangel.database_management_systems_pp1.model.ReportedCrime;
import com.vicangel.database_management_systems_pp1.rest.dto.request.BetweenDateRequest;
import com.vicangel.database_management_systems_pp1.rest.dto.request.BoundingBoxRequest;
import com.vicangel.database_management_systems_pp1.rest.dto.response.Response1;
import com.vicangel.database_management_systems_pp1.rest.dto.response.Response2;
import com.vicangel.database_management_systems_pp1.rest.dto.response.Response3;
import com.vicangel.database_management_systems_pp1.rest.dto.response.Response4;
import com.vicangel.database_management_systems_pp1.rest.dto.response.Response5;
import com.vicangel.database_management_systems_pp1.rest.dto.response.Response6;

public interface ReportedCrimeControllerOpenApi {

  /**
   * Find all, not in project's questions
   */
  ResponseEntity<List<ReportedCrime>> findAllReportedCrimes();

  /**
   * 1. Find the total reports per “Crm Cd” that occurred within a specified time range and sort
   * them in descending order.
   */
  ResponseEntity<List<Response1>> findTotalReportsPerCrimeBetweenTimeOccurrence(
    BetweenDateRequest request);

  /**
   * 2. Find the total reports per day for a specific “Crm Cd” and time range.
   */
  ResponseEntity<List<Response2>> findTotalReportsPerCrimeBetweenTimeOccurrence(
    Integer code, BetweenDateRequest request);

  /**
   * 3. Find the most common crime committed regardless of code 1, 2, 3, and 4, per area for a specific day.
   */
  ResponseEntity<List<Response3>> findTotalReportsPerCrimeBetweenTimeOccurrence(String date);

  /**
   * 4. Find the average number of crimes occurred per hour (24 hours) for a specific date range.
   */
  ResponseEntity<List<Response4>> findAverageCrimeOccurrencePerHour(BetweenDateRequest request);

  /**
   * 5. Find the most common “Crm Cd” in a specified bounding box (as designated by GPS-coordinates) for a specific day.
   */
  ResponseEntity<List<Response5>> findMostCommonCrimeInCoordinatesBoundingBox(
    BoundingBoxRequest request);

  /**
   * 6.1 Find the top-5 Area names in regard to total number of crimes reported per day for a specific date range.
   */
  ResponseEntity<List<Response6>> findTop5AreasWithMostCrimesReportedForSpecificDateRange(
    BetweenDateRequest request);

  /**
   * 6.2 Find the top-5 Area names in regard to total number of crimes reported per day for a specific date range.
   */
  ResponseEntity<List<Response6>> findTop5DistrictsWithMostCrimesReportedForSpecificDateRange(
    BetweenDateRequest request);

  /**
   * 7. Find the pair of crimes that has co-occurred in the area with the most reported incidents for
   * a specific date range.
   */

  /**
   * 8. Find the second most common crime that has co-occurred with a particular crime for a specific
   * date range.
   */

  /**
   * 9. Find the most common type of weapon used against victims depending on their group of age.
   * The age groups are formed by bucketing ages every 5 years, e.g., 0 ≤ x < 5, 5 ≤ x < 10, etc..
   */

  /**
   * 10. Find the area with the longest time range without an occurrence of a specific crime. Include
   * the time range in the results. The same for Rpt Dist No.
   */

  /**
   * 11. For 2 crimes of your choice, find all areas that have received more than 1 report on each of
   * these 2 crimes on the same day. The 2 crimes could be for example: “CHILD ANNOYING
   * (17YRS & UNDER)” or “THEFT OF IDENTITY”. Do not restrict yourself to just these 2
   * specific types of crimes of course!
   */

  /**
   * 12. Find the number of division of records for crimes reported on the same day in different areas
   * using the same weapon for a specific time range.
   */

  /**
   * 13. Find the crimes that occurred for a given number of times N on the same day, in the same
   * area, using the same weapon, for a specific time range. Return the list of division of records
   * numbers, the area name, the crime code description and the weapon description.
   */
}
