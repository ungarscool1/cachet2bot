package tk.ungarscool1.cachet2bot;

public enum Status {
	OPERATIONAL(1), PERFORMANCE_ISSUES(2), PARTIAL_OUTAGE(3), MAJOR_OUTAGE(4);
	
	private int statusCode;
	
	public int getCode() {
		return this.statusCode;
	}
	
	private Status(int statusCode) {
		this.statusCode = statusCode;
	}
	
}
