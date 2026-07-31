package com.captrojo.resadditae.magic;

public enum MagicComplexity
{
	BEGINNER("beginner"),
	BASIC("basic"),
	INTERMEDIATE("intermediate"),
	ADVANCED("advanced"),
	EXPERT("expert"),
	GODLIKE("godlike");
	
	public final String name;
	
	private MagicComplexity(String name)
	{
		this.name = name;
	}
}
