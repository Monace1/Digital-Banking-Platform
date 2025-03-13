package com.app.Account_Service.Service;

import com.app.Account_Service.Dto.CustomerDto;
import com.app.Account_Service.Entity.Customer;
import com.app.Account_Service.Repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDto createCustomer(CustomerDto customerDto) {
        if (customerRepository.existsByNationalid(customerDto.getNationalid())) {
            throw new RuntimeException("Customer with this national ID already exists.");
        }

        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setNationalid(customerDto.getNationalid());
        customer.setEmail(customerDto.getEmail());
        customer.setPhoneNumber(customerDto.getPhoneNumber());

        Customer savedCustomer = customerRepository.save(customer);
        return convertToDto(savedCustomer);
    }

    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CustomerDto getCustomerByNationalId(String nationalid) {
        Customer customer = customerRepository.findByNationalid(nationalid)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return convertToDto(customer);
    }

    public CustomerDto updateCustomer(String nationalid, CustomerDto updatedCustomerDto) {
        Customer existingCustomer = customerRepository.findByNationalid(nationalid)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        existingCustomer.setName(updatedCustomerDto.getName());
        existingCustomer.setEmail(updatedCustomerDto.getEmail());
        existingCustomer.setPhoneNumber(updatedCustomerDto.getPhoneNumber());

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return convertToDto(updatedCustomer);
    }

    @Transactional
    public void deleteCustomer(String nationalid) {
        if (!customerRepository.existsByNationalid(nationalid)) {
            throw new RuntimeException("Customer not found");
        }
        customerRepository.deleteByNationalid(nationalid);
    }

    private CustomerDto convertToDto(Customer customer) {
        return new CustomerDto(
                customer.getNationalid(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhoneNumber()
        );
    }
}
