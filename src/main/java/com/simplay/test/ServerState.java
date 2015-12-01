package com.simplay.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import net.minecraft.entity.player.EntityPlayer;

public class ServerState {
	
	private static ServerState instance = null;
	private final List<EntityPlayer> activePlayers;
	
	private final String pigeonSecret = "5f1cb22eb645b6f1d738543a0007922ced4b0f30";
	private final int pigeonPort = 21337;
	
	private List<EntityPlayer> activityList() {
		return activePlayers;
	}
	
	private ServerState() {
		this.activePlayers = new LinkedList<EntityPlayer>();
		Timer uploadCheckerTimer = new Timer(true);
		uploadCheckerTimer.scheduleAtFixedRate(
		    new TimerTask() {
		      public void run() { 
		    	  notifyPigeon(); 
		      }
		    }, 0, 10 * 1000);
	}
	
	public void notifyPigeon() {
		Socket echoSocket;
		try {
			echoSocket = new Socket("localhost", pigeonPort);
		    PrintWriter out =
			        new PrintWriter(echoSocket.getOutputStream(), true);

		    out.println(prettyPlayerNames());
		    System.out.println("Notifying Sir Pigeon...");
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
	private String prettyPlayerNames() {
		
	    String p_contents = "";
	    for (EntityPlayer p : activityList()) {
	    	p_contents += p.getDisplayName() + " [" + PlayerUtil.getInstance().getPlayerPing(p) + "ms]" +";";
	    }
		
		String header = rubySerializedHashKeyValue("header", "mss") + ",";
		String secret = rubySerializedHashKeyValue("secret", pigeonSecret) + ",";
		String content = rubySerializedHashKeyValue("content", p_contents);
		String serializedHash = "{" + header + secret + content + "}";
		return serializedHash;
	}
	
	private String rubySerializedHashKeyValue(String key, String value) {
		return "\""+key+"\":\""+value+"\"";
	}

	
	public static synchronized void appendToActivityList(EntityPlayer player) {
		if (!hasPlayerInActivityList(player)) {
			getInstance().activityList().add(player);
			getInstance().notifyPigeon();
		}
	}
	
	public static synchronized void removeFromActivityList(EntityPlayer player) {
		if (hasPlayerInActivityList(player)) {
			List<EntityPlayer> players = getInstance().activityList();
			players.remove(player);
			getInstance().notifyPigeon();
		}
	}
	
	public static synchronized boolean hasPlayerInActivityList(EntityPlayer player) {
		for (EntityPlayer p : getActivityList()) {
			if ( p.getDisplayName().equals(player.getDisplayName())) {
				return true;
			}
		}
		return false;
	}
	
	public static synchronized List<EntityPlayer> getActivityList() {
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
		List<EntityPlayer> activityList = ServerState.getActivityList();
		for (EntityPlayer p : activityList) {
			System.out.println(p.getDisplayName() + " is online");
		}
	}
}
