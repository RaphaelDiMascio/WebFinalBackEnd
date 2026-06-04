package mygroup.web_final_back_end.repositories;

import mygroup.web_final_back_end.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
	Optional<Category> findByName(String name);
	List<Category> findByNameContainingIgnoreCase(String name);

	@Query("SELECT c FROM Category c WHERE LOWER(c.name) = LOWER(:name) AND (c.user IS NULL OR c.user.id = :userId)")
	Optional<Category> findByNameIgnoreCaseAndUserOrGlobal(@Param("name") String name, @Param("userId") UUID userId);

	@Query("SELECT c FROM Category c WHERE c.user IS NULL OR c.user.id = :userId")
	List<Category> findAllByUserOrGlobal(@Param("userId") UUID userId);

	@Query("SELECT c FROM Category c WHERE (c.user IS NULL OR c.user.id = :userId) AND LOWER(c.name) LIKE :pattern")
	List<Category> findAllByUserOrGlobalAndName(@Param("userId") UUID userId, @Param("pattern") String pattern);
}
