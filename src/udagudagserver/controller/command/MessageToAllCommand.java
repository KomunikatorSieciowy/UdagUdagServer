package udagudagserver.controller.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import udagudagserver.controller.Controller;
import udagudagserver.controller.backcommand.MessageBackCommandData;
import udagudagserver.controller.backcommand.MessageToAllBackCommandData;

public class MessageToAllCommand implements Command {

	public String className = "MessageToAllCommand";
    public String message;
	
	@Override
	public String execute() {
		Gson gson = new GsonBuilder().create();
		String json = null;
		
		MessageBackCommandData messageBackCommandData = new MessageBackCommandData();
		messageBackCommandData.message = message;
		json = gson.toJson(messageBackCommandData, MessageBackCommandData.class);
		
		boolean success = Controller.getInstance().getTcpServer().sendBackCommandToAllClients(json);
		MessageToAllBackCommandData messageToAllBackCommandData = new MessageToAllBackCommandData();
		messageToAllBackCommandData.success = success;
		json = gson.toJson(messageToAllBackCommandData, MessageToAllBackCommandData.class);
		return json;
	}
}