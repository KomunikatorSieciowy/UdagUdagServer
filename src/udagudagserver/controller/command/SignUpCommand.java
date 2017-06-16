package udagudagserver.controller.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import udagudagserver.controller.Controller;
import udagudagserver.controller.backcommand.SignUpBackCommandData;

public class SignUpCommand implements Command {

	public String className = "SignUpCommand";
    public String email;
    public String birthday;
    public String password;
    public String firstName;
    public String lastName;
    public String place;
    public String state;
	
	@Override
	public String execute() {

		boolean success = Controller.getInstance().getMessengerDAO().signUp(email, birthday, password, firstName, lastName, place, state);
		SignUpBackCommandData signUpBackCommandData = new SignUpBackCommandData();
		signUpBackCommandData.success = success;
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(signUpBackCommandData, SignUpBackCommandData.class);
		return json;
	}
}