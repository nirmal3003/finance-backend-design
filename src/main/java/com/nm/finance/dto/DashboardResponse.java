package com.nm.finance.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {

    private Double totalIncome;
    private Double totalExpense;
    private Double netBalance;

    private Map<String, Double> categoryTotals;

    private List<?> recentTransactions;
}