package com.captrojo.resadditae.entity.passive;

import java.util.ArrayList;

import com.captrojo.resadditae.block.ModBlocks;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class EntitySheepMoreColors extends EntitySheep
{
	public static final float[][] FLEECE_COLORS = {
		{0.01f, 0.01f, 0.01f},
		{0.24f, 0.24f, 0.24f},
		{0.25f, 0.25f, 0.25f},
		{0.71f, 0.71f, 0.71f},
		{0.88f, 0.88f, 0.88f},
		{0.65f, 0.63f, 0.61f},
		{0.41f, 0.36f, 0.40f},
		{0.46f, 0.46f, 0.55f},
		
		{0.27f, 0.08f, 0.08f},
		{0.50f, 0.00f, 0.00f},
		{0.52f, 0.25f, 0.29f},
		{0.68f, 0.40f, 0.38f},
		{0.77f, 0.47f, 0.47f},
		{0.89f, 0.64f, 0.61f},
		{0.91f, 0.39f, 0.35f},
		{0.84f, 0.20f, 0.00f},
		
		{0.21f, 0.14f, 0.08f},
		{0.54f, 0.25f, 0.00f},
		{0.99f, 0.59f, 0.08f},
		{0.85f, 0.60f, 0.51f},
		{0.93f, 0.55f, 0.09f},
		{0.92f, 0.75f, 0.66f},
		{0.90f, 0.80f, 0.73f},
		{0.96f, 0.92f, 0.84f},
		
		{0.84f, 0.75f, 0.41f},
		{1.00f, 0.82f, 0.00f},
		{0.80f, 0.71f, 0.41f},
		{0.71f, 0.67f, 0.55f},
		{0.77f, 0.65f, 0.50f},
		{0.95f, 0.96f, 0.00f},
		{0.82f, 0.90f, 0.34f},
		{0.61f, 1.00f, 0.23f},
		
		{0.04f, 0.41f, 0.00f},
		{0.35f, 0.38f, 0.15f},
		{0.56f, 0.82f, 0.00f},
		{0.18f, 0.54f, 0.34f},
		{0.31f, 0.74f, 0.11f},
		{0.18f, 0.79f, 0.48f},
		{0.71f, 0.79f, 0.68f},
		{0.58f, 0.62f, 0.53f},
		
		{0.00f, 0.21f, 0.27f},
		{0.27f, 0.48f, 0.60f},
		{0.15f, 0.33f, 0.29f},
		{0.26f, 0.65f, 0.71f},
		{0.37f, 0.84f, 0.79f},
		{0.50f, 0.91f, 0.77f},
		{0.76f, 0.87f, 0.87f},
		{0.62f, 0.68f, 0.75f},
		
		{0.04f, 0.04f, 0.16f},
		{0.07f, 0.09f, 0.28f},
		{0.00f, 0.00f, 0.50f},
		{0.39f, 0.49f, 0.83f},
		{0.27f, 0.51f, 0.70f},
		{0.31f, 0.33f, 0.44f},
		{0.18f, 0.15f, 0.55f},
		{0.54f, 0.50f, 0.93f},
		
		{0.16f, 0.12f, 0.36f},
		{0.18f, 0.10f, 0.23f},
		{0.33f, 0.22f, 0.34f},
		{0.56f, 0.44f, 0.73f},
		{0.65f, 0.54f, 0.87f},
		{0.73f, 0.62f, 0.83f},
		{0.79f, 0.72f, 0.89f},
		{0.76f, 0.70f, 0.76f},
		
		{0.60f, 0.06f, 0.29f},
		{0.48f, 0.20f, 0.43f},
		{0.81f, 0.30f, 0.57f},
		{1.00f, 0.24f, 0.43f},
		{0.73f, 0.59f, 0.59f},
		{0.76f, 0.38f, 0.55f},
		{0.90f, 0.59f, 0.68f},
		{0.84f, 0.69f, 0.80f}
	};
	
	public static final int DW_FLEECE_COLOR = 16;
	public static final int DW_SHEARED = 17;
	
	static boolean fleece_color_hack = false;
	
	public static EntitySheepMoreColors replaceVanillaSheepWithModded(EntitySheep oldsheep)
	{
		if (oldsheep.isDead) {
			return null;
		}
		
		World world = oldsheep.worldObj;
		
		NBTTagCompound nbt = new NBTTagCompound();
		oldsheep.writeToNBT(nbt);
		nbt.setByte("Color", (byte) -1);
		
		world.removeEntity(oldsheep);
		
		EntitySheepMoreColors newsheep = new EntitySheepMoreColors(world);
		newsheep.readFromNBT(nbt);
		world.spawnEntityInWorld(newsheep);
		
		newsheep.rotationYaw = oldsheep.rotationYaw;
		newsheep.rotationPitch = oldsheep.rotationPitch;
		newsheep.rotationYawHead = oldsheep.rotationYawHead;
		
		return newsheep;
	}
	
	public static EntitySheep replaceModdedSheepWithVanilla(EntitySheep oldsheep)
	{
		if (oldsheep.isDead) {
			return null;
		}
		
		World world = oldsheep.worldObj;
		
		NBTTagCompound nbt = new NBTTagCompound();
		oldsheep.writeToNBT(nbt);
		nbt.setByte("Color", (byte) 0);
		
		world.removeEntity(oldsheep);
		
		EntitySheep newsheep = new EntitySheep(world);
		newsheep.readFromNBT(nbt);
		world.spawnEntityInWorld(newsheep);
		
		newsheep.rotationYaw = oldsheep.rotationYaw;
		newsheep.rotationPitch = oldsheep.rotationPitch;
		newsheep.rotationYawHead = oldsheep.rotationYawHead;
		
		return newsheep;
	}

	public EntitySheepMoreColors(World world)
	{
		super(world);
	}
	
	public int getFleeceColorReal()
	{
		return this.dataWatcher.getWatchableObjectByte(DW_FLEECE_COLOR);
	}
	
	public void setFleeceColorReal(int color)
	{
		this.dataWatcher.updateObject(DW_FLEECE_COLOR, (byte) color);
	}
	
	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(DW_SHEARED, (byte) 0);
	}
	
	@Override
	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		if (this.getSheared()) {
			return;
		}
		int color = this.getFleeceColorReal();
		int idx = (color >> 3);
		int meta = (color & 0x7);
		this.entityDropItem(new ItemStack(ModBlocks.wools[idx], 1, meta), 0.0f);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		fleece_color_hack = true;
		super.writeEntityToNBT(nbt);
		fleece_color_hack = false;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		fleece_color_hack = true;
		super.readEntityFromNBT(nbt);
		fleece_color_hack = false;
	}

	/**
	 * Do not call; used to detect ItemDye calling in order to turn this sheep back into a
	 * normal one
	 */
	@Override
	public int getFleeceColor()
	{
		if (fleece_color_hack) {
			return this.getFleeceColorReal();
		}
		return -1;
	}

	/**
	 * Do not call; used to detect ItemDye calling in order to turn this sheep back into a
	 * normal one
	 */
	@Override
	public void setFleeceColor(int color)
	{
		if (fleece_color_hack) {
			this.setFleeceColorReal(color);
			return;
		}
		if (this.worldObj.isRemote) {
			return;
		}
		EntitySheep newsheep = EntitySheepMoreColors.replaceModdedSheepWithVanilla(this);
		newsheep.setFleeceColor(color);
	}
	
	@Override
	public boolean getSheared()
	{
		return this.dataWatcher.getWatchableObjectByte(DW_SHEARED) != 0;
	}
	
	@Override
	public void setSheared(boolean sheared)
	{
		this.dataWatcher.updateObject(DW_SHEARED, sheared ? (byte) 1 : (byte) 0);
	}
	
	@Override
	public EntitySheep createChild(EntityAgeable entity)
	{
		EntitySheep other_sheep = (EntitySheep) entity;
		boolean b = this.rand.nextBoolean();
		
		fleece_color_hack = true;
		int color = b ? this.getFleeceColorReal() : other_sheep.getFleeceColor();
		fleece_color_hack = false;
		
		EntitySheep sheep;
		if (!(other_sheep instanceof EntitySheepMoreColors) && !b) {
			sheep = new EntitySheep(this.worldObj);
			sheep.setFleeceColor(color);
		} else {
			sheep = new EntitySheepMoreColors(this.worldObj);
			((EntitySheepMoreColors) sheep).setFleeceColorReal(color);
		}
		
		return sheep;
	}
	
	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data)
	{
		fleece_color_hack = true;
		data = super.onSpawnWithEgg(data);
		fleece_color_hack = false;
		this.setFleeceColorReal(this.rand.nextInt(FLEECE_COLORS.length));
		return data;
	}
	
	@Override
	public boolean canMateWith(EntityAnimal animal)
	{
		return (animal instanceof EntitySheep);
	}
	
	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune)
	{
		ArrayList<ItemStack> drops = super.onSheared(item, world, x, y, z, fortune);
		drops.clear();

		int count = 1 + this.rand.nextInt(3);
		int color = this.getFleeceColorReal();
		
		int idx = (color >> 3);
		int meta = (color & 0x7);
		
		for (int i = 0; i < count; i++) {
			drops.add(new ItemStack(ModBlocks.wools[idx], 1, meta));
		}
		
		return drops;
	}
}
