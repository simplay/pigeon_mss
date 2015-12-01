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
	
	private final String pigeonSecret = "5f1cb22eb645b6f1d738543a0007922ced4b0f30";
	private final int pigeonPort = 21337;
	
	private List<String> activityList() {
		return activePlayerNames;
	}
	
	private ServerState() {
		this.activePlayerNames = new LinkedList<String>();
	}
	
	public void notifyPigeon() {
		Socket echoSocket;
		try {
			echoSocket = new Socket("localhost", pigeonPort);
		    PrintWriter out =
			        new PrintWriter(echoSocket.getOutputStream(), true);
		    String names = "";
		    for (String pName : activePlayerNames) {
		    	names += pName+";";
		    }
		    System.out.println("Notifying Sir Pigeon...");
		    out.println(prettyPlayerNames(names));
		    out.close();
		    echoSocket.close();
		} catch (UnknownHostException e) {
			System.out.println("Could not establish a connection.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could not establish a connection.");
			e.printStackTrace();
		}
	}
	
	public static void wipeActivityList() {
		getInstance().activityList().clear();
		getInstance().notifyPigeon();
	}
	
	/**
	 * Parse a serialized ruby hash
	 * @example "{\\"header\\":\\"text\\",\\"secret\\":\\"SECRET\\",\\"content\\":\\"0:\CONTENT\\"}\\n"
	 * @param pNames names of players currently online
	 * @return
	 */
	private String prettyPlayerNames(String pNames) {
		String header = rubySerializedHashKeyValue("header", "mss") + ",";
		String secret = rubySerializedHashKeyValue("secret", pigeonSecret) + ",";
		String content = rubySerializedHashKeyValue("content", pNames);
		String serializedHash = "{" + header + secret + content + "}";
		return serializedHash;
	}
	
	private String rubySerializedHashKeyValue(String key, String value) {
		return "\""+key+"\":\""+value+"\"";
	}

	
	public static synchronized void appendToActivityList(String playerName) {
		if (!hasPlayerInActivityList(playerName)) {
			getInstance().activityList().add(playerName);
			getInstance().notifyPigeon();
		}
	}
	
	public static synchronized void removeFromActivityList(String playerName) {
		if (hasPlayerInActivityList(playerName)) {
			List<String> playerNames = getInstance().activityList();
			playerNames.remove(playerName);
			getInstance().notifyPigeon();
		}
	}
	
	public static synchronized boolean hasPlayerInActivityList(String playerName) {
		for (String pName : getActivityList()) {
			if ( pName.equals(playerName)) {
				return true;
			}
		}
		return false;
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
	
	public static void displayActivityList() {
		List<String> activityList = ServerState.getActivityList();
		for (String pName : activityList) {
			System.out.println(pName + " is online");
		}
	}
}
