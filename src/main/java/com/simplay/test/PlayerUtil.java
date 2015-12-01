package com.simplay.test;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class PlayerUtil {
	
	private static PlayerUtil instance = null;
	
	/**
	 * @return ServerState singleton
	 */
	public static PlayerUtil getInstance() {
		if (instance == null) {
			instance = new PlayerUtil();
		}
		return instance;
	}
	
	private PlayerUtil() {}
	
	public int processCommandPlayer(EntityPlayer player) {
		return ((EntityPlayerMP) player).ping;
	}
	
	
	private void displayAllOnlinePlayerNames() {
		List<EntityPlayerMP> players = allPlayersOnServer();
		for (EntityPlayerMP player : players) {
			System.out.println(player.getDisplayName() + " is online");
		}
	}
	
	/**
	 * Returns a list of all players currently online on this server
	 * @return [List] players logged in server
	 */
	private List<EntityPlayerMP> allPlayersOnServer() {
		return MinecraftServer.getServer().getConfigurationManager().playerEntityList;
	}
}
