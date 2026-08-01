package com.captrojo.resadditae.extprop;

import com.captrojo.resadditae.main.ResAdditae;

public enum ManaUpgrades
{
	RECHARGE_1,
	RECHARGE_2;
	
	public final long bit;
	
	private ManaUpgrades(ManaUpgrades...supercedes)
	{
		this.bit = 1 << this.ordinal();
	}
}
