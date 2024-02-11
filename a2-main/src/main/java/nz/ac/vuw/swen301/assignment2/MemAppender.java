package nz.ac.vuw.swen301.assignment2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

public class MemAppender extends AppenderSkeleton{
	private List<LoggingEvent> events = new ArrayList<LoggingEvent>();
	private long maxSize = 1000;
	private long discardedLogs = 0;

	public MemAppender() {

	}

	public void doAppend(LoggingEvent event) {
		if(events.size() == maxSize) {
			events.remove(0);
			discardedLogs++;
			return;
		}
		events.add(event);
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LoggingEvent> getCurrentLogs() {
		return Collections.unmodifiableList(events);
	}

	public void exportToJSON(String fileName) {
		String out = "";
		for (LoggingEvent e: events) {
			out += new JsonLayout().format(e);
		}
		try {
			FileWriter myWriter = new FileWriter(fileName);
			myWriter.write(out);
			myWriter.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public long getMaxSize() {
		return maxSize;
	}


	public void setMaxSize(long maxSize) {
		this.maxSize = maxSize;
	}

	public long getDiscardedLogs() {
		return discardedLogs;
	}

	public void close() {
		// do nothing
		return;
	}

	public boolean requiresLayout() {

		return false;
	}

	@Override
	protected void append(LoggingEvent event) {
		// TODO Auto-generated method stub

	}
}