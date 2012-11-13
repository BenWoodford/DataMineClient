package io.datamine.DataMineClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import io.datamine.DataMineClient.DataWrappers.*;

public class DataMineNonEvent {

	DataMineClient plugin;
	
	public DataMineNonEvent(DataMineClient plugin)
	{
		this.plugin = plugin;
	}
	
	public void getData()
	{
		getEntities();
	}
	
	public void getEntities()
	{
		List<org.bukkit.World> worlds = this.plugin.getServer().getWorlds();
		for(org.bukkit.World w : worlds)
		{
			// First, let's do the totals per world per type.
			HashMap<Short, Integer> counts = new HashMap<Short, Integer>();
			List<Entity> entities = w.getEntities();
			
			for(EntityType et : EntityType.values())
				counts.put(et.getTypeId(), 0);
			
			for(Entity e : entities)
			{
				counts.put(e.getType().getTypeId(), counts.get(e.getType().getTypeId() + 1));
				this.plugin.addToQueue(new LogEvent(new World(w), new Chunk(e.getLocation()), "entities", true, null));
			}
			
			for (Map.Entry<Short, Integer> entry : counts.entrySet())
			{
				EntityType et = EntityType.fromId(entry.getKey());
				if(et == null)
					continue;
				
				HashMap<String, Object> additionals = new HashMap<String, Object>();
				additionals.put("count", entry.getValue());
				
				this.plugin.addToQueue(new LogEvent(new World(w), null, et.name().toLowerCase(), false, additionals));
			}
		}
	}
}
