package com.mackthehobbit.mbplugin.terrain;

import java.util.HashMap;
import java.util.Map;

import com.mbserver.api.dynamic.WorldGenerator;

public abstract class MoreTerrainGenerator extends WorldGenerator {
	
	private Map<String, String> extras = new HashMap<String, String>();
	
	/**
	 * Populates the extras field using an array of "key=value" strings
	 * @param extras The array containing key=value pairs as strings
	 * @param extrasIndex The index to start at in the array
	 */
	public void addExtras(String[] extras, int extrasIndex) {
		for(int i = extrasIndex; i < extras.length; i++) {
			String[] keyValue = extras[i].split("=", 2);
			this.extras.put(keyValue[0], keyValue[1]);
		}
	}
	
	/**
	 * Populates the extras field using an array of keys and an array of values
	 * @param keys Array of keys to use
	 * @param values Array of values to use. Should be the same length as keys
	 */
	public void addExtras(String[] keys, String[] values) {
		for(int i = 0; i < keys.length; i++) {
			extras.put(keys[i], values[i]);
		}
	}
	
	protected String getExtra(String key) {
		String string = this.extras.get(key);
		if(string == null) string = "";
		return string;
	}
	
	@Override
	public void setSeed(long seed) {}
	
	/**
	 * Called after all extras have been added, and before world generation starts. Useful for creating variables outside the fillChunk() scope
	 */
	public void init() {}
	public abstract boolean canProceed();
	public abstract String getExtrasUsage();
	
}
