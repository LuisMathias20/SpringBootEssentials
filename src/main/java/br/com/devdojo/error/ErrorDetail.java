package br.com.devdojo.error;

public class ErrorDetail {

	private String title;
	
	private int status;
	
	private String  detail;
	
	private long timestamp;
	
	private String developerMessage;


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
}
