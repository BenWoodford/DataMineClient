package io.datamine.DataMineClient.Listeners;

import java.util.HashMap;

import io.datamine.DataMineClient.DataMineClient;
import io.datamine.DataMineClient.DataWrappers.*;

import org.bukkit.Bukkit;

import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class Entities implements Listener {

	private DataMineClient plugin;

	public Entities(DataMineClient plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event)
	{
		HashMap<String, Object> additionals = new HashMap<String, Object>();
		additionals.put("entity_type", event.getEntityType().getName());
		Player killer = event.getEntity().getKiller();
		if(killer != null)
			additionals.put("killer", killer.getName());
		
		this.plugin.addToQueue(new LogEvent(new World(event.getEntity().getWorld()), new Chunk(event.getEntity().getLocation()), "death", false, additionals));
	}
}
