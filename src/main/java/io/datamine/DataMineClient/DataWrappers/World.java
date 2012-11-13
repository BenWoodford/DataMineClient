package io.datamine.DataMineClient.DataWrappers;

public class World {
	public org.bukkit.World cbWorld = null;
	
	public World(org.bukkit.World w)
	{
		this.cbWorld = w;
	}
	
	public void setWorld(org.bukkit.World world)
	{
		cbWorld = world;
	}
	
	public org.bukkit.World getWorld()
	{
		return cbWorld;
	}
	
	@Override public String toString()
	{
		return this.cbWorld.getName();
	}
}
