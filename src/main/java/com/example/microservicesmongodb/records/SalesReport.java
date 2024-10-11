package com.example.microservicesmongodb.records;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Document(collection = "sales_reports")
public record SalesReport(
        @Id UUID id,
        DateRange dateRange,
        BigDecimal totalSales,
        List<BestSellingProduct> bestSellingProducts,
        List<WorstSellingProduct> worstSellingProducts
) {}

record DateRange(
        Instant startDate,
        Instant endDate
) {}

record BestSellingProduct(
        UUID productId, // Reference to products._id
        int unitsSold
) {}

record WorstSellingProduct(
        UUID productId, // Reference to products._id
        int unitsSold
) {}
