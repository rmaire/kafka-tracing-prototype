package ch.uprisesoft.prototype.kafka_producer_proto.api;


import ch.uprisesoft.prototype.kafka_producer_proto.producer.Customer;
import ch.uprisesoft.prototype.kafka_producer_proto.producer.CustomerProducerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	private final CustomerProducerService customerService;

	public CustomerController(CustomerProducerService customerService) {
		this.customerService = customerService;
	}

	@PostMapping()
	public Mono<Customer> getEmployeeById(@RequestBody Customer customer) {
		return customerService.send(customer);
	}
}
