package bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookstore.domain.CustomerBean;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerBean, Integer> {
}