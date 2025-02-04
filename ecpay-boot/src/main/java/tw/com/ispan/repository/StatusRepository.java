package tw.com.ispan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.com.ispan.domain.StatusBean;

public interface StatusRepository extends JpaRepository<StatusBean, Integer> {

}
