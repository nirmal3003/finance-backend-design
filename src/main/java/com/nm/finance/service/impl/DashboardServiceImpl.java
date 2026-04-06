package com.nm.finance.service.impl;

import com.nm.finance.dto.DashboardResponse;
import com.nm.finance.entity.RecordType;
import com.nm.finance.repository.FinancialRecordRepository;
import com.nm.finance.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private static final Logger log = LoggerFactory.getLogger(DashboardServiceImpl.class);
    private final FinancialRecordRepository repository;

    @Override
    public DashboardResponse getDashboard() {
        log.info("Fetching dashboard summary");
        Double income = Optional.ofNullable(repository.getTotalByType(RecordType.INCOME)).orElse(0.0);
        Double expense = Optional.ofNullable(repository.getTotalByType(RecordType.EXPENSE)).orElse(0.0);
        log.debug("Total Income: {}, Total Expense: {}", income, expense);
        Double net = income - expense;

        Map<String, Double> categoryMap = new HashMap<>();
        List<Object[]> categoryData = repository.getCategoryWiseTotals();

        for (Object[] obj : categoryData) {
            categoryMap.put((String) obj[0], (Double) obj[1]);
        }
        log.debug("Category-wise totals: {}", categoryMap);
        var recent = repository.findTop5ByOrderByDateDesc();
        log.info("Dashboard data prepared successfully");
        return new DashboardResponse(income, expense, net, categoryMap, recent);
    }
}
