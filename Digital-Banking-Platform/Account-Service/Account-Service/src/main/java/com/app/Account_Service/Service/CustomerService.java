package com.app.Account_Service.Service;

import com.app.Account_Service.Dto.CustomerDto;
import com.app.Account_Service.Entity.Customer;
import com.app.Account_Service.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setPhoneNumber(customerDto.getPhoneNumber());

        Customer savedCustomer = customerRepository.save(customer);
        return convertToDto(savedCustomer);
    }

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CustomerDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return convertToDto(customer);
    }

    public CustomerDto updateCustomer(Long id, CustomerDto updatedCustomerDto) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        existingCustomer.setName(updatedCustomerDto.getName());
        existingCustomer.setEmail(updatedCustomerDto.getEmail());
        existingCustomer.setPhoneNumber(updatedCustomerDto.getPhoneNumber());

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return convertToDto(updatedCustomer);
    }

    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found");
        }
        customerRepository.deleteById(id);
    }

    private CustomerDto convertToDto(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhoneNumber()
        );
    }
}
