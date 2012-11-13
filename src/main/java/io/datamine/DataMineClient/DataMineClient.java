package io.datamine.DataMineClient;

/*
    This file is part of DataMineClient

    Foobar is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Foobar is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

import io.datamine.DataMineClient.Commands.Commands;
import io.datamine.DataMineClient.DataWrappers.LogEvent;
import io.datamine.DataMineClient.Listeners.Blocks;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class DataMineClient extends JavaPlugin {

	//ClassListeners
	private Commands commandExecutor;
	private Blocks entityEventListener;
	//ClassListeners
	
	private DataMineQueue queue;
	
	public int asyncTaskId;

	public void onDisable() {
		this.entityEventListener = null;
		this.commandExecutor = null;
		this.getServer().getScheduler().cancelTask(asyncTaskId);
	}

	public void onEnable() {		
		PluginManager pm = this.getServer().getPluginManager();
		commandExecutor = new Commands(this);
		entityEventListener = new Blocks(this);
		
		queue = new DataMineQueue(this);

		getCommand("datamine").setExecutor(commandExecutor);

		// you can register multiple classes to handle events if you want
		// just call pm.registerEvents() on an instance of each class
		pm.registerEvents(entityEventListener, this);

		this.getConfig().addDefault("api.endpoint", "http://api.loadingchunks.net/datamine/endpoint.php");
		this.getConfig().addDefault("api.key", "idjekowefkokwfjiwejigrjoeskjidfher939iejfisjiwer93i0ew");
		this.getConfig().addDefault("api.polling", 60);
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		
		this.startScheduler(this.getConfig().getInt("api.polling"));
	}
	
	public DataMineQueue getQueue()
	{
		return this.queue;
	}
	
	public void addToQueue(LogEvent event)
	{
		this.queue.addEntry(event.world, event);
	}
	
	public void sendQueue()
	{
		this.queue.send();
	}
	
	public void startScheduler(int seconds)
	{
		asyncTaskId = this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
			public void run()
			{
				getLogger().info("Processing Queue...");
				sendQueue();
			}
		}
		,20 * seconds, 20 * seconds);
	}
	
}
