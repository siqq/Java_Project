package logger;
import java.io.IOException;
import java.util.logging.FileHandler;

public class Handler extends FileHandler {
	
	public Handler(String name) throws IOException, SecurityException {		
		super("loggerFiles/" + name + ".txt",false);		
		setFormatter(new LogFormatter());
		
		
	}

	public Handler(String name,Object obj) throws IOException, SecurityException {
		super("loggerFiles/" + name + ".txt",false);
		setFilter(new ObjectFilter(obj));
		setFormatter(new LogFormatter());

	}

	public Handler(String name, String id, Object obj) throws IOException, SecurityException {
		super("loggerFiles/" + name + "_" + id + ".txt",false);
		setFilter(new ObjectFilter(obj));
		setFormatter(new LogFormatter());		

	}
	public Handler(String name, int id, Object obj) throws IOException, SecurityException {
		super("loggerFiles/" + name + "_" + id + ".txt",false);
		setFilter(new ObjectFilter(obj));
		setFormatter(new LogFormatter());		

	}

}
