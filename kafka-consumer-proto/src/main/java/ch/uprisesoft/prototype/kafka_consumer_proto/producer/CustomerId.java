package ch.uprisesoft.prototype.kafka_consumer_proto.producer;

public class CustomerId {

	private Long id;


	public CustomerId() {
	}

	public CustomerId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return String.format(
				"CustomerId[ID='%s']",
				id);
	}
}
