package com.captrojo.resadditae.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.captrojo.resadditae.compatibility.OrderedEquipmentLists;
import com.captrojo.resadditae.compatibility.OrderedEquipmentLists.Tiers;
import com.captrojo.resadditae.extprop.RAMobProperties;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.Constants.NBT;

public class TEMultiSpawner extends TileEntity
{
	public int spawn_range = 5;
	public int max_nearby_mobs = 5;
	public int activation_range = 16;
	
	public int min_spawn_delay = 200;
	public int max_spawn_delay = 800;
	
	@SideOnly(Side.CLIENT) public float render_angle;
	@SideOnly(Side.CLIENT) private int render_timer;
	@SideOnly(Side.CLIENT) public Entity render_entity;
	
	public ArrayList<String> entity_list;
	
	private int spawn_delay;
	
	public TEMultiSpawner()
	{
		this.entity_list = new ArrayList<String>();
		
		this.spawn_delay = this.min_spawn_delay;
	}
	
	public void onPlacedInStructure()
	{
		this.spawn_delay = this.min_spawn_delay;
	}
	
	public void addEntityToList(String name)
	{
		this.entity_list.add(name);
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	}
	
	public boolean isActive()
	{
		int meta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
		if ((meta & 0x1) != 0) {
			return false;
		}
		
		EntityPlayer player = this.worldObj.getClosestPlayer(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, this.activation_range);
		return player != null;
	}
	
	protected boolean canSpawnEntities()
	{
		if (!this.isActive()) {
			return false;
		}
		
		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(
			(double) (this.xCoord - this.spawn_range), (double) (this.yCoord - this.spawn_range), (double) (this.zCoord - this.spawn_range),
			(double) (this.xCoord + this.spawn_range), (double) (this.yCoord + this.spawn_range), (double) (this.zCoord + this.spawn_range)
		);
		List nearby_entities = this.worldObj.getEntitiesWithinAABB(EntityLiving.class, aabb);
		if (nearby_entities.size() >= this.max_nearby_mobs) {
			return false;
		}
		
		return true;
	}
	
	protected double[] getRandomSpawnPos()
	{
		Random rand = this.worldObj.rand;
		double[] pos = new double[3];
		
		/* It is literally impossible for these to be accessed without being initialized,
		 * but Java won't compile unless they are. I guess it's better to have unncessary
		 * initializations instead of missing initializations...
		 */
		int x = 0, y = 0, z = 0;
		double y1 = 0;
		
		for (int q = 0; q < 1000; q++) {
			x = (rand.nextBoolean() ? 1 : -1) * rand.nextInt(this.spawn_range) + this.xCoord;
			int y0 = rand.nextInt(this.spawn_range) + this.yCoord;
			y = y0;
			z = (rand.nextBoolean() ? 1 : -1) * rand.nextInt(this.spawn_range) + this.zCoord;
			
			for (; y > (this.yCoord - this.spawn_range); y--) {
				Block block = this.worldObj.getBlock(x, y, z);
				if (!block.isAir(this.worldObj, x, y, z)) {
					AxisAlignedBB aabb = block.getCollisionBoundingBoxFromPool(this.worldObj, x, y, z);
					y++;
					y1 = (aabb == null ? y : aabb.maxY) - (double) y;
					break;
				}
			}
			if (y == this.yCoord - this.spawn_range) {
				y = y0;
			}
			
			Block block = this.worldObj.getBlock(x, y, z);
			if (block.isAir(this.worldObj, x, y, z) || block == Blocks.water) {
				break;
			}
		}
		
		pos[0] = (double) x + 0.5;
		pos[1] = (double) y + y1;
		pos[2] = (double) z + 0.5;
		return pos;
	}
	
	protected EntityLiving getRandomEntity()
	{
		if (this.entity_list.size() == 0) {
			return null;
		}
		int i = this.worldObj.rand.nextInt(this.entity_list.size());
		return (EntityLiving) EntityList.createEntityByName(this.entity_list.get(i), this.worldObj);
	}
	
	protected void modifyEntity(EntityLiving entity)
	{
		int meta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
		if (meta < 2) {
			return;
		}
		
		RAMobProperties rmp = RAMobProperties.get(entity);
		
		Item weapon = null;
		if (entity instanceof EntitySkeleton) {
			weapon = Items.bow;
		} else {
			switch (this.worldObj.rand.nextInt(4)) {
			case 0:
				weapon = OrderedEquipmentLists.getRandomItem(OrderedEquipmentLists.sword_map, Tiers.LEATHER_WOOD, Tiers.DIAMOND, 0.7);
				break;
			case 1:
				weapon = OrderedEquipmentLists.getRandomItem(OrderedEquipmentLists.axe_map, Tiers.LEATHER_WOOD, Tiers.DIAMOND, 0.7);
				break;
			case 2:
				weapon = OrderedEquipmentLists.getRandomItem(OrderedEquipmentLists.scythe_map, Tiers.LEATHER_WOOD, Tiers.DIAMOND, 0.7);
				break;
			case 3:
				weapon = OrderedEquipmentLists.getRandomItem(OrderedEquipmentLists.halberd_map, Tiers.LEATHER_WOOD, Tiers.DIAMOND, 0.7);
				break;
			}
		}
		ItemStack weapon_stack = new ItemStack(weapon);
		EnchantmentHelper.addRandomEnchantment(this.worldObj.rand, weapon_stack, 20);
		entity.setCurrentItemOrArmor(0, weapon_stack);
		OrderedEquipmentLists.applyRandomArmor(entity, Tiers.LEATHER_WOOD, Tiers.DIAMOND, 0.7, 0.1, 20);
	}
	
	protected void spawnEntity()
	{
		double[] pos = this.getRandomSpawnPos();
		EntityLiving entity = this.getRandomEntity();
		if (entity == null) {
			return;
		}
		entity.setLocationAndAngles(pos[0], pos[1], pos[2], 0, 0);
		this.modifyEntity(entity);
		this.worldObj.spawnEntityInWorld(entity);
		entity.spawnExplosionParticle();
	}
	
	protected void updateServer()
	{
		if (!this.isActive()) {
			return;
		}
		
		this.spawn_delay--;
		if (this.spawn_delay <= 0) {
			this.spawn_delay = this.min_spawn_delay + this.worldObj.rand.nextInt(this.max_spawn_delay - this.min_spawn_delay);
			
			if (this.canSpawnEntities()) {
				for (int i = 0; i < this.worldObj.rand.nextInt(this.max_nearby_mobs) + 1; i++) {
					this.spawnEntity();
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	protected void spawnParticles()
	{
		double px = this.xCoord + this.worldObj.rand.nextDouble();
                double py = this.yCoord + this.worldObj.rand.nextDouble();
                double pz = this.zCoord + this.worldObj.rand.nextDouble();
                this.worldObj.spawnParticle("smoke", px, py, pz, 0, 0, 0);
                this.worldObj.spawnParticle("flame", px, py, pz, 0, 0, 0);
	}
	
	@SideOnly(Side.CLIENT)
	protected void updateClient()
	{
		if (this.isActive()) {
			this.spawnParticles();
		}
		
		this.render_angle = (this.render_angle + 1) % 360f;
		this.render_timer++;
                if (this.render_timer % 40 == 0) {
			this.render_entity = this.getRandomEntity();
		}
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		
		if (this.entity_list.size() == 0) {
			return;
		}
		
		if (this.worldObj.isRemote) {
			this.updateClient();
		} else {
			this.updateServer();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		
		NBTTagList list = tag.getTagList("entities", NBT.TAG_STRING);
		for (int i = 0; i < list.tagCount(); i++) {
			this.entity_list.add(list.getStringTagAt(i));
		}
		
		this.readSpecificFromNBT(tag);
	}
	
	protected void readSpecificFromNBT(NBTTagCompound tag)
	{
		tag.setInteger("spawn_delay", this.spawn_delay);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		
		NBTTagList list = new NBTTagList();
		for (String s : this.entity_list) {
			list.appendTag(new NBTTagString(s));
		}
		tag.setTag("entities", list);
		
		this.writeSpecificToNBT(tag);
	}
	
	protected void writeSpecificToNBT(NBTTagCompound tag)
	{
		this.spawn_delay = tag.getInteger("spawn_delay");
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbt);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
	{
		this.readFromNBT(packet.func_148857_g());
	}
}
