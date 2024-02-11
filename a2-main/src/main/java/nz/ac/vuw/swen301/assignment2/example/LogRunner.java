package nz.ac.vuw.swen301.assignment2.example;

import java.lang.management.ManagementFactory;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.apache.log4j.Logger;

import nz.ac.vuw.swen301.assignment2.MemAppender;
import nz.ac.vuw.swen301.assignment2.Monitor;

public class LogRunner {

	private static final Logger LOGGER_FILE = Logger.getLogger("FILE");
	private static final long t = System.currentTimeMillis();

	public static void main(String[] args) throws MalformedObjectNameException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
		// TODO Auto-generated method stub

		MemAppender appender = new MemAppender();

		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName name = new ObjectName("nz.ac.vuw.swen301.assignment2:type=Monitor");
		final Monitor mbean = new Monitor(appender);
		mbs.registerMBean(mbean, name);
		LOGGER_FILE.addAppender(appender);

		final Timer timer = new Timer();
		final Random r = new Random();
		timer.schedule(new TimerTask() {
		  @Override
		  public void run() {
			  System.out.println((t-System.currentTimeMillis())/1000);
			  switch(r.nextInt(5)) {
			  case 0:
			  	LOGGER_FILE.info(Double.toString(r.nextDouble()));
			  	break;
			  case 1:
			  	LOGGER_FILE.debug(Double.toString(r.nextDouble()));
			  	break;
			  case 2:
				  	LOGGER_FILE.warn(Double.toString(r.nextDouble()));
				  	break;
			  case 3:
				  	LOGGER_FILE.error(Double.toString(r.nextDouble()));
				  	break;
		  	  case 4:
			  	LOGGER_FILE.fatal(Double.toString(r.nextDouble()));
			  	break;
			  }
			  mbean.getLogs();
			  mbean.getLogCount();
			  mbean.exportToJSON("output.json");

		  }
		}, 0, 1000);//wait 0 ms before doing the action and do it evry 1000ms (1second)
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
				  timer.cancel();

			  }
			}, 120 * 1000, 1);//wait 0 ms before doing the action and do it evry 1000ms (1second)

	}

}
