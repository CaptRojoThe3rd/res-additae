package com.captrojo.resadditae.main;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.command.CommandFill;
import com.captrojo.resadditae.command.CommandFlightSpeed;
import com.captrojo.resadditae.command.CommandRADebug;
import com.captrojo.resadditae.command.CommandRADev;
import com.captrojo.resadditae.compatibility.CommonBlocks;
import com.captrojo.resadditae.compatibility.CommonItems;
import com.captrojo.resadditae.compatibility.CommonStuffStatus;
import com.captrojo.resadditae.compatibility.ModList;
import com.captrojo.resadditae.compatibility.ModOreDict;
import com.captrojo.resadditae.compatibility.OrderedEquipmentLists;
import com.captrojo.resadditae.compatibility.helper.EtFuturumHlpr;
import com.captrojo.resadditae.config.common.CommonFeaturesConfig;
import com.captrojo.resadditae.config.common.WorldGenConfig;
import com.captrojo.resadditae.crafting.CraftingRecipes;
import com.captrojo.resadditae.crafting.FurnaceRecipes;
import com.captrojo.resadditae.crafting.StonecutterRecipe;
import com.captrojo.resadditae.entity.ModEntities;
import com.captrojo.resadditae.gui.GuiHandler;
import com.captrojo.resadditae.item.ModItems;
import com.captrojo.resadditae.magic.spell.Spells;
import com.captrojo.resadditae.packet.toclient.PacketDisplayAlert;
import com.captrojo.resadditae.packet.toclient.PacketPerformanceInfo;
import com.captrojo.resadditae.packet.toclient.PacketPlayerExtProps;
import com.captrojo.resadditae.packet.toclient.PacketSetFlightSpeed;
import com.captrojo.resadditae.packet.toclient.PacketSpellFeedback;
import com.captrojo.resadditae.packet.toserver.PacketGuiContainerAction;
import com.captrojo.resadditae.packet.toserver.PacketLearnSpell;
import com.captrojo.resadditae.packet.toserver.PacketNBTControl;
import com.captrojo.resadditae.packet.toserver.PacketPlayerSettings;
import com.captrojo.resadditae.packet.toserver.PacketUseSpell;
import com.captrojo.resadditae.stats.ModAchievements;
import com.captrojo.resadditae.stats.ModStats;
import com.captrojo.resadditae.tileentity.ModTileEntities;
import com.captrojo.resadditae.world.WorldProviderDepths;
import com.captrojo.resadditae.world.biome.ModBiomes;
import com.captrojo.resadditae.world.gen.ModWorldGen;
import com.captrojo.resadditae.world.gen.structure.ModStructures;
import com.captrojo.resadditae.world.loot.ModLoot;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.init.Items;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraftforge.common.DimensionManager;

@Mod(
	modid = ResAdditae.MOD_ID,
	name = ResAdditae.NAME,
	version = ResAdditae.VERSION,
	acceptableRemoteVersions = ResAdditae.VERSIONS_ACCEPTED
)
public class ResAdditae
{
	@Mod.Instance
	public static ResAdditae instance;
	
	public static final String NAME = "Res Additae";
	public static final String MOD_ID = "resadditae";
	
//	public static final String VERSION = "1.0.0";
//	public static final String VERSIONS_ACCEPTED = "1.0.*";
//	public static final String VERSION_NAME = "1.0.0";
	public static final String VERSION = "X0009";
	public static final String VERSIONS_ACCEPTED = "X0009";
	public static final String VERSION_NAME = "Build #0009";
	
	public static final Logger LOG = LogManager.getLogger(MOD_ID);
	
	@SidedProxy(
		clientSide = "com.captrojo.resadditae.main.ClientProxy", 
		serverSide = "com.captrojo.resadditae.main.CommonProxy"
	)
	public static CommonProxy proxy;
	
	public static SimpleNetworkWrapper network;
	
	public static String dir_minecraft;
	public static String dir_config;
	public static String dir_crash_reports;
	public static String dir_resadditae;
	
	public static String dir_structures;
	public static String dir_loots;
	
	public static File config_common;
	public static File config_client;
	
	public static boolean testing_mode = Boolean.getBoolean("resadditae.testing");
	
	public static boolean common_items_error = false;
	
	/**
	 * This function is completely unncessary and is unused, but I'm leaving it here
	 * because it is funny
	 */
	public static Side getSideUnsafely(Side assumed_upon_failure)
	{
		Thread thr = Thread.currentThread();
		if (thr.getName().equals("Client thread")) {
			return Side.CLIENT;
		}
		if (thr.getName().equals("Server thread")) {
			return Side.SERVER;
		}
		return assumed_upon_failure;
	}
	
	/**
	 * Add "resadditae:" to the string if another identifier is not already present.
	 */
	public static String ident(String str)
	{
		if (str.contains(":")) {
			return str;
		}
		return MOD_ID + ":" + str;
	}
	
