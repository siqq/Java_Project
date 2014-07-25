import java.util.Calendar;
import java.util.Vector;

public class Iron_Dome extends Thread {
	private String id;

	public Iron_Dome() {
		this.id = "D" + (int) (Math.random() * 1000);
	}

	public Iron_Dome(String id) {
		this.id = id;
	}

	@Override
	public void run() {

	}

	@Override
	public String toString() {
		return "missileDestructor id " + id;
	}
}
