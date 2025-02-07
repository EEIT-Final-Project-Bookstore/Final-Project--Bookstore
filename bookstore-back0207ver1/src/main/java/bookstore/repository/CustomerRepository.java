package bookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bookstore.domain.CustomerBean;

public interface CustomerRepository extends JpaRepository<CustomerBean, Long> {

	Optional<CustomerBean> findByUsername(String username);

	Optional<CustomerBean> findById(Long customerId);

	boolean existsByUsername(String username);

	void deleteByUsername(String username);

	boolean existsByEmail(String email);
}
