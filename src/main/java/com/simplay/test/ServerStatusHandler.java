package com.simplay.test;

import java.util.List;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class ServerStatusHandler {

	@SubscribeEvent
	public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
		String pName = event.player.getDisplayName();
		ServerState.removeFromActivityList(pName);
		System.out.println(pName + " has logged out.");
		displayActivityList();
	}
	
	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		String pName = event.player.getDisplayName();
		ServerState.appendToActivityList(pName);
		System.out.println(pName + " has logged in.");
		displayActivityList();
	}
	
	private void displayAllOnlinePlayerNames() {
		List<EntityPlayerMP> players = allPlayersOnServer();
		for (EntityPlayerMP player : players) {
			System.out.println(player.getDisplayName() + " is online");
		}
	}
	
	private void displayActivityList() {
		List<String> activityList = ServerState.getActivityList();
		for (String pName : activityList) {
			System.out.println(pName + " is online");
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
