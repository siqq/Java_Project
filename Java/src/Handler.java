import java.io.IOException;
import java.util.logging.FileHandler;

public class Handler extends FileHandler {
	
	public Handler(String name) throws IOException, SecurityException {
		super(name + ".txt",true);		
		setFormatter(new LogFormatter());
		
		
	}

	public Handler(String name,Object obj) throws IOException, SecurityException {
		super(name + ".txt",true);
		setFilter(new ObjectFilter(obj));
		setFormatter(new LogFormatter());

	}

	public Handler(String name, String id, Object obj) throws IOException, SecurityException {
		super(name + "_" + id + ".txt",true);
		setFilter(new ObjectFilter(obj));
		setFormatter(new LogFormatter());		

	}

}
