package io.datamine.DataMineClient.DataWrappers;

import java.util.HashMap;

import org.bukkit.Location;

public class Chunk {
	public int x;
	public int y;
	public int z;
	
	private final int chunkSize = 16;
	
	public Chunk(Location l)
	{
		this.setCoordinates(l.getBlockX(), l.getBlockY(), l.getBlockZ());
	}
	
	public void setCoordinates(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void setCoordinates(float x, float y, float z)
	{
		this.x = Math.round(x);
		this.y = Math.round(y);
		this.z = Math.round(z);
	}
	
	public void setCoordinatesFromBlock(float x, float y, float z)
	{
		this.x = (int)(Math.floor(x / chunkSize) * chunkSize);
		this.y = (int)(Math.floor(y / chunkSize) * chunkSize);
		this.z = (int)z;
	}
	
	public HashMap<String, Integer> toHashMap()
	{
		HashMap<String, Integer> xyz = new HashMap<String, Integer>();
		xyz.put("x", this.x);
		xyz.put("y", this.y);
		xyz.put("z", this.z);
		
		return xyz;
	}
}
