package com.mackthehobbit.mbplugin.terrain.generators;

import java.util.ArrayList;
import java.util.Random;

import com.mackthehobbit.mbplugin.terrain.MoreTerrainGenerator;
import com.mbserver.api.Constructors;
import com.mbserver.api.game.Chunk;
import com.mbserver.api.game.Material;

public class SkygridTerrainGenerator extends MoreTerrainGenerator {
	
	private int size;
	private Random random = new Random();
	private Material items[] = Material.values();
	
	@Override
	public void init() {
		try {
			size = Integer.parseInt(this.getExtra("size"));
		} catch (NumberFormatException e) {
			size = 3;
		}
		for(Material material : Material.values()) {
			if(material.getID() < 256 && material.getID() > 0) values.add(material);
		}
	}
	
	@Override
	public void setSeed(long seed) {
		this.random.setSeed(seed);
	}
	
	@Override
	public boolean canProceed() {
		return true;
	}
	
	@Override
	public String getExtrasUsage() {
		return "";
	}
	
	@Override
	public void fillChunk(Chunk chunk) {
		int chunkx = chunk.getLocation().getBlockX();
		int chunky = chunk.getLocation().getBlockY();
		int chunkz = chunk.getLocation().getBlockZ();
		
		for(int x = 0; x < Chunk.SIZE; x += size) {
			for(int y = 0; y < Chunk.SIZE; y += size) {
				for(int z = 0; z < Chunk.SIZE; z += size) {
					if((chunkx + x) % size == 0 && (chunky + y) % size == 0 && (chunkz + z) % size == 0)
						chunk.set(x, y, z, items[random.nextInt(items.length)]);
				}
			}
		}
	}
	
}
