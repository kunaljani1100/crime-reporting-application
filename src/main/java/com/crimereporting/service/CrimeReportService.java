package com.crimereporting.service;

import com.crimereporting.dto.CrimeReportRequest;
import com.crimereporting.dto.CrimeReportResponse;
import com.crimereporting.model.CrimeReport;
import com.crimereporting.model.CrimeType;
import com.crimereporting.repository.CrimeReportRepository;
import org.springframework.stereotype.Service;

@Service
public class CrimeReportService {

    private final CrimeReportRepository crimeReportRepository;

    public CrimeReportService(CrimeReportRepository crimeReportRepository) {
        this.crimeReportRepository = crimeReportRepository;
    }

    public CrimeReportResponse reportCrime(CrimeReportRequest request) {
        CrimeReport crimeReport = new CrimeReport(
                CrimeType.fromValue(request.getCrimeType()),
                request.getDateOfCrime(),
                request.getTimeOfCrime(),
                request.getDescription()
        );

        CrimeReport savedReport = crimeReportRepository.save(crimeReport);

        return new CrimeReportResponse(
                savedReport.getIncidentIdentifier(),
                savedReport.getCrimeType().getValue(),
                savedReport.getDateOfCrime(),
                savedReport.getTimeOfCrime(),
                savedReport.getDescription()
        );
    }
}
