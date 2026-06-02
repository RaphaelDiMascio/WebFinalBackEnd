package mygroup.web_final_back_end.services;

import mygroup.web_final_back_end.dto.DashboardSummaryDTO;
import java.util.UUID;

public interface DashboardService {
    DashboardSummaryDTO getSummary(UUID userId);
}
