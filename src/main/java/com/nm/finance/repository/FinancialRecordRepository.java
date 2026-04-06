package com.nm.finance.repository;

import com.nm.finance.entity.FinancialRecord;
import com.nm.finance.entity.RecordType;
import com.nm.finance.entity.FinancialRecord;
import com.nm.finance.entity.RecordType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

    List<FinancialRecord> findByType(RecordType type);

    List<FinancialRecord> findByCategory(String category);

    List<FinancialRecord> findByDateBetween(LocalDate start, LocalDate end);

    @Query("SELECT SUM(f.amount) FROM FinancialRecord f WHERE f.type = :type")
    Double getTotalByType(@Param("type") RecordType type);

    @Query("SELECT f.category, SUM(f.amount) FROM FinancialRecord f GROUP BY f.category")
    List<Object[]> getCategoryWiseTotals();

    List<FinancialRecord> findTop5ByOrderByDateDesc();
}
