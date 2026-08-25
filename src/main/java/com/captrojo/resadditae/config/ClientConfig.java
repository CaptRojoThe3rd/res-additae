package com.captrojo.resadditae.config;

import com.captrojo.resadditae.main.ResAdditae;

public class ClientConfig
{
	public static void loadAll()
	{
		ModConfig.load(ResAdditae.config_client);
		
		ModConfig.save();
	}
}
