package application;

public class Comment {
	String tickName;
	String description;
	String date;

	public Comment(String des, String dat) {
		tickName = "";
		description = des;
		date = dat;
	}

	public String getTickName() {
		return tickName;
	}

	public String getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public void setTickName(String t) {
		this.tickName = t;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
