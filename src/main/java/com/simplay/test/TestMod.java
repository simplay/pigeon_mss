package com.simplay.test;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = "ssm", name = "Server Status Mod", version = "0.0.1", acceptableRemoteVersions = "*")
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
	public void postInit(FMLPostInitializationEvent event) {}
	
}
