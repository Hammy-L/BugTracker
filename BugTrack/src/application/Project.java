package application;

public class Project {
	String name;
	String date;
	String description;

	public Project() {
		name = null;
		date = null;
		description = null;
	}

	public Project(String n, String d, String desc) {
		name = n;
		date = d;
		description = desc;
	}

	public String getName() {
		return name;
	}

	public String getDate() {
		return date;
	}

	public String getDesc() {
		return description;
	}

	public void setName(String n) {
		this.name = n;
	}

	public void setDate(String d) {
		this.date = d;
	}

	public void setDesc(String desc) {
		this.description = desc;
	}
}
