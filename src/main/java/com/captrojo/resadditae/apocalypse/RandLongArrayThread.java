package com.captrojo.resadditae.apocalypse;

import java.util.Random;

public class RandLongArrayThread extends Thread
{
	private Random rand;
	private long base;
	private int bound;
	private volatile long[] rand_arr;
	
	public RandLongArrayThread(Random rand, long base, int bound, int size)
	{
		this.rand = rand;
		this.base = base;
		this.bound = bound;
		this.rand_arr = new long[size];
	}
	
	public long[] getArr()
	{
		return this.rand_arr;
	}
	
	@Override
	public void run()
	{
		for (int i = 0; i < this.rand_arr.length; i++) {
			this.rand_arr[i] = base + rand.nextInt(bound);
		}
	}
}
