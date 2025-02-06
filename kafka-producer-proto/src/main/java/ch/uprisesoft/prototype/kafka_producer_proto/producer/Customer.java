package ch.uprisesoft.prototype.kafka_producer_proto.producer;

public class Customer {

	//private Long id;

	private final String firstName;
	private final String lastName;

	public Customer(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

/*	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}*/

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	@Override
	public String toString() {
		return String.format(
				"Customer[firstName='%s', lastName='%s']",
				firstName, lastName);
	}
}
