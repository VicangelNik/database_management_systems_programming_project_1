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
import com.vicangel.database_management_systems_pp1.infrastructure.postgress.entity.MoCodesEntity;
import com.vicangel.database_management_systems_pp1.infrastructure.postgress.repository.MoCodesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(2)
public class MocodesDataLoader implements CommandLineRunner {

  private final MoCodesRepository moCodesRepository;

  @Value("classpath:csv/MO_CODES.csv")
  private Resource moCodesCsvResource;

  @Override
  public void run(String... args) throws Exception {
    log.info("Executing {}", MocodesDataLoader.class.getName());
    if (moCodesRepository.findAll().isEmpty()) {
      saveMoCodes();
    }
  }

  private void saveMoCodes() throws IOException {
    log.info("Saving mocodes..");
    moCodesRepository.saveAll(readMoCodesFile());
  }

  private List<MoCodesEntity> readMoCodesFile() throws IOException {
    log.info("Reading file: {}", moCodesCsvResource.getFilename());

    try (InputStream inputStream = moCodesCsvResource.getInputStream();
         MappingIterator<MoCodesEntity> iterator = new CsvMapper()
           .readerFor(MoCodesEntity.class)
           .with(getCsvSchema())
           .readValues(inputStream)) {
      return iterator.readAll();
    }
  }

  private static CsvSchema getCsvSchema() {
    return CsvSchema.builder()
      .addColumn("id", ColumnType.NUMBER)
      .addColumn("moCode", ColumnType.STRING)
      .build()
      .withHeader()
      .withColumnSeparator(';');
  }
}
