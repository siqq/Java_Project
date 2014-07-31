import java.io.IOException;
import java.util.logging.FileHandler;

public class Handler extends FileHandler {
	
	public Handler(String name) throws IOException, SecurityException {
		super(name + ".txt");		
		setFormatter(new LogFormatter());
	}

	public Handler(String name,Object obj) throws IOException, SecurityException {
		super(name + ".txt");
		setFilter(new ObjectFilter(obj));
		setFormatter(new LogFormatter());

	}

	public Handler(String name, String id, Object obj) throws IOException, SecurityException {
		super(name + "_" + id + ".txt");
		setFilter(new ObjectFilter(obj));
		setFormatter(new LogFormatter());		

	}

}
