package com.captrojo.resadditae.main;

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
	
	public static Integer[] getUniqueRandomInts(Random rand, int size, int max)
	{
		Set<Integer> set = new HashSet<Integer>();
		while (set.size() < size) {
			set.add(rand.nextInt(max));
		}
		return set.toArray(new Integer[size]);
	}
}
