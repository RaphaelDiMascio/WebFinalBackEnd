package mygroup.web_final_back_end.repositories;

import mygroup.web_final_back_end.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
	Optional<Category> findByName(String name);
	List<Category> findByNameContainingIgnoreCase(String name);
}
