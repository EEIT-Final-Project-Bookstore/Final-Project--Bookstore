package com.finalproject.ispan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalproject.ispan.domain.PaymentMethodsBean;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethodsBean, Integer> {
	PaymentMethodsBean findByPaymentMethodName(String paymentMethodName);
}
