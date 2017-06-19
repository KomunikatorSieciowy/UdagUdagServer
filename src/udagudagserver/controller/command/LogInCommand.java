package udagudagserver.controller.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import udagudagserver.controller.Controller;
import udagudagserver.controller.backcommand.LogInBackCommandData;

public class LogInCommand implements Command {

	public String className = "LogInCommand";
	public String email;
	public String password;

	@Override
	public String execute() {
		boolean success = Controller.getInstance().getMessengerDAO().logIn(email, password);
		LogInBackCommandData logInBackCommandData = new LogInBackCommandData(success);
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(logInBackCommandData, LogInBackCommandData.class);
		return json;
	}
}