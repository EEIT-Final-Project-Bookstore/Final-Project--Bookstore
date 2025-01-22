package bookstore.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookstore.domain.StatusBean;


@Repository
public interface StatusesRepository extends JpaRepository<StatusBean, Integer> {
}
