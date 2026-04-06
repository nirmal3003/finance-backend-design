package com.nm.finance.service;

import com.nm.finance.entity.FinancialRecord;
import com.nm.finance.entity.RecordType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface FinancialRecordService {
    FinancialRecord create(FinancialRecord record);

    Page<FinancialRecord> getAll(Pageable pageable);

    List<FinancialRecord> filterByType(RecordType type);

    List<FinancialRecord> filterByCategory(String category);

    List<FinancialRecord> filterByDate(LocalDate start, LocalDate end);

    FinancialRecord update(Long id, FinancialRecord updated);

    void delete(Long id);
}
