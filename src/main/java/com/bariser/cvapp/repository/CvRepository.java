package com.bariser.cvapp.repository;

import com.bariser.cvapp.model.Cv;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CvRepository extends JpaRepository<Cv, Long> {
    Cv findCvByUserId(Long userId);
}
