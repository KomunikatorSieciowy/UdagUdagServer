package udagudagserver.controller.tcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import udagudagserver.controller.Controller;

public class TcpServer extends Thread {

	private Controller controller;

	private ServerSocket serverSocket;
	private List<TcpMiniServer> tcpMiniServers;
	
	private volatile boolean running = true;

	public TcpServer() {
		try {
			serverSocket = new ServerSocket(8086);
		} catch (IOException e) {
			System.out.println("Cannot create SERVER. The port is already in use.");
		}
		tcpMiniServers = new ArrayList<TcpMiniServer>();
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	@Override
	public void run(){
		while (running) {
			try {
				Socket clientSocket = serverSocket.accept();
				BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());

				System.out.println("[CLIENT" + clientSocket.getInetAddress() + "/] has joined.");

				TcpMiniServer tcpMiniServer = new TcpMiniServer(clientSocket, inFromClient, outToClient, this);
				tcpMiniServer.start();
				tcpMiniServers.add(tcpMiniServer);

			} catch (IOException e) {
				System.out.println("Problem while connecting to new CLIENT."); // Very unlikely.
			}
		}
		// Before shutDownServer() method is invoked, we have to stop the thread with setting 'running' to false.
		shutDownServer();
	}
	
	private void setRunning(boolean running) {
		this.running = running;
	}

	private void shutDownServer() {
		
		shutDownAllMiniServers();

		if (serverSocket != null && !serverSocket.isClosed()) {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void shutDownAllMiniServers() {
		for (TcpMiniServer ms : tcpMiniServers) {
			ms.shutDownWithoutRemoving();
		}
		tcpMiniServers.clear();
	}
	
	public boolean sendBackCommandToAllClients(String json) {
		boolean success = true;
		for (TcpMiniServer ms : tcpMiniServers) {
			if (ms.sendBackCommandToClient(json) == false) {
				success = false;
			}
		}
		return success;
	}

	public String sendCommandToController(String json) {
		return controller.executeCommand(json);
	}

	public void removeMiniServer(TcpMiniServer tcpMiniServer) {
		int index = tcpMiniServers.indexOf(tcpMiniServer);
		tcpMiniServers.remove(index);
	}
}