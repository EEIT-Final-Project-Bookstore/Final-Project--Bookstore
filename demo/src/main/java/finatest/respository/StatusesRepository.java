package finatest.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import finatest.domain.StatusBean;
@Repository
public interface StatusesRepository extends JpaRepository<StatusBean, Integer> {
}
