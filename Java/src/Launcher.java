import java.util.LinkedList;
import java.util.Queue;

public class Launcher extends Thread{
	private String id;
	private boolean isHidden;
	private Queue<Missile> missleQueue;
	public Launcher() {
		this.id = "L" + (int) (Math.random() * 1000);
		this.isHidden = (Math.random() < 0.5);
		this.missleQueue = new LinkedList<Missile>();
	}

	public Launcher(String id, String isHidden) {
		this.id = id;
		this.isHidden = Boolean.parseBoolean(isHidden);
		this.missleQueue = new LinkedList<Missile>();
	}

	public String getID() {
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

	@Override
	public String toString() {
		return "Launcher id=" + id + ", isHidden=" + isHidden
				+ " ";
	}
	

}