	/**
	 * Create a resource location, calling ResAdditae.ident on the path.
	 */
	public static ResourceLocation resource(String path)
	{
		return new ResourceLocation(ident(path));
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		LOG.info(String.format("%s %s for Minecraft 1.7.10", NAME, VERSION_NAME));
		
		/* This shouldn't break (hopefully) */
		dir_minecraft = event.getModConfigurationDirectory().getParent();
		dir_config = event.getModConfigurationDirectory().toString() + File.separator + "resadditae";
		dir_crash_reports = dir_minecraft + File.separator + "crash-reports";
		dir_resadditae = dir_minecraft + File.separator + "resadditae";
		
		dir_structures = dir_resadditae + File.separator + "structures";
		dir_loots = dir_resadditae + File.separator + "loot";
		
		final String[] af = {dir_config, dir_crash_reports, dir_structures, dir_loots};
		for (String s : af) {
			File f = new File(s);
			if (!f.exists()) {
				f.mkdirs();
			}
		}
		
		config_common = new File(dir_config + File.separator + "common.cfg");
		config_client = new File(dir_config + File.separator + "client.cfg");
		proxy.loadConfig();
		
		/* Make gold equipment good (between silver and platinum) */
		ReflectionHelper.setPrivateValue(ToolMaterial.class, ToolMaterial.GOLD, 2, "harvestLevel", "field_78001_f");
		ReflectionHelper.setPrivateValue(ToolMaterial.class, ToolMaterial.GOLD, 777, "maxUses", "field_78002_g");
		ReflectionHelper.setPrivateValue(ToolMaterial.class, ToolMaterial.GOLD, 2.5f, "damageVsEntity", "field_78011_i");
		Items.golden_pickaxe.setMaxDamage(777);
		Items.golden_axe.setMaxDamage(777);
		Items.golden_shovel.setMaxDamage(777);
		Items.golden_sword.setMaxDamage(777);
		Items.golden_hoe.setMaxDamage(777);
		ReflectionHelper.setPrivateValue(ItemTool.class, (ItemTool) Items.golden_pickaxe, 4.5f, "damageVsEntity", "field_77865_bY");
		ReflectionHelper.setPrivateValue(ItemTool.class, (ItemTool) Items.golden_axe, 5.5f, "damageVsEntity", "field_77865_bY");
		ReflectionHelper.setPrivateValue(ItemTool.class, (ItemTool) Items.golden_shovel, 3.5f, "damageVsEntity", "field_77865_bY");
		ReflectionHelper.setPrivateValue(ItemSword.class, (ItemSword) Items.golden_sword, 6.5f, "field_150934_a");
		ReflectionHelper.setPrivateValue(ArmorMaterial.class, ArmorMaterial.GOLD, 24, "maxDamageFactor", "field_78048_f");
		ReflectionHelper.setPrivateValue(ArmorMaterial.class, ArmorMaterial.GOLD, new int[] {2, 7, 6, 2}, "damageReductionAmountArray", "field_78049_g");
		Items.golden_helmet.setMaxDamage(11 * 24);
		Items.golden_chestplate.setMaxDamage(16 * 24);
		Items.golden_leggings.setMaxDamage(15 * 24);
		Items.golden_boots.setMaxDamage(13 * 24);
		ReflectionHelper.setPrivateValue(ItemArmor.class, (ItemArmor) Items.golden_helmet, 2, "damageReduceAmount", "field_77879_b");
		ReflectionHelper.setPrivateValue(ItemArmor.class, (ItemArmor) Items.golden_chestplate, 7, "damageReduceAmount", "field_77879_b");
		ReflectionHelper.setPrivateValue(ItemArmor.class, (ItemArmor) Items.golden_leggings, 6, "damageReduceAmount", "field_77879_b");
		ReflectionHelper.setPrivateValue(ItemArmor.class, (ItemArmor) Items.golden_boots, 2, "damageReduceAmount", "field_77879_b");
		
		/* Allow trapdoors to float */
		BlockTrapDoor.disableValidation = true;
		
		/* Make extreme hills actually extreme */
		BiomeGenBase.extremeHills.rootHeight += 0.6f;
		BiomeGenBase.extremeHillsPlus.rootHeight += 0.6f;
		BiomeGenBase.getBiome(BiomeGenBase.extremeHills.biomeID + 128).rootHeight += 0.6f;
		BiomeGenBase.getBiome(BiomeGenBase.extremeHillsPlus.biomeID + 128).rootHeight += 0.6f;
		
		/* Make ice mountains taller */
		BiomeGenBase.iceMountains.rootHeight += 1.0f;
		
		Spells.init();
		
		ModItems.initItems();
		ModItems.registerItems();
		
		ModBlocks.initBlocks();
		ModBlocks.registerBlocks();
		ModTileEntities.registerTileEntities();
		
		ModOreDict.registerOres();
		
		ModEntities.register();
		
		ModStructures.load();
		DimensionManager.registerProviderType(WorldGenConfig.depths_dimension_id, WorldProviderDepths.class, true);
		DimensionManager.registerDimension(WorldGenConfig.depths_dimension_id, WorldGenConfig.depths_dimension_id);
		ModWorldGen.instance = new ModWorldGen();
		GameRegistry.registerWorldGenerator(ModWorldGen.instance, 1);
		ModBiomes.initBiomes();
		ModBiomes.registerBiomes();
		
		if (!ModList.VILLAGE_NAMES.isLoaded() && !MapGenVillage.villageSpawnBiomes.contains(BiomeGenBase.taiga)) {
			MapGenVillage.villageSpawnBiomes.add(BiomeGenBase.taiga);
		}
		
		proxy.registerEventHandlers();
		
		network = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);
		
		network.registerMessage(PacketSetFlightSpeed.HandlerClient.class, PacketSetFlightSpeed.class, 0x00, Side.CLIENT);
		network.registerMessage(PacketPlayerExtProps.HandlerClient.class, PacketPlayerExtProps.class, 0x01, Side.CLIENT);
		network.registerMessage(PacketDisplayAlert.HandlerClient.class, PacketDisplayAlert.class, 0x02, Side.CLIENT);
		network.registerMessage(PacketPerformanceInfo.HandlerClient.class, PacketPerformanceInfo.class, 0x03, Side.CLIENT);
		network.registerMessage(PacketSpellFeedback.HandlerClient.class, PacketSpellFeedback.class, 0x04, Side.CLIENT);

		network.registerMessage(PacketNBTControl.HandlerServer.class, PacketNBTControl.class, 0x80, Side.SERVER);
		network.registerMessage(PacketPlayerSettings.HandlerServer.class, PacketPlayerSettings.class, 0x81, Side.SERVER);
		network.registerMessage(PacketUseSpell.HandlerServer.class, PacketUseSpell.class, 0x82, Side.SERVER);
		network.registerMessage(PacketGuiContainerAction.HandlerServer.class, PacketGuiContainerAction.class, 0x83, Side.SERVER);
		network.registerMessage(PacketLearnSpell.HandlerServer.class, PacketLearnSpell.class, 0x84, Side.SERVER);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		/* We need to wait for other mods to register their ores */
		ModBlocks.doPostRegistrationSetup();
		ModOreDict.registerModdedOres();
		
		CommonBlocks.values();
		CommonItems.values();
		if (common_items_error) {
			String msg = "Couldn't find common item(s). Ensure that you have configured Res Additae and/or your other mods correctly. ";
			if (CommonStuffStatus.saveReports()) {
				msg += "A report has been saved to your crash reports folder ('" + CommonStuffStatus.report_file_path + "')";
			} else {
				msg += "A report could not be saved, so you will have to search the logs for more info.";
			}
			throw new IllegalStateException(msg);
		}
		OrderedEquipmentLists.init();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

		proxy.registerKeybinds();
		proxy.createCreativeTabs();
		
		proxy.registerRenderers();
		proxy.initRenderingStuff();
		
		CraftingRecipes.register();
		FurnaceRecipes.register();
		StonecutterRecipe.registerRecipes();
		
		ModLoot.load();
		
		ModStats.initStats();
		ModAchievements.initAchievements();
		
		if (ModList.ET_FUTURUM.isLoaded()) {
			EtFuturumHlpr.registerRecipes();
		}
		if (ModList.NEI.isLoaded()) {
			proxy.handleNEIStuff();
		}
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
	}
	
	@EventHandler
	public void serverStart(FMLServerStartingEvent event)
	{
		if (CommonFeaturesConfig.fill_command) {
			event.registerServerCommand(new CommandFill());
		}
		event.registerServerCommand(new CommandFlightSpeed());
		event.registerServerCommand(new CommandRADev());
		event.registerServerCommand(new CommandRADebug());
	}
	
//	@EventHandler
//	public void handleMissingMappings(FMLMissingMappingsEvent event)
//	{
//		HashSet<String> ignored = new HashSet<String>();
//		HashMap<String, Item> remapped_items = new HashMap<String, Item>();
//		HashMap<String, Block> remapped_blocks = new HashMap<String, Block>();
//
//		for (Entry<String, Block> e : remapped_blocks.entrySet()) {
//			remapped_items.put(e.getKey(), Item.getItemFromBlock(e.getValue()));
//		}
//
//		for (MissingMapping mapping : event.get()) {
//			ResAdditae.LOG.info(String.format("Missing %s %s", mapping.type.name(), mapping.name));
//
//			if (ignored.contains(mapping.name)) {
//				mapping.ignore();
//				continue;
//			}
//
//			if (mapping.type == GameRegistry.Type.ITEM) {
//				Item item = remapped_items.get(mapping.name);
//				if (item != null) {
//					mapping.remap(item);
//				}
//			}
//		}
//	}
}
