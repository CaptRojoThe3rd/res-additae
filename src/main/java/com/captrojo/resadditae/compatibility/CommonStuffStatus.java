package com.captrojo.resadditae.compatibility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.captrojo.resadditae.main.ResAdditae;

public class CommonStuffStatus
{
	static ArrayList<CommonStuffErrorEntry> errors = new ArrayList<CommonStuffErrorEntry>();
	static CommonStuffErrorEntry current_entry;
	
	public static String report_file_path;
	
	static void beginNew(String name)
	{
		current_entry = new CommonStuffErrorEntry(name);
	}
	
	static void addAttempted(String mod_id, String id, int meta)
	{
		current_entry.attempted_refs.add(String.format("%s:%s (meta=%d)", mod_id, id, meta));
	}
	
	static void reportError()
	{
		ResAdditae.common_items_error = true;
		errors.add(current_entry);
		
		ResAdditae.LOG.error("▓▓▓ COMMON BLOCK/ITEM NOT FOUND ▓▓▓");
		String[] a = current_entry.getReportStrings();
		for (String s : a) {
			ResAdditae.LOG.error(s);
		}
	}
	
	public static boolean saveReports()
	{
		report_file_path = ResAdditae.dir_crash_reports + File.separator + "resadditae-common-stuff-errors.txt";
		File rfile = new File(report_file_path);
		try {
			rfile.createNewFile();
			FileWriter fw = new FileWriter(rfile);
			
			fw.write("\n▓▓▓ Common Block/Item Errors ▓▓▓\n");
			
			for (CommonStuffErrorEntry entry : errors) {
				fw.write("\n");
				String[] arr = entry.getReportStrings();
				for (String s : arr) {
					fw.write(s);
					fw.write("\n");
				}
				fw.write("\n");
			}
			
			fw.close();
			return true;
		} catch (IOException e) {
			ResAdditae.LOG.error("Failed to write to file " + rfile.getAbsolutePath());
			e.printStackTrace();
			return false;
		}
	}
}
