import java.util.logging.Formatter;
import java.util.logging.LogRecord;


public class LogFormatter extends Formatter {

	@Override
	public String format(LogRecord rec) {
		StringBuffer buffer = new StringBuffer(1000);
		buffer.append(new java.util.Date().toLocaleString());
		buffer.append(" ");
		buffer.append(rec.getMessage());
		buffer.append("\n\n");
		
		return buffer.toString();
	}

}
