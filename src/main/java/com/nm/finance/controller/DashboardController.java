package com.nm.finance.controller;


import com.nm.finance.dto.DashboardResponse;
import com.nm.finance.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService service;

    @GetMapping("/summary")
    public DashboardResponse getSummary() {
        return service.getDashboard();
    }
}