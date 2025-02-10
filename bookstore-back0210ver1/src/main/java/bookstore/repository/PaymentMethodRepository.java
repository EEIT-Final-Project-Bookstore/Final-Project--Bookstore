package bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bookstore.domain.PaymentMethodsBean;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethodsBean, Integer> {
	PaymentMethodsBean findByPaymentMethodName(String paymentMethodName);
}
