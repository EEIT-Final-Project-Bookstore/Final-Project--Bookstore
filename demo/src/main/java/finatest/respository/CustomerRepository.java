package finatest.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import finatest.domain.CustomerBean;
@Repository
public interface CustomerRepository extends JpaRepository<CustomerBean, Integer> {
}