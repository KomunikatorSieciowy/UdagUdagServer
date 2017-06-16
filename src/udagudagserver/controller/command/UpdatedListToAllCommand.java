package udagudagserver.controller.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import udagudagserver.controller.Controller;
import udagudagserver.controller.backcommand.MessageBackCommandData;
import udagudagserver.controller.backcommand.MessageToAllBackCommandData;
import udagudagserver.controller.backcommand.UpdatedListBackCommandData;
import udagudagserver.controller.backcommand.UpdatedListToAllBackCommandData;

public class UpdatedListToAllCommand implements Command {

	@Expose
	public String className = "UpdatedListToAllCommand";
	@Expose
	public String email;
	@Expose
	public boolean connected; // 'true' - has joined / 'false' - has left

	@Override
	public String execute() {
		if (connected == true) {
			Controller.getInstance().getEmailsList().add(email);
		} else {
			for (String nickFromList : Controller.getInstance().getEmailsList()) {
				if (email.equals(nickFromList)) {
					Controller.getInstance().getEmailsList().remove(nickFromList);
					break;
				}
			}
		}

		Gson gson = new GsonBuilder().create();
		String json = null;
		
		UpdatedListBackCommandData updatedListBackCommandData = new UpdatedListBackCommandData();
		updatedListBackCommandData.emailsList = Controller.getInstance().getEmailsList();
		json = gson.toJson(updatedListBackCommandData, UpdatedListBackCommandData.class);
		
		boolean success = Controller.getInstance().getTcpServer().sendBackCommandToAllClients(json);
		UpdatedListToAllBackCommandData updatedListToAllBackCommandData = new UpdatedListToAllBackCommandData();
		updatedListToAllBackCommandData.success = success;
		json = gson.toJson(updatedListToAllBackCommandData, UpdatedListToAllBackCommandData.class);

		return json;
	}
}