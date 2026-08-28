package com.captrojo.resadditae.main;

import com.captrojo.resadditae.compatibility.ModList;
import com.captrojo.resadditae.compatibility.helper.NEIHlpr;
import com.captrojo.resadditae.config.ClientConfig;
import com.captrojo.resadditae.config.common.GeneralConfig;
import com.captrojo.resadditae.creativetab.ModCreativeTab;
import com.captrojo.resadditae.creativetab.TabBlocks;
import com.captrojo.resadditae.creativetab.TabColors;
import com.captrojo.resadditae.creativetab.TabEquipment;
import com.captrojo.resadditae.creativetab.TabMaterials;
import com.captrojo.resadditae.creativetab.TabNature;
import com.captrojo.resadditae.creativetab.TabUtility;
import com.captrojo.resadditae.entity.EntityThrownHalberd;
import com.captrojo.resadditae.entity.monster.EntitySnowEye;
import com.captrojo.resadditae.gui.hud.HUDElements;
import com.captrojo.resadditae.item.ModItems;
import com.captrojo.resadditae.render.RenderHlpr;
import com.captrojo.resadditae.render.block.BlockRenderIDs;
import com.captrojo.resadditae.render.block.RenderDirectionalBlock;
import com.captrojo.resadditae.render.block.RenderMossLayer;
import com.captrojo.resadditae.render.block.RenderMultiFence;
import com.captrojo.resadditae.render.block.RenderMultiStair;
import com.captrojo.resadditae.render.entity.RenderSnowEye;
import com.captrojo.resadditae.render.entity.RenderThrownHalberd;
import com.captrojo.resadditae.render.item.RenderItemHalberd;
import com.captrojo.resadditae.render.tileentity.RenderTEMultiSpawner;
import com.captrojo.resadditae.render.tileentity.RenderTESnowDungeonSpawner;
import com.captrojo.resadditae.render.tileentity.RenderTEStructureBlock;
import com.captrojo.resadditae.render.tileentity.RenderTEVault;
import com.captrojo.resadditae.tileentity.TEMultiSpawner;
import com.captrojo.resadditae.tileentity.TESnowDungeonSpawner;
import com.captrojo.resadditae.tileentity.TEStructureBlock;
import com.captrojo.resadditae.tileentity.TEVault;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy
{
	@Override
	protected void loadConfig()
	{
		super.loadConfig();
		ClientConfig.loadAll();
	}
	
	@Override
	public void registerEventHandlers()
	{
		ClientEventHandler.instance = new ClientEventHandler();
		this.registerEventHandlers(ClientEventHandler.instance);
		
		InputEventHandler.instance = new InputEventHandler();
		FMLCommonHandler.instance().bus().register(InputEventHandler.instance);
	}

	@Override
	public void registerRenderers()
	{
		RenderItemHalberd renderhalberd = new RenderItemHalberd();
		MinecraftForgeClient.registerItemRenderer(ModItems.wood_halberd, renderhalberd);
		MinecraftForgeClient.registerItemRenderer(ModItems.stone_halberd, renderhalberd);
		MinecraftForgeClient.registerItemRenderer(ModItems.iron_halberd, renderhalberd);
		MinecraftForgeClient.registerItemRenderer(ModItems.gold_halberd, renderhalberd);
		MinecraftForgeClient.registerItemRenderer(ModItems.diamond_halberd, renderhalberd);
		if (GeneralConfig.netherite_tools) {
			MinecraftForgeClient.registerItemRenderer(ModItems.netherite_halberd, renderhalberd);
		}
		if (GeneralConfig.hbm_tools) {
			MinecraftForgeClient.registerItemRenderer(ModItems.steel_halberd, renderhalberd);
			MinecraftForgeClient.registerItemRenderer(ModItems.titanium_halberd, renderhalberd);
			MinecraftForgeClient.registerItemRenderer(ModItems.cobalt_halberd, renderhalberd);
			MinecraftForgeClient.registerItemRenderer(ModItems.starmetal_halberd, renderhalberd);
			MinecraftForgeClient.registerItemRenderer(ModItems.cmb_halberd, renderhalberd);
		}
		MinecraftForgeClient.registerItemRenderer(ModItems.silver_halberd, renderhalberd);
		MinecraftForgeClient.registerItemRenderer(ModItems.platinum_halberd, renderhalberd);
		MinecraftForgeClient.registerItemRenderer(ModItems.ancient_gem_halberd, renderhalberd);
		
		RenderingRegistry.registerBlockHandler(BlockRenderIDs.DIRECTIONAL.id, new RenderDirectionalBlock());
		RenderingRegistry.registerBlockHandler(BlockRenderIDs.MULTI_STAIR.id, new RenderMultiStair());
		RenderingRegistry.registerBlockHandler(BlockRenderIDs.MULTI_FENCE.id, new RenderMultiFence());
		RenderingRegistry.registerBlockHandler(BlockRenderIDs.MOSS_LAYER.id, new RenderMossLayer());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TEStructureBlock.class, new RenderTEStructureBlock());
		ClientRegistry.bindTileEntitySpecialRenderer(TEMultiSpawner.class, new RenderTEMultiSpawner());
		ClientRegistry.bindTileEntitySpecialRenderer(TESnowDungeonSpawner.class, new RenderTESnowDungeonSpawner());
		ClientRegistry.bindTileEntitySpecialRenderer(TEVault.class, new RenderTEVault());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityThrownHalberd.class, new RenderThrownHalberd());
		RenderingRegistry.registerEntityRenderingHandler(EntitySnowEye.class, new RenderSnowEye());
	}
	
	@Override
	public void initRenderingStuff()
	{
		HUDElements.init();
		
		RenderHlpr.texturemap_spells = new TextureMap(RenderHlpr.SPELL_TEXTUREMAP_ID, "textures/spells", true);
		ResourceLocation loc = new ResourceLocation("textures/atlas/spells.png");
		Minecraft.getMinecraft().renderEngine.loadTextureMap(loc, RenderHlpr.texturemap_spells);
	}
	
	@Override
	public void registerKeybinds()
	{
		InputEventHandler.registerKeybinds();
	}
	
	@Override
	public void createCreativeTabs()
	{
		TabBlocks.create();
		TabColors.create();
		TabNature.create();
		TabMaterials.create();
		TabEquipment.create();
		TabUtility.create();
		ModCreativeTab.fixSearchTab();
	}
	
	@Override
	public void handleNEIStuff()
	{
		NEIHlpr.registerHandlers();
		if (ModList.NEI.getVersionString().contains("GTNH")) {
			NEIHlpr.sendInfoToGTNH();
		}
		NEIHlpr.hideItems();
	}
	
	@Override
	public void displayHotbarStatusMsg(String msg, boolean rainbow)
	{
		Minecraft.getMinecraft().ingameGUI.func_110326_a(msg, rainbow);
	}
}
