package com.dauphine.web_final_back_end.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.dauphine.web_final_back_end.services.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/dashboard")
@Tag(name = "Dashboard Management", description = "Endpoints for retrieving aggregated dashboard data")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    @Operation(summary = "Get dashboard summary", description = "Retrieve aggregated financial summary data for a user, optionally filtered by year and month")
    public ResponseEntity<Map<String, Object>> getSummary(
            @RequestParam UUID userId,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month) {
        return ResponseEntity.ok(dashboardService.getSummary(userId, year, month));
    }
}
