package com.captrojo.resadditae.util;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MiscHlpr
{
	public static Object getRandomElement(List list, Random rand)
	{
		return list.get(rand.nextInt(list.size()));
	}
	
	public static int getRandomElement(int[] arr, Random rand)
	{
		return arr[rand.nextInt(arr.length)];
	}
	
	public static int[] getRandomInts(Random rand, int size, int bound)
	{
		int[] arr = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = rand.nextInt(bound);
		}
		return arr;
	}
	
	public static Integer[] getUniqueRandomInts(Random rand, int size, int bound)
	{
		Set<Integer> set = new HashSet<Integer>();
		while (set.size() < size) {
			set.add(rand.nextInt(bound));
		}
		return set.toArray(new Integer[size]);
	}
}
