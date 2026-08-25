package com.captrojo.resadditae.block.utility;

import com.captrojo.resadditae.block.IMultiBlockData;
import com.captrojo.resadditae.item.MultiItems;
import com.captrojo.resadditae.world.loot.LootGroup;
import com.captrojo.resadditae.world.loot.ModLoot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockSnowDungeonVault extends BlockVaultBase
{
	private static final String[] NAMES = {
		"lvl_1",
		"lvl_2",
		"lvl_3",
		"lvl_4",
		"lvl_5",
		"lvl_6",
		"lvl_7",
		"lvl_8"
	};
	private static final String[] TEXTURE_NAMES = {
		"snow_dungeon/vault_1",
		"snow_dungeon/vault_2",
		"snow_dungeon/vault_3",
		"snow_dungeon/vault_4",
		"snow_dungeon/vault_5",
		"snow_dungeon/vault_6",
		"snow_dungeon/vault_7",
		"snow_dungeon/vault_8",
	};

	public BlockSnowDungeonVault()
	{
		super("snow_dungeon_vault", TEXTURE_NAMES);
		this.meta_count = 8;
	}

	@Override
	protected LootGroup getLoot(World world, int meta)
	{
		return ModLoot.snow_dungeon_vaults[meta & 0x7];
	}

	@Override
	public String[] getNames()
	{
		return NAMES;
	}

	@Override
	public boolean isCorrectKey(World world, EntityPlayer player, int x, int y, int z, int key)
	{
		if (key == 0) {
			return true;
		}
		if ((key & MultiItems.KEY_TYPE_MASK) != MultiItems.KEYTYPE_SNOW_DUNGEON_VAULT) {
			return false;
		}
		return (key & 0x7) == world.getBlockMetadata(x, y, z);
	}
}
