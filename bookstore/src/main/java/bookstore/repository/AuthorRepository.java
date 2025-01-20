package bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookstore.domain.AuthorBean;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorBean, Integer> {
}
