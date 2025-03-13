package com.app.Account_Service.Controller;

import com.app.Account_Service.Dto.CustomerDto;
import com.app.Account_Service.Service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "", allowedHeaders = "", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok(customerService.createCustomer(customerDto));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{nationalid}")
    public ResponseEntity<CustomerDto> getCustomerByNationalId(@PathVariable String nationalid) {
        return ResponseEntity.ok(customerService.getCustomerByNationalId(nationalid));
    }

    @PutMapping("/{nationalid}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable String nationalid, @RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok(customerService.updateCustomer(nationalid, customerDto));
    }

    @DeleteMapping("/{nationalid}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String nationalid) {
        customerService.deleteCustomer(nationalid);
        return ResponseEntity.ok("Customer deleted successfully");
    }
}
