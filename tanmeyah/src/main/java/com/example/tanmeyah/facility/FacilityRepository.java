package com.example.tanmeyah.facility;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.EnableAsync;

public interface FacilityRepository extends JpaRepository<Facility,Long> {
}
