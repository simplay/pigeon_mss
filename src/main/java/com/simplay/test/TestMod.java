package com.simplay.test;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = "ssm", name = "Server Status Mod", version = "0.0.2", acceptableRemoteVersions = "*")
public class TestMod {
		
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		ServerStatusHandler statusHandler = new ServerStatusHandler();
		MinecraftForge.EVENT_BUS.register(statusHandler);
		FMLCommonHandler.instance().bus().register(statusHandler);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		ServerState.getInstance().notifyPigeon();
	}
	
	@EventHandler
	public void fmlLifeCycle(FMLServerStoppingEvent event) {
		ServerState.wipeActivityList();
	    System.out.println("Server stopping");
	        
	}
	
	@EventHandler
	public void fmlLifeCycle(FMLServerStoppedEvent event) {
		ServerState.wipeActivityList();
	    System.out.println("Server stopped");

	}
	
}
