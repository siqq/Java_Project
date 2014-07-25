import java.util.LinkedList;
import java.util.Queue;

public class Missle_Launcher extends Thread {
	private String id;
	private boolean isHidden;
	private Queue<Missile> missleQueue;

	public Missle_Launcher() {
		this.id = "L" + (int) (Math.random() * 1000);
		this.isHidden = (Math.random() < 0.5);
		this.missleQueue = new LinkedList<Missile>();
	}

	public Missle_Launcher(String id, String isHidden) {
		this.id = id;
		this.isHidden = Boolean.parseBoolean(isHidden);
		this.missleQueue = new LinkedList<Missile>();
		this.start();
	}

	public void fireMissile() {
		Missile m = missleQueue.poll();
		if (m != null) {
			
		}
	}

	public Queue<Missile> getMissleQueue() {
		return missleQueue;
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
	public void run() {

	}

	@Override
	public String toString() {
		return "Launcher id= " + id + ", isHidden= " + isHidden + " ";
	}

}
