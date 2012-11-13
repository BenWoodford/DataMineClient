package io.datamine.DataMineClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;

public class DataMineSender {
	
	private DataMineClient plugin;
	
	public DataMineSender(DataMineClient plugin)
	{
		this.plugin = plugin;
	}
	
	public void sendJSON(String json)
	{
		try {
			URL url;
		    URLConnection urlConn;
		    DataOutputStream printout;
		    DataInputStream input;
		    // URL of CGI-Bin script.
		    url = new URL(this.plugin.getConfig().getString("api.endpoint"));
		    // URL connection channel.
		    urlConn = url.openConnection();
		    // Let the run-time system (RTS) know that we want input.
		    urlConn.setDoInput(true);
		    // Let the RTS know that we want to do output.
		    urlConn.setDoOutput(true);
		    // No caching, we want the real thing.
		    urlConn.setUseCaches(false);
		    // Specify the content type.
		    urlConn.setRequestProperty
		    ("Content-Type", "application/x-www-form-urlencoded");
		    // Send POST output.
		    printout = new DataOutputStream(urlConn.getOutputStream ());

		    String content = "";
		    content += "username=" + URLEncoder.encode(json, "UTF-8");
		    content += "&key=" + URLEncoder.encode(this.plugin.getConfig().getString("api.key"), "UTF-8");

		    printout.writeBytes (content);
		    printout.flush();
		    printout.close();
		    // Get response data.
		    input = new DataInputStream(urlConn.getInputStream());
		    String str;
		    String output = "";

		    while (null != ((str = input.readLine())))
		    	output += str;//System.out.println (str);

		    input.close ();
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
