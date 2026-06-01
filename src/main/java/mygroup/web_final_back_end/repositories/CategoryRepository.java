package mygroup.web_final_back_end.repositories;

import mygroup.web_final_back_end.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
