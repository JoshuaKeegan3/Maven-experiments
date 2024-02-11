package nz.ac.vuw.swen301.assignment2;

import java.util.List;

import org.apache.log4j.spi.LoggingEvent;

public class Monitor implements MonitorMBean {
	MemAppender appender;
	public Monitor(MemAppender appender) {
		this.appender = appender;
	}

	public String[] getLogs() {
		// TODO Auto-generated method stub
		List<LoggingEvent> events = appender.getCurrentLogs();
		String[] s = new String[events.size()];
		for(int i=0;i<s.length;i++) {
			s[i] = events.get(i).toString();
		}
		return s;
	}

	public long getLogCount() {
		// TODO Auto-generated method stub
		return appender.getCurrentLogs().size();
	}

	public void exportToJSON(String fileName) {
		// TODO Auto-generated method stub
		appender.exportToJSON(fileName);
	}

	public long getDiscardedLogCount() {
		// TODO Auto-generated method stub
		return appender.getDiscardedLogs();
	}

}
