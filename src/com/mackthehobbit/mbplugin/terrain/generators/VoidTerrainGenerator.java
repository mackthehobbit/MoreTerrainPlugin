package com.mackthehobbit.mbplugin.terrain.generators;

import com.mackthehobbit.mbplugin.terrain.MoreTerrainGenerator;
import com.mbserver.api.game.Chunk;
import com.mbserver.api.game.Material;

public class VoidTerrainGenerator extends MoreTerrainGenerator {
	
	private boolean valid = true;
	private int x, y, z;
	
	@Override
	public void init() {
		if(this.getExtra("block").equals("")) return;
		String[] block = this.getExtra("block").split(",", 3);
		try {
			x = Integer.parseInt(block[0]);
			y = Integer.parseInt(block[1]);
			z = Integer.parseInt(block[2]);
		} catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
			valid = false;
		}
		if(y < 0) valid = false;
	}
	
	@Override
	public boolean canProceed() {
		return valid;
	}

	@Override
	public String getExtrasUsage() {
		return "[block=x,y,z]";
	}

	@Override
	public void fillChunk(Chunk chunk) {
		int x = chunk.getLocation().getBlockX();
		int y = chunk.getLocation().getBlockY();
		int z = chunk.getLocation().getBlockZ();
		int xoff = this.x - x;
		int yoff = this.y - y;
		int zoff = this.z - z;
		if(xoff >= 0 && xoff < Chunk.SIZE && yoff >= 0 && yoff < Chunk.SIZE && zoff >= 0 && zoff < Chunk.SIZE) {
			chunk.set(xoff, yoff, zoff, Material.OBSIDIAN.getID());
		}
	}

}
