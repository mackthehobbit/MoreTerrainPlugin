package com.mackthehobbit.mbplugin.terrain.generators;

import com.mackthehobbit.mbplugin.terrain.MoreTerrainGenerator;
import com.mbserver.api.game.Chunk;
import com.mbserver.api.game.Material;

public class FlatTerrainGenerator extends MoreTerrainGenerator {
	
	private int height;
	private short material;
	private boolean bedrock = true;
	
	@Override
	public void init() {
		try {
			height = Integer.parseInt(this.getExtra("height"));
		} catch(NumberFormatException e) {
			height = 4;
		}
		try {
			material = Short.parseShort(this.getExtra("material"));
		} catch (NumberFormatException e) {
			try {
				material = Material.valueOf(this.getExtra("material").toUpperCase()).getID();
			} catch (IllegalArgumentException ex) {
				material = -1;
			}
		}
		if(this.getExtra("bedrock").equals("false")) bedrock = false;
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
		int chunkY = chunk.getLocation().getBlockY();
		for(int x = 0; x < Chunk.SIZE; x++) {
			for(int y = 0; y < Chunk.SIZE; y++) {
				for(int z = 0; z < Chunk.SIZE; z++) {
					short id = 0;
					if(chunkY + y <= this.height) {
						 if(this.material == -1) {
							 id = Material.GRASS.getID();
							 if(chunkY + y < this.height) id = Material.DIRT.getID();
							 if(chunkY + y < this.height - 3) id = Material.STONE.getID();
						 } else id = this.material;
					}
					if(chunkY + y == 1 && this.bedrock) {
						id = Material.BEDROCK.getID();
					}
					chunk.set(x, y, z, id);
				}
			}
		}
	}
	
}
