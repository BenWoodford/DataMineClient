package io.datamine.DataMineClient;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.*;

import io.datamine.DataMineClient.DataWrappers.*;

public class DataMineQueue {
	private HashMap<String, ArrayList<HashMap<String, Object>>> queue = new HashMap<String, ArrayList<HashMap<String, Object>>>();
	private HashMap<String, HashMap<String, Object>> tempStore = new HashMap<String, HashMap<String, Object>>();
	
	public void addWorld(World w)
	{
		
	}
	
	public void addEntry(World w, LogEvent event)
	{
		if(!event.stackable)
			this.queue.get(w.toString()).add(event.toHashMap());
		else {
			/* We stack by chunk
			 * So throw the chunk coordinates in the key.
			 */
			// Note to self: Restructure to use world names. ASAP
			if(this.tempStore.containsKey(event.type + "_" + event.chunk.x + "-" + event.chunk.y + "-" + event.chunk.z))
			{
				int tmp = (Integer) this.tempStore.get(event.type + "_" + event.chunk.x + "-" + event.chunk.y + "-" + event.chunk.z).get("count");
				tmp++;
				this.tempStore.get(event.type + "_" + event.chunk.x + "-" + event.chunk.y + "-" + event.chunk.z).put("count", tmp);
			} else {
				HashMap<String, Object> tmphash = new HashMap<String, Object>();
				tmphash.put("count", 1);
				this.tempStore.put(event.type + "_" + event.chunk.x + "-" + event.chunk.y + "-" + event.chunk.z, tmphash);
			}
		}
	}
	
	
}
