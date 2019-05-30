package brian.example.boot.cloud.contract.consumer.model;

public class CustomerResponse {

	private Status status;
	private String message;

	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public enum Status{
		OK("OK"),
		FAIL("FAIL");
		
		private String statusDesc;
		
		private Status(String statusDesc) {
			this.statusDesc = statusDesc;
		};
		
		public String statusDesc() {
			return statusDesc;
		}
	}
}
