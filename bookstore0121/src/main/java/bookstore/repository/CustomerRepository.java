package customers.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import customers.domain.CustomerBean;

public interface CustomerRepository extends JpaRepository<CustomerBean, Long>, CustomerDAO {

	Optional<CustomerBean> findByUsername(String username);

	boolean existsByUsername(String username);

	void deleteByUsername(String username);

	boolean existsByEmail(String email);
}
