package com.captrojo.resadditae.world;

public class WorldLoadingException extends RuntimeException
{
	public WorldLoadingException(String message)
	{
		super(message);
	}
	
	public WorldLoadingException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
