package mygroup.web_final_back_end.services;

import java.util.Map;
import java.util.UUID;

public interface DashboardService {
    Map<String, Object> getSummary(UUID userId, Integer year, Integer month);
}
