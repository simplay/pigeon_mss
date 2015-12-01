package com.simplay.test;

public class ActivityPlayer {
	
	private String name;
	private int ping;
	
	public ActivityPlayer(String name, int ping) {
		this.name = name;
		this.ping = ping;
	}
	
	public String toString() {
		return name + "," + ping;
	}
}
