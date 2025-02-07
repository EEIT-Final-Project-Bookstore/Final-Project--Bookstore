package bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bookstore.domain.StatusBean;

public interface StatusesRepository extends JpaRepository<StatusBean, Integer> {
    StatusBean findByDetailedStatus(String detailedStatus);
}
