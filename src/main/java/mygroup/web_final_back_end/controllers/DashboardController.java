package mygroup.web_final_back_end.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import mygroup.web_final_back_end.dto.DashboardSummaryDTO;
import mygroup.web_final_back_end.services.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<DashboardSummaryDTO> getSummary(@RequestParam UUID userId) {
        return ResponseEntity.ok(dashboardService.getSummary(userId));
    }
}
