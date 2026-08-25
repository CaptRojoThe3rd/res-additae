package com.captrojo.resadditae.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.captrojo.resadditae.main.ResAdditae;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;

public class JsonConfig
{
	static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	public static JsonObject obj_common_stuff;
	
	public static void init()
	{
		File file_common_stuff = new File(ResAdditae.dir_config + File.separator + "common_stuff.json");
		obj_common_stuff = loadObj(file_common_stuff);
	}
	
	public static String getString(JsonObject obj, String key)
	{
		try {
			JsonElement e = obj.get(key);
			String s = e.getAsString();
			return s;
		} catch (Exception e) {
			return null;
		}
	}
	
	static JsonObject loadObj(File file)
	{
		if (!file.exists()) {
			writeDefaultJson(file);
		}
		JsonObject obj;
		try {
			obj = gson.fromJson(new FileReader(file), JsonObject.class);
		} catch (FileNotFoundException e) {
			obj = new JsonObject();
		}
		return obj;
	}
	
	static void writeDefaultJson(File file)
	{
		try {
			JsonWriter writer = new JsonWriter(new FileWriter(file));
			writer.setIndent("\t");
			writer.beginObject();
			writer.endObject();
			writer.close();
		} catch (IOException e) {
			throw new IllegalStateException("Failed to initialize json config");
		}
	}
}
