package udagudagserver.controller;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import udagudagserver.controller.command.Command;
import udagudagserver.controller.command.LogInCommand;
import udagudagserver.controller.command.MessageToAllCommand;
import udagudagserver.controller.command.SignUpCommand;
import udagudagserver.controller.command.UpdatedListToAllCommand;
import udagudagserver.controller.dao.MessengerDAO;
import udagudagserver.controller.tcp.TcpServer;

public class Controller {

	private static Controller instance = new Controller();
	public static Controller getInstance() {
		return instance;
	}

	private MessengerDAO messengerDAO;
	private TcpServer tcpServer;
	private List<String> emailsList;

	private Controller() {
		messengerDAO = new MessengerDAO();
		tcpServer = new TcpServer();
		emailsList = new ArrayList<String>();
		tcpServer.start();
	}

	public String executeCommand(String json) {

		Gson gson = new GsonBuilder().create();
		Command command = null;

		if (json.contains("LogInCommand")) {
			command = gson.fromJson(json, LogInCommand.class);
		} else if (json.contains("MessageToAllCommand")) {
			command = gson.fromJson(json, MessageToAllCommand.class);
		} else if (json.contains("SignUpCommand")) {
			command = gson.fromJson(json, SignUpCommand.class);
		} else if (json.contains("UpdatedListToAllCommand")) {
			command = gson.fromJson(json, UpdatedListToAllCommand.class);
		}

		return command.execute();
	}

	public MessengerDAO getMessengerDAO() {
		return messengerDAO;
	}

	public TcpServer getTcpServer() {
		return tcpServer;
	}

	public List<String> getEmailsList() {
		return emailsList;
	}
}