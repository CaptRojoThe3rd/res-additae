package com.captrojo.resadditae.compatibility;

import java.util.ArrayList;

public class CommonStuffErrorEntry
{
	String name;
	ArrayList<String> attempted_refs;
	
	CommonStuffErrorEntry(String name)
	{
		this.name = name;
		this.attempted_refs = new ArrayList<String>();
	}
	
	String[] getReportStrings()
	{
		String ret = String.format(
			"Block/item not found: '%s'\n" +
			"Checked:\n",
			this.name
		);
		for (String a : this.attempted_refs) {
			ret = ret + "\t" + a + "\n";
		}
		return ret.split("\n");
	}
}
