package nz.ac.vuw.swen301.assignment2;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;
import org.json.JSONObject;

/**
 * 
 * @author Joshua Keegan 300523483 
 *
 */

public class JsonLayout extends PatternLayout{
	@Override
	public String format(LoggingEvent e) {
		String loggername = e.getLoggerName();
		String loglevel = e.getLevel().toString();
		String starttime = Long.toString(LoggingEvent.getStartTime());
		String thread = e.getThreadName();
		String message = (String) e.getMessage();
		
		Map<String, String> m = new HashMap<String, String>();
		m.put("loggername", loggername);
		m.put("loglevel", loglevel);
		m.put("starttime", starttime);
		m.put("thread", thread);
		m.put("message", message);
		
		
		JSONObject jo = new JSONObject(m);
		return jo.toString();
	}
}
