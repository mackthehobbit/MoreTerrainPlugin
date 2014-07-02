package com.mackthehobbit.mbplugin.terrain;

import com.mbserver.api.CommandExecutor;
import com.mbserver.api.CommandSender;

public class TerrainCommand implements CommandExecutor {
	
	public static final String permission_terrain = "mackthehobbit.terrain.terrain";
	public static final String usage_terrain = "Usage: /%s <name> <type> [key=value]...";
	
	private MoreTerrainPlugin plugin;
	
	public TerrainCommand(MoreTerrainPlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void execute(String command, CommandSender sender, String[] args, String label) {
		if(!sender.hasPermission(permission_terrain)) {
			sender.sendMessage("You do not have the required permission to use this command.");
		}
		
		try {
			
			// Check if a world with the same name exists already
			final String worldName = args[0];
			if(plugin.getServer().getWorld(worldName) != null) {
				sender.sendMessage("A world with that name already exists.");
				return;
			}
			
			// Try creating a new MoreTerrainGenerator instance
			final String generatorName = args[1];
			
			final MoreTerrainGenerator generator = plugin.getTerrainGenerator(generatorName);
			if(generator == null) {
				sender.sendMessage(String.format("Terrain generator '%s' does not exist", generatorName));
				return;
			}
			// Add the key=value extras to the generator
			generator.addExtras(args, 2);
			
			// Initiate the generator
			generator.init();
			
			// Check if the generator has all the needed extras
			if(!generator.canProceed()) {
				sender.sendMessage(generatorName + " usage: " + generator.getExtrasUsage());
				return;
			}
			
			// Start creating a new world with the generator, in a new thread
			new Thread(new Runnable() {
				@Override
				public void run() {
					TerrainCommand.this.plugin.getServer().createWorld(worldName, System.currentTimeMillis(), generator);
				}
			}).start();
			
		} catch (ArrayIndexOutOfBoundsException e) {
			sender.sendMessage(String.format(usage_terrain, label));
		}
		
	}
	
}
