package com.example.tanmeyah.facility;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Optional;

public interface FacilityRepository extends JpaRepository<Facility, Long> {

    Optional<Facility> findFacilityByFacilityName(String facilityName);
}
