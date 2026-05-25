package com.crimereporting.repository;

import com.crimereporting.model.CrimeReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CrimeReportRepository extends JpaRepository<CrimeReport, UUID> {
}
