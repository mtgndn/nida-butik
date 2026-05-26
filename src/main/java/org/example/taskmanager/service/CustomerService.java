package org.example.taskmanager.service;

import org.example.taskmanager.dto.CustomerPurchaseSummary;
import org.example.taskmanager.dto.CustomerRequest;
import org.example.taskmanager.entity.Customer;
import org.example.taskmanager.entity.Gender;
import org.example.taskmanager.exception.BusinessRuleException;
import org.example.taskmanager.exception.ResourceNotFoundException;
import org.example.taskmanager.repository.CustomerRepository;
import org.example.taskmanager.repository.OrderRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public CustomerService(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomer(Long id) {
        return findCustomer(id);
    }

    public List<CustomerPurchaseSummary> getTopEightCustomersByGender(Gender gender) {
        return orderRepository.findTopCustomersByPurchasedQuantity(gender, PageRequest.of(0, 8));
    }

    @Transactional
    public Customer createCustomer(CustomerRequest request) {
        if (customerRepository.existsByEmailIgnoreCase(request.email())) {
            throw new BusinessRuleException("Bu e-posta ile kayitli musteri var.");
        }
        Customer customer = new Customer();
        applyRequest(customer, request);
        return customerRepository.save(customer);
    }

    @Transactional
    public Customer updateCustomer(Long id, CustomerRequest request) {
        Customer customer = findCustomer(id);
        customerRepository.findByEmailIgnoreCase(request.email()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new BusinessRuleException("Bu e-posta baska bir musteriye ait.");
            }
        });
        applyRequest(customer, request);
        return customerRepository.save(customer);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        customerRepository.delete(findCustomer(id));
    }

    Customer findCustomer(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Musteri bulunamadi: " + id));
    }

    private void applyRequest(Customer customer, CustomerRequest request) {
        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setEmail(request.email());
        customer.setPhone(request.phone());
        customer.setGender(request.gender());
        customer.setAddress(request.address());
        customer.setCity(request.city());
    }
}
