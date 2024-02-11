package test.nz.ac.vuw.swen301.assignment2;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import nz.ac.vuw.swen301.assignment2.JsonLayout;

public class JsonLayoutTest {
	String starttime = Long.toString(LoggingEvent.getStartTime());
	@Before
	public void before() {
		BasicConfigurator.configure();
		File f = new File("output.json");
		f.delete();
	}
	@Test
	public void testValid_01() throws IOException {

		LoggingEvent e1 = new LoggingEvent("foo", Logger.getLogger("foo"), Level.WARN, "something went wrong", null);
		LoggingEvent e2 = new LoggingEvent("foo", Logger.getLogger("foo"), Level.DEBUG, "interesting !", null);
		String s1 = new JsonLayout().format(e1);
		String s2 = new JsonLayout().format(e2);

		JSONObject jo1 = new JSONObject(s1);
		JSONObject jo2 = new JSONObject(s2);


		assertTrue(jo1.get("loggername").equals("foo"));
		assertTrue(jo1.get("loglevel").equals("WARN"));
		assertTrue(jo1.get("starttime").equals(Long.toString(LoggingEvent.getStartTime())));
		assertTrue(jo1.get("thread").equals("main"));
		assertTrue(jo1.get("message").equals("something went wrong"));

		assertTrue(jo2.get("loggername").equals("foo"));
		assertTrue(jo2.get("loglevel").equals("DEBUG"));
		assertTrue(jo2.get("starttime").equals(Long.toString(LoggingEvent.getStartTime())));
		assertTrue(jo2.get("thread").equals("main"));
		assertTrue(jo2.get("message").equals("interesting !"));
	}
}
