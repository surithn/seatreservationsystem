package chapters.layouts;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;

public class SpringBootJSON extends LayoutBase<ILoggingEvent> {

	String application = null;
	String environment = System.getenv("SPRINGBOOT_PROFILE");
	
	public String getDate() {
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		df.setTimeZone(tz);
		String nowAsISO = df.format(new Date());
		return nowAsISO;
	}
	
	public static String removeDoubleQuotes(String s) {
		Pattern p = Pattern.compile("\"");
		Matcher m = p.matcher(s);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "'");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	public void setApplication(String application) {
		this.application = application;
	}
	
	public String doLayout(ILoggingEvent event) {
		StringBuffer sbuf = new StringBuffer(128);
		sbuf.append("{");
		sbuf.append("\"app\": \"" + application + "\", ");
		sbuf.append("\"environment\": \"" + environment + "\", ");
		sbuf.append("\"date\": \"" + getDate() + "\", ");
		sbuf.append("\"loglevel\": \"" + event.getLevel() + "\", ");
		sbuf.append("\"thread\": \"" + event.getThreadName() + "\", ");
		sbuf.append("\"logger\": \"" + event.getLoggerName() + "\", ");
		sbuf.append("\"msg\": \"" +
		removeDoubleQuotes(event.getFormattedMessage()) + "\"");
		sbuf.append("}\n");
		return sbuf.toString();
	}
	
}