package com.zip.backend.domain.housing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HousingRepositoryTest {

  @Autowired
  HousingRepository housingRepository;

  @Test
  public void saveHousing() {
    Housing housing = Housing.builder().HOUSE_MANAGE_NO(2023000114L).build();
    housingRepository.save(housing);
    System.out.println(housingRepository.findAll().size());
    System.out.println(housingRepository.findAll().get(0).getHOUSE_MANAGE_NO());
  }

  @Test
  public void createHousing() {
  }

}