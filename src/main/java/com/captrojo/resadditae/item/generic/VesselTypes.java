package com.captrojo.resadditae.item.generic;

public enum VesselTypes
{
	HEART_CONTAINER(0, ItemVessel.VF_PIECE),
	MANA_VESSEL(1, ItemVessel.VF_PIECE),
	MANA_RECHARGE_UPGRADE_1(2, 0),
	MANA_RECHARGE_UPGRADE_2(3, 0);
	
	public final int meta;
	public final String name;
	public final int flags;
	
	private VesselTypes(int meta, int flags)
	{
		this.meta = meta;
		this.name = this.name().toLowerCase();
		this.flags = flags;
	}
	
	public boolean hasFlag(int flag)
	{
		return (this.flags & flag) != 0;
	}
}
