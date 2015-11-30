package com.simplay.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

public class ServerState {
	
	private static ServerState instance = null;
	private final List<String> activePlayerNames;
	
	private List<String> activityList() {
		return activePlayerNames;
	}
	
	private ServerState() {
		this.activePlayerNames = new LinkedList<String>();
	}
	
	public void notifyPigeon() {
		Socket echoSocket;
		try {
			echoSocket = new Socket("localhost", 21337);
		    PrintWriter out =
			        new PrintWriter(echoSocket.getOutputStream(), true);
		    String names = "";
		    for (String pName : activePlayerNames) {
		    	names += pName+";";
		    }
		    out.println("players online: "+names);
		    out.close();
		    echoSocket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static synchronized void appendToActivityList(String playerName) {
		getInstance().activityList().add(playerName);
		getInstance().notifyPigeon();
	}
	
	public static synchronized void removeFromActivityList(String playerName) {
		 List<String> playerNames = getInstance().activityList();
		 playerNames.remove(playerName);
	}
	
	public static synchronized List<String> getActivityList() {
		return getInstance().activityList();
	}
	
	/**
	 * @return ServerState singleton
	 */
	public static ServerState getInstance() {
		if (instance == null) {
			instance = new ServerState();
		}
		return instance;
	}
}
