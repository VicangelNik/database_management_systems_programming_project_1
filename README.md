# Spring Web API

A simple Spring Boot Web API for accessing postgreSQL database.

## Features

The API provides endpoints for the following queries:

1. Find the total requests per “Crm Cd” that occurred within a specified time range and sort
   them in descending order.
2. Find the total requests per day for a specific “Crm Cd” and time range.
3. Find the most common crime committed regardless of code 1, 2, 3, and 4, per area for a
   specific day.
4. Find the average number of crimes occurred per hour (24 hours) for a specific date range.
5. Find the most common “Crm Cd” in a specified bounding box (as designated by GPS-coordinates)
   for a specific day.
6. Find the top-5 Area names with regards to total number of crimes reported per day for a
   specific date range. The same for Rpt Dist No.
7. Find the pair of crimes that has co-occurred in the area with the most reported incidents for
   a specific date range.
8. Find the second most common crime that has co-occurred with a particular crime for a specific
   date range.
9. Find the most common type of weapon used against victims depending on their group of age.
   The age groups are formed by bucketing ages every 5 years, e.g., 0 ≤ x < 5, 5 ≤ x < 10, etc..
10. Find the area with the longest time range without an occurrence of a specific crime. Include
    the time range in the results. The same for Rpt Dist No.
11. For 2 crimes of your choice, find all areas that have received more than 1 report on each of
    these 2 crimes on the same day. The 2 crimes could be for example: “CHILD ANNOYING
    (17YRS & UNDER)” or “THEFT OF IDENTITY”. Do not restrict yourself to just these 2
    specific types of crimes of course!
12. Find the number of division of records for crimes reported on the same day in different areas
    using the same weapon for a specific time range.
13. Find the crimes that occurred for a given number of times N on the same day, in the same
    area, using the same weapon, for a specific time range. Return the list of division of records
    numbers, the area name, the crime code description and the weapon description.

## Prerequisites

Before you begin, ensure you have the following installed:

- Java 21
- Gradle
- An IDE (e.g., IntelliJ IDEA, Eclipse)

## Getting Started

```bash
./gradlew clean build
./gradlew bootRun
```

### Clone the Repository

```bash
git clone https://github.com/VicangelNik/database_management_systems_programming_project_1.git
```

## References
* https://learnsql.com/blog/sql-rank-over-partition/
* https://www.sqlshack.com/sql-partition-by-clause-overview/
* https://bytescout.com/blog/postgresql-advanced-queries.html
* https://data.lacity.org/Public-Safety/Crime-Data-from-2020-to-Present/2nrs-mtv8/about_data
* https://www.overleaf.com/read/hzpdvgkwdsfw#65fd54
* https://neon.tech/postgresql/postgresql-window-function/postgresql-lead-function
* https://gist.github.com/jsundram/1251783