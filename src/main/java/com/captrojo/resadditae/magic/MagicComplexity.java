package com.captrojo.resadditae.magic;

public enum MagicComplexity
{
	BEGINNER("beginner"),
	BASIC("basic"),
	INTERMEDIATE("intermediate"),
	ADVANCED("advanced"),
	EXPERT("expert"),
	GODLIKE("godlike");
	
	public static final MagicComplexity _MAX = GODLIKE;
	
	public final String name;
	
	private MagicComplexity(String name)
	{
		this.name = name;
	}
}
