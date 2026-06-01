package mygroup.web_final_back_end.repositories;

import mygroup.web_final_back_end.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
