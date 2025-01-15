package finatest.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import finatest.domain.BooksBean;
@Repository
public interface BooksRepository extends JpaRepository<BooksBean, Integer> {
}
