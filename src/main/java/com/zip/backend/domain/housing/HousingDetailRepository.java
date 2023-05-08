package com.zip.backend.domain.housing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HousingDetailRepository extends JpaRepository<HousingDetail, Long> {

}
