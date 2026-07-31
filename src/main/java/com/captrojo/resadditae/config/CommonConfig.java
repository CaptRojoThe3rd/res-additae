package com.captrojo.resadditae.config;

import com.captrojo.resadditae.config.common.BiomeConfig;
import com.captrojo.resadditae.config.common.CommonFeaturesConfig;
import com.captrojo.resadditae.config.common.CommonStuffConfig;
import com.captrojo.resadditae.config.common.DebugConfig;
import com.captrojo.resadditae.config.common.GeneralConfig;
import com.captrojo.resadditae.config.common.PlayerConfig;
import com.captrojo.resadditae.config.common.WorldGenConfig;
import com.captrojo.resadditae.main.ResAdditae;

import net.minecraftforge.common.config.Configuration;

public class CommonConfig
{
	public static void loadAll()
	{
		ModConfig.load(ResAdditae.config_common);
		
		DebugConfig.load();
		GeneralConfig.load();
		PlayerConfig.load();
		BiomeConfig.load();
		CommonFeaturesConfig.load();
		CommonStuffConfig.load();
		WorldGenConfig.load();
		
		ModConfig.save();
	}
}
