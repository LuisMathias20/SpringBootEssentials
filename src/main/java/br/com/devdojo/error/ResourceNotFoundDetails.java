package br.com.devdojo.error;

public class ResourceNotFoundDetails {

	private String title;
	
	private int status;
	
	private String  detail;
	
	private long timestamp;
	
	private String developerMessage;
	
	private ResourceNotFoundDetails () {
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public long getTimeStamp() {
		return timestamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timestamp = timeStamp;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}
	
	public static final class Builder {
		private String title;
		
		private int status;
		
		private String  detail;
		
		private long timestamp;
		
		private String developerMessage;
		
		private Builder() {
			
		}
		
		public static Builder newBuilder() {
			return new Builder();
		}
		
		public Builder title(String title) {
			this.title = title;
			return this;
		}
		
		public Builder status(int status) {
			this.status = status;
			return this;
		}
		
		public Builder detail(String detail) {
			this.detail = detail;
			return this;
		}
		
		public Builder timestamp(Long timestamp) {
			this.timestamp = timestamp;
			return this;
		}
		
		public Builder developerMessage(String developerMessage) {
			this.developerMessage = developerMessage;
			return this;
		}
		
		public ResourceNotFoundDetails build() {
			ResourceNotFoundDetails resourceNotFoundDetails = new ResourceNotFoundDetails();
			
			resourceNotFoundDetails.developerMessage = this.developerMessage;
			resourceNotFoundDetails.title = this.title;
			resourceNotFoundDetails.detail = this.detail;
			resourceNotFoundDetails.timestamp = this.timestamp;
			resourceNotFoundDetails.status = this.status;
			
			return resourceNotFoundDetails;
		}
		
		
	}
}
