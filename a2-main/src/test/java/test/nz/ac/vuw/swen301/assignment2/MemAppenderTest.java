package test.nz.ac.vuw.swen301.assignment2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Scanner;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import nz.ac.vuw.swen301.assignment2.JsonLayout;
import nz.ac.vuw.swen301.assignment2.MemAppender;
import nz.ac.vuw.swen301.assignment2.Monitor;

public class MemAppenderTest {
	private static final Logger LOGGER_FILE = Logger.getLogger("FILE");
	@Before
	public void before() {
		BasicConfigurator.configure();
		File f = new File("output.json");
		f.delete();
	}
	@Test
	public void testValid_01() throws IOException, InstanceAlreadyExistsException,
	MBeanRegistrationException, NotCompliantMBeanException, MalformedObjectNameException {
		MemAppender appender = new MemAppender();

		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName name = new ObjectName("nz.ac.vuw.swen301.assignment2:type=Monitor");
		Monitor mbean = new Monitor(appender);
		mbs.registerMBean(mbean, name);

		appender.doAppend(new LoggingEvent("foo", Logger.getLogger("foo"), Level.DEBUG, "interesting !", null));

		appender.exportToJSON("output.json");

		File f = new File("output.json");
		Scanner sc = new Scanner(f);
		String s = "";
		while (sc.hasNext()) {
			s += sc.next();
		}
		assertTrue(isJSONValid(s.toString()));

		for (int i=0;i<appender.getMaxSize()*2;i++) {
			appender.doAppend(new LoggingEvent("foo", Logger.getLogger(""+i), Level.DEBUG, ""+i, null));
		}
		appender.exportToJSON("output.json");

		assertTrue(isJSONValid(s.toString()));
		assertEquals(appender.getMaxSize(), appender.getCurrentLogs().size());
		appender.setName("");
		assertEquals(appender.getName(), "");

	}

	public boolean isJSONValid(String s) {
	    try {
	        new JSONObject(s);
	    } catch (JSONException e) {
	        try {
	            new JSONArray(s);
	        } catch (JSONException e2) {
	            return false;
	        }
	    }
	    return true;
	}
}


