package io.datamine.DataMineClient.Listeners;

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

import io.datamine.DataMineClient.DataMineClient;
import io.datamine.DataMineClient.DataWrappers.Chunk;
import io.datamine.DataMineClient.DataWrappers.LogEvent;
import io.datamine.DataMineClient.DataWrappers.World;

import org.bukkit.event.Listener;
import org.bukkit.event.block.*;

public class Blocks implements Listener {

	private DataMineClient plugin;

	public Blocks(DataMineClient plugin) {
		this.plugin = plugin;
	}

	public void onRedstone(BlockRedstoneEvent event)
	{
		this.plugin.addToQueue(new LogEvent(new World(event.getBlock().getWorld()), new Chunk(event.getBlock().getLocation()), "redstone", true, null));
	}
}
