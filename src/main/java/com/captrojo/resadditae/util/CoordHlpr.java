package com.captrojo.resadditae.util;

public class CoordHlpr
{
	/**
	 * Returns an array organized as [x1, y1, z1, x2, y2, z2], where:
	 * (x1 <= x2 && y1 <= y2 && z1 <= z2). Useful for filling areas with blocks.
	 */
	public static int[] fixCorners(int x1, int y1, int z1, int x2, int y2, int z2)
	{
		int[] arr = new int[6];
		if (x1 <= x2) {
			arr[0] = x1;
			arr[3] = x2;
		} else {
			arr[0] = x2;
			arr[3] = x1;
		}
		if (y1 <= y2) {
			arr[1] = y1;
			arr[4] = y2;
		} else {
			arr[1] = y2;
			arr[4] = y1;
		}
		if (z1 <= z2) {
			arr[2] = z1;
			arr[5] = z2;
		} else {
			arr[2] = z2;
			arr[5] = z1;
		}
		return arr;
	}
}
