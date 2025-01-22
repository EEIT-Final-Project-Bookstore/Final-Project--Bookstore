package bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookstore.domain.AuthorBean;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorBean, Integer> {
	  List<AuthorBean> findByAuthorNameContaining(String authorName);
}
