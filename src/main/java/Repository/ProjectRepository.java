package Repository;

import com.task.TeamManager.model.Project;
import com.task.TeamManager.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;


public interface ProjectRepository extends JpaRepository<Project, Long> {

    Page<Project> findByNameContainingIgnoreCase(String name, Pageable pageable);
    long countByEndDateIsNullOrEndDateAfter(LocalDate date);
    Page<Project> findByProjectManager(User projectManager, Pageable pageable);
}