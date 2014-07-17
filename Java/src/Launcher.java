import java.util.LinkedList;
import java.util.Queue;

public class Launcher {
	private String id;
	private boolean isHidden;
	private Queue<Missile> missleQueue;
	int maniac;
	public Launcher() {
		this.id = "L" + (int) (Math.random() * 1000);
		this.isHidden = (Math.random() < 0.5);
		this.missleQueue = new LinkedList<Missile>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isHidden() {
		return isHidden;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

}
