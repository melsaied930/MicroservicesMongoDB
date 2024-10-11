package com.example.microservicesmongodb.repository;

import com.example.microservicesmongodb.records.SalesReport;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface SalesReportRepository extends MongoRepository<SalesReport, UUID> {
    // Custom query methods (if needed)
    List<SalesReport> findByDateRangeStartDateGreaterThanEqualAndDateRangeEndDateLessThanEqual(Instant startDate, Instant endDate);
}