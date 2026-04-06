package com.nm.finance.service.impl;

import com.nm.finance.entity.FinancialRecord;
import com.nm.finance.entity.RecordType;
import com.nm.finance.exception.BadRequestException;
import com.nm.finance.exception.ResourceNotFoundException;
import com.nm.finance.repository.FinancialRecordRepository;
import com.nm.finance.service.FinancialRecordService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FinancialRecordServiceImpl implements FinancialRecordService {
    private static final Logger log = LoggerFactory.getLogger(FinancialRecordServiceImpl.class);
    private final FinancialRecordRepository recordRepository;

    @Override
    public FinancialRecord create(FinancialRecord record) {
        log.info("Creating financial record for userId: {}", record.getUserId());
        validateRecord(record);
        return recordRepository.save(record);
    }

    @Override
    public Page<FinancialRecord> getAll(Pageable pageable) {
        log.info("Fetching records with pagination: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return recordRepository.findAll(pageable);
    }

    @Override
    public List<FinancialRecord> filterByType(RecordType type) {
        return recordRepository.findByType(type);
    }

    @Override
    public List<FinancialRecord> filterByCategory(String category) {
        return recordRepository.findByCategory(category);
    }

    @Override
    public List<FinancialRecord> filterByDate(LocalDate start, LocalDate end) {
        return recordRepository.findByDateBetween(start, end);
    }

    @Override
    public FinancialRecord update(Long id, FinancialRecord updated) {
        log.info("Updating record with id: {}", id);
        FinancialRecord record = recordRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Record not found with id: " + id));

        validateRecord(updated);

        record.setAmount(updated.getAmount());
        record.setType(updated.getType());
        record.setCategory(updated.getCategory());
        record.setDate(updated.getDate());
        record.setDescription(updated.getDescription());

        return recordRepository.save(record);
    }

    @Override
    public void delete(Long id) {
        log.warn("Deleting record with id: {}", id);
        FinancialRecord record = recordRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Record not found with id: " + id));

        recordRepository.delete(record);
    }

    private void validateRecord(FinancialRecord record) {

        if (record.getAmount() == null || record.getAmount() <= 0) {
            throw new BadRequestException("Amount must be greater than zero");
        }

        if (record.getType() == null) {
            throw new BadRequestException("Record type is required");
        }

        if (record.getCategory() == null || record.getCategory().isBlank()) {
            throw new BadRequestException("Category is required");
        }

        if (record.getDate() == null) {
            throw new BadRequestException("Date is required");
        }

        if (record.getUserId() == null) {
            throw new BadRequestException("UserId is required");
        }
    }
}