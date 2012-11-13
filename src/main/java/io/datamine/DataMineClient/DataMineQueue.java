package io.datamine.DataMineClient;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.*;

import io.datamine.DataMineClient.DataWrappers.*;

public class DataMineQueue {
	private HashMap<String, ArrayList<HashMap<String, Object>>> mainQueue = new HashMap<String, ArrayList<HashMap<String, Object>>>();
	private HashMap<String, HashMap<String, HashMap<String, Object>>> stackedQueue = new HashMap<String, HashMap<String, HashMap<String, Object>>>();
	
	private DataMineClient plugin;
	
	public DataMineQueue(DataMineClient plugin)
	{
		this.plugin = plugin;
	}
	
	public void addWorld(World w)
	{
		ArrayList<HashMap<String, Object>> mainQueue = new ArrayList<HashMap<String, Object>>();
		HashMap<String, HashMap<String, Object>> stackedQueue = new HashMap<String, HashMap<String, Object>>();
		
		this.mainQueue.put(w.toString(), mainQueue);
		this.stackedQueue.put(w.toString(), stackedQueue);
	}
	
	public void addEntry(World w, LogEvent event)
	{
		if(!event.stackable)
		{
			ArrayList<HashMap<String, Object>> tmpHash = (ArrayList<HashMap<String, Object>>) this.mainQueue.get(w.toString());
			tmpHash.add(event.toHashMap());
			this.mainQueue.put(w.toString(), tmpHash);
		}
		else {
			/* We stack by chunk
			 * So throw the chunk coordinates in the key.
			 */
			HashMap<String, HashMap<String, Object>> tempStore = this.stackedQueue.get(w.toString());
			
			if(tempStore.containsKey(event.type + "_" + event.chunk.x + "-" + event.chunk.y + "-" + event.chunk.z))
			{
				HashMap<String, Object> ev = tempStore.get(event.type + "_" + event.chunk.x + "-" + event.chunk.y + "-" + event.chunk.z);
				int tmp = (Integer) ev.get("count");
				tmp++;
				ev.put("count", tmp);
				tempStore.put(event.type + "_" + event.chunk.x + "-" + event.chunk.y + "-" + event.chunk.z, ev);
			} else {
				HashMap<String, Object> tmphash = new HashMap<String, Object>();
				tmphash.put("count", 1);
				tempStore.put(event.type + "_" + event.chunk.x + "-" + event.chunk.y + "-" + event.chunk.z, tmphash);
			}
			
			this.stackedQueue.put(w.toString(), tempStore);
		}
	}
	
	@SuppressWarnings("unchecked") // I hate using this.
	public void send()
	{
		JSONObject json = new JSONObject();
		
		try {
			json.put("main", this.mainQueue.clone());
			json.put("stacked", this.stackedQueue.clone());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if(json.size() > 0)
		{
			DataMineSender sender = new DataMineSender(this.plugin);
			sender.sendJSON(json.toJSONString());
		}
	}
}
