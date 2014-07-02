package com.mackthehobbit.mbplugin.terrain;

import java.util.HashMap;
import java.util.Map;

import com.mackthehobbit.mbplugin.terrain.generators.FlatTerrainGenerator;
import com.mackthehobbit.mbplugin.terrain.generators.VoidTerrainGenerator;
import com.mbserver.api.MBServerPlugin;
import com.mbserver.api.Manifest;

@Manifest(authors = "mackthehobbit", name = "MoreTerrain")
public class MoreTerrainPlugin extends MBServerPlugin {
	
	private TerrainCommand command;
	private Map<String, Class<? extends MoreTerrainGenerator>> terrainGenerators = new HashMap<String, Class<? extends MoreTerrainGenerator>>();
	
	@Override
	public void onEnable() {
		this.getPluginManager().registerCommand("terrain", command = new TerrainCommand(this));
		this.addTerrainGenerator("flat", FlatTerrainGenerator.class);
		this.addTerrainGenerator("void", VoidTerrainGenerator.class);
	}
	
	public boolean addTerrainGenerator(String name, Class<? extends MoreTerrainGenerator> terrainGeneratorClass) {
		if(terrainGenerators.containsKey(name)) return false;
		if(name.equals("")) return false;
		terrainGenerators.put(name, terrainGeneratorClass);
		return true;
	}
	
	public MoreTerrainGenerator getTerrainGenerator(String name) {
		try {
			return terrainGenerators.get(name).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			return null;
		}
	}
	
}
