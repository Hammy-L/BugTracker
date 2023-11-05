package application;

public class Ticket {
	String projName;
	String title;
	String description;

	public Ticket(String t, String d) {
		projName = null;
		title = t;
		description = d;

	}

	public String getDescription() {
		return description;
	}

	public String getProjname() {
		return projName;
	}

	public String getTitle() {
		return title;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public void setProjname(String projname) {
		this.projName = projname;
	}
	public void setTitle(String title) {
		this.title = title;
	}

}
