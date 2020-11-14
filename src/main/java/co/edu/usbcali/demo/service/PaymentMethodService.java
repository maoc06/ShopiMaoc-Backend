package co.edu.usbcali.demo.service;

import java.util.List;

import co.edu.usbcali.demo.domain.PaymentMethod;

public interface PaymentMethodService extends GenericService<PaymentMethod, Integer> {

	List<PaymentMethod> findByEnableTrue();

}
