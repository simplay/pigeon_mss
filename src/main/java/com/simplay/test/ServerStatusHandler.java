package com.simplay.test;

import java.util.List;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class ServerStatusHandler {

	@SubscribeEvent
	public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
		EntityPlayer player = event.player;
		String pName = player.getDisplayName();
		ServerState.removeFromActivityList(pName);
		System.out.println(pName + " has logged out.");
		ServerState.displayActivityList();
	}
	
	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		EntityPlayer player = event.player;
		String pName = player.getDisplayName();
		ServerState.appendToActivityList(pName);
		System.out.println(pName + " has logged in.");
		ServerState.displayActivityList();
	}
	

}
