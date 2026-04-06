package com.nm.finance.controller;

import com.nm.finance.entity.FinancialRecord;
import com.nm.finance.entity.RecordType;
import com.nm.finance.service.FinancialRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
public class FinancialRecordController {

    private final FinancialRecordService service;

    @PostMapping
    public FinancialRecord create(@Valid @RequestBody FinancialRecord record) {
        return service.create(record);
    }

    @GetMapping
    public Page<FinancialRecord> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("/type")
    public List<FinancialRecord> filterByType(@RequestParam RecordType type) {
        return service.filterByType(type);
    }

    @GetMapping("/category")
    public List<FinancialRecord> filterByCategory(@RequestParam String category) {
        return service.filterByCategory(category);
    }

    @GetMapping("/date")
    public List<FinancialRecord> filterByDate(
            @RequestParam String start,
            @RequestParam String end) {

        return service.filterByDate(LocalDate.parse(start), LocalDate.parse(end));
    }

    @PutMapping("/{id}")
    public FinancialRecord update(@PathVariable Long id, @Valid @RequestBody FinancialRecord record) {
        return service.update(id, record);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}