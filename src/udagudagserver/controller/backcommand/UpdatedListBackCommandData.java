package udagudagserver.controller.backcommand;

import java.util.List;

public class UpdatedListBackCommandData {

	public String className = "UpdatedListBackCommand";
	public List<String> emailsList;
	
	public UpdatedListBackCommandData(List<String> emailsList) {
		this.emailsList = emailsList;
	}
}