package com.captrojo.resadditae.tileentity;

import java.util.List;

import com.captrojo.resadditae.compatibility.OrderedEquipmentLists;
import com.captrojo.resadditae.compatibility.OrderedEquipmentLists.Tiers;
import com.captrojo.resadditae.extprop.RAMobProperties;
import com.captrojo.resadditae.extprop.SpawnSource;
import com.captrojo.resadditae.item.ModItems;
import com.captrojo.resadditae.item.MultiItems;
import com.captrojo.resadditae.sounds.ModSounds;
import com.captrojo.resadditae.util.ItemHlpr;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public class TESnowDungeonSpawner extends TEMultiSpawner
{
	public static enum State
	{
		INACTIVE,
		WAVE_COOLDOWN,
		SPAWNING,
		IDLE,
		REWARDING,
		SPENT
	}

	private State state;
	private State prev_state;
	private boolean changed_state;

	private int player_count;

	private int entities_alive;
	private int groups_to_spawn;
	private int action_delay;
	private int time_till_next_wave;
	private int waves_remaining;
	private int rewards_remaining;

	public TESnowDungeonSpawner()
	{
		this.prev_state = State.INACTIVE;
		this.state = State.INACTIVE;
		
		this.activation_range = 8;
	}

	public State getState()
	{
		return this.state;
	}

	public void setState(State state)
	{
		this.state = state;
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	}

	public void onEntityKilled()
	{
		this.entities_alive--;
		if (this.state == State.IDLE && this.entities_alive == 0) {
			this.nextWave();
		}
	}

	public boolean isSpent()
	{
		return this.state == State.SPENT;
	}

	@Override
	public boolean isActive()
	{
		return (this.state != State.INACTIVE || super.isActive()) && this.state != State.SPENT;
	}

	protected void nextWave()
	{
		if (this.waves_remaining == 0) {
			this.rewards_remaining = this.player_count * 2;
			this.setState(State.REWARDING);
		} else {
			this.groups_to_spawn = 5;
			this.time_till_next_wave = 60;
			this.waves_remaining--;
			this.setState(State.WAVE_COOLDOWN);
		}
	}

	private static final Tiers[] GEAR_TIERS = {
		Tiers.LEATHER_WOOD, Tiers.IRON,
		Tiers.STONE_CHAINMAIL, Tiers.SILVER,
		Tiers.IRON, Tiers.GOLD,
		Tiers.GOLD, Tiers.DIAMOND,
		Tiers.TITANIUM, Tiers.COBALT,
		Tiers.DIAMOND, Tiers.STARMETAL,
		Tiers.COBALT, Tiers.ANCIENT_GEM,
		Tiers.STARMETAL, Tiers.ANCIENT_GEM
	};

	@Override
	protected void modifyEntity(EntityLiving entity)
	{
		RAMobProperties rmp = RAMobProperties.get(entity);
		rmp.setSpawnSrc(SpawnSource.SNOW_DUNGEON, this.xCoord, this.yCoord, this.zCoord);
		this.entities_alive++;

		int lvl2 = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord) & 0xe;
		int lvl = lvl2 >> 1;
		Tiers start_tier = GEAR_TIERS[lvl2];
		Tiers end_tier = GEAR_TIERS[lvl2 + 1];
		double exp = (lvl > 6) ? 0.8 : 1;
		int ench_lvl = 25 + (lvl * 4);

		OrderedEquipmentLists.applyRandomArmor(entity, start_tier, end_tier, exp, 0, ench_lvl);
		Item weapon = null;
		if (entity instanceof EntitySkeleton) {
			weapon = Items.bow;
		} else {
			switch (this.worldObj.rand.nextInt(4)) {
			case 0:
				weapon = OrderedEquipmentLists.getRandomItem(OrderedEquipmentLists.sword_map, start_tier, end_tier, exp);
				break;
			case 1:
				weapon = OrderedEquipmentLists.getRandomItem(OrderedEquipmentLists.axe_map, start_tier, end_tier, exp);
				break;
			case 2:
				weapon = OrderedEquipmentLists.getRandomItem(OrderedEquipmentLists.scythe_map, start_tier, end_tier, exp);
				break;
			case 3:
				weapon = OrderedEquipmentLists.getRandomItem(OrderedEquipmentLists.halberd_map, start_tier, end_tier, exp);
				break;
			}
		}
		ItemStack weapon_stack = new ItemStack(weapon);
		EnchantmentHelper.addRandomEnchantment(this.worldObj.rand, weapon_stack, ench_lvl);
		entity.setCurrentItemOrArmor(0, weapon_stack);
	}

	protected void spawnEntityGroup()
	{
		for (int i = 0; i < this.player_count; i++) {
			this.spawnEntity();
		}
	}

	private static final ItemStack[][] REWARDS = {
		{
			new ItemStack(Items.bread, 24),
			new ItemStack(Items.golden_apple, 1),
			new ItemStack(ModItems.keys, 1, MultiItems.KEYTYPE_SNOW_DUNGEON_VAULT | 0)
		},
		{
			new ItemStack(Items.bread, 48),
			new ItemStack(Items.golden_apple, 1),
			new ItemStack(ModItems.keys, 1, MultiItems.KEYTYPE_SNOW_DUNGEON_VAULT | 1)
		},
		{
			new ItemStack(Items.bread, 64),
			new ItemStack(Items.cooked_chicken, 24),
			new ItemStack(Items.golden_apple, 2),
			new ItemStack(ModItems.keys, 1, MultiItems.KEYTYPE_SNOW_DUNGEON_VAULT | 2)
		},
		{
			new ItemStack(Items.bread, 64),
			new ItemStack(Items.cooked_chicken, 48),
			new ItemStack(Items.golden_apple, 4),
			new ItemStack(ModItems.keys, 1, MultiItems.KEYTYPE_SNOW_DUNGEON_VAULT | 3)
		},
		{
			new ItemStack(Items.cooked_chicken, 64),
			new ItemStack(Items.cooked_beef, 24),
			new ItemStack(Items.golden_apple, 8),
			new ItemStack(ModItems.keys, 1, MultiItems.KEYTYPE_SNOW_DUNGEON_VAULT | 4)
		},
		{
			new ItemStack(Items.cooked_chicken, 64),
			new ItemStack(Items.cooked_beef, 48),
			new ItemStack(Items.golden_apple, 8),
			new ItemStack(ModItems.keys, 1, MultiItems.KEYTYPE_SNOW_DUNGEON_VAULT | 5)
		},
		{
			new ItemStack(Items.cooked_beef, 64),
			new ItemStack(Items.golden_carrot, 24),
			new ItemStack(Items.golden_apple, 16),
			new ItemStack(Items.golden_apple, 1, 2),
			new ItemStack(ModItems.keys, 1, MultiItems.KEYTYPE_SNOW_DUNGEON_VAULT | 6)
		},
		{
			new ItemStack(Items.cooked_beef, 64),
			new ItemStack(Items.golden_carrot, 48),
			new ItemStack(Items.golden_apple, 16),
			new ItemStack(Items.golden_apple, 3, 2),
			new ItemStack(ModItems.keys, 1, MultiItems.KEYTYPE_SNOW_DUNGEON_VAULT | 7)
		}
	};
	
	protected void spawnReward(ItemStack stack)
	{
		ItemHlpr.spawnEntityItemFromBlock(stack, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
		this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, ModSounds.VAULT_DISPENSE, 1.0f, 1.0f);
	}

	protected void spawnReward()
	{
		int lvl = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord) >> 1;
		ItemStack[] pool = REWARDS[lvl];
		ItemStack stack = pool[this.worldObj.rand.nextInt(pool.length)].copy();
		stack.stackSize = this.worldObj.rand.nextInt(stack.stackSize + 1);
		this.spawnReward(stack);
	}

	@Override
	protected void updateServer()
	{
		if (this.state == State.INACTIVE && this.isActive()) {
			this.waves_remaining = 3;
			this.nextWave();

			AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(
				this.xCoord - this.activation_range, this.yCoord - this.activation_range, this.zCoord - this.activation_range,
				this.xCoord + this.activation_range, this.yCoord + this.activation_range, this.zCoord + this.activation_range);
			List players = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, aabb);
			this.player_count = players.size();
		}

		if (this.state == State.WAVE_COOLDOWN) {
			this.time_till_next_wave--;
			if (this.time_till_next_wave <= 0) {
				this.setState(State.SPAWNING);
			}
		}

		if (this.state == State.SPAWNING) {
			if (this.groups_to_spawn > 0) {
				this.action_delay--;
				if (this.action_delay <= 0) {
					this.groups_to_spawn--;
					this.action_delay = 20;
					this.spawnEntityGroup();
				}
			} else {
				this.setState(State.IDLE);
			}
		}

		if (this.state == State.REWARDING) {
			this.action_delay--;
			if (this.action_delay <= 0) {
				if (this.rewards_remaining == 0) {
					this.setState(State.SPENT);
					int lvl = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord) >> 1;
					this.spawnReward(new ItemStack(ModItems.keys, 1, lvl | MultiItems.KEYTYPE_SNOW_DUNGEON_VAULT));
				} else {
					this.action_delay = 20;
					this.rewards_remaining--;
					this.spawnReward();
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected void updateClient()
	{
		super.updateClient();

		if (this.changed_state) {
			this.changed_state = false;
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);

			if (this.prev_state == State.INACTIVE) {
				Minecraft.getMinecraft().effectRenderer.addBlockDestroyEffects(this.xCoord, this.yCoord, this.zCoord, Blocks.snow, 0);
			}
		}
	}

	@Override
	protected void readSpecificFromNBT(NBTTagCompound tag)
	{
		this.changed_state = true;
		this.prev_state = this.state;
		this.state = State.values()[tag.getByte("State")];
		this.action_delay = tag.getShort("ActionDelay");

		this.player_count = tag.getShort("PlayerCount");
		this.entities_alive = tag.getShort("EntitiesAlive");
		this.groups_to_spawn = tag.getShort("GroupsToSpawn");
		this.time_till_next_wave = tag.getShort("TimeTillNextWave");
		this.waves_remaining = tag.getByte("WavesRemaining");
		this.rewards_remaining = tag.getByte("RewardsRemaining");
	}

	@Override
	protected void writeSpecificToNBT(NBTTagCompound tag)
	{
		tag.setByte("State", (byte) this.state.ordinal());
		tag.setShort("ActionDelay", (short) this.action_delay);

		tag.setShort("PlayerCount", (short) this.player_count);
		tag.setShort("EntitiesAlive", (short) this.entities_alive);
		tag.setShort("GroupsToSpawn", (short) this.groups_to_spawn);
		tag.setShort("TimeTillNextWave", (short) this.time_till_next_wave);
		tag.setByte("WavesRemaining", (byte) this.waves_remaining);
		tag.setByte("RewardsRemaining", (byte) this.rewards_remaining);
	}
}
