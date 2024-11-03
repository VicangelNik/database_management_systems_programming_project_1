package com.vicangel.database_management_systems_pp1.infrastructure.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType;
import com.vicangel.database_management_systems_pp1.infrastructure.postgress.entity.ReportingDistrictsEntity;
import com.vicangel.database_management_systems_pp1.infrastructure.postgress.repository.ReportingDistrictsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(1)
public class ReportingDistrictDataLoader implements CommandLineRunner {

  private final ReportingDistrictsRepository reportingDistrictsRepository;

  @Value("classpath:csv/LAPD_Reporting_Districts.csv")
  private Resource reportingDistrictsFile;

  @Override
  public void run(String... args) throws Exception {
    log.info("Executing {}", ReportingDistrictDataLoader.class.getName());
    if (reportingDistrictsRepository.findAll().isEmpty()) {
      saveReportingDistricts();
    }
  }

  private void saveReportingDistricts() throws IOException {
    log.info("Saving reporting districts..");
    reportingDistrictsRepository.saveAll(readReportingDistrictsFile());
  }

  private List<ReportingDistrictsEntity> readReportingDistrictsFile() throws IOException {
    log.info("Reading file: {}", reportingDistrictsFile.getFilename());

    try (InputStream inputStream = reportingDistrictsFile.getInputStream();
         MappingIterator<ReportingDistrictsEntity> iterator = new CsvMapper()
           .readerFor(ReportingDistrictsEntity.class)
           .with(getCsvSchema())
           .readValues(inputStream)) {
      return iterator.readAll();
    }
  }

  private static CsvSchema getCsvSchema() {
    return CsvSchema.builder()
      .addColumn("fid", ColumnType.NUMBER)
      .addColumn("repDist", ColumnType.NUMBER)
      .addColumn("prec", ColumnType.NUMBER)
      .addColumn("apRec", ColumnType.STRING)
      .addColumn("bureau", ColumnType.STRING)
      .addColumn("basicCar", ColumnType.STRING)
      .addColumn("agency", ColumnType.STRING)
      .addColumn("name", ColumnType.STRING)
      .addColumn("shapeLeng", ColumnType.NUMBER)
      .addColumn("abbrevApr", ColumnType.STRING)
      .addColumn("shapeArea", ColumnType.NUMBER)
      .addColumn("shapeLength", ColumnType.NUMBER)
      .build()
      .withHeader()
      .withColumnSeparator(',');
  }
}
