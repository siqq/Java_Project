import java.util.LinkedList;
import java.util.Queue;



public class Enemy_Launcher extends Thread {
	private String id;
	private boolean isHidden;
	private Queue<Enemy_Missile> missleQueue = new LinkedList<Enemy_Missile>();
	private Queue<Enemy_Missile> waitingMissile = new LinkedList<Enemy_Missile>();

	public Enemy_Launcher() {
		this.id = "L" + (int) (Math.random() * 1000);
		this.isHidden = (Math.random() < 0.5);
		this.missleQueue = new LinkedList<Enemy_Missile>();
	}

	public Enemy_Launcher(String id, String isHidden) {
		this.id = id;
		this.isHidden = Boolean.parseBoolean(isHidden);
		this.missleQueue = new LinkedList<Enemy_Missile>();
		this.start();
	}
	public void addMissile(Enemy_Missile newMissile) {
		missleQueue.add(newMissile);
		newMissile.start();
	}
	public synchronized void addWaitingMissile(Enemy_Missile newMissile) {
		waitingMissile.add(newMissile);

		System.out.println("After adding airplane #" + newMissile.getId()
				+ " there are " + waitingMissile.size()
				+ " airplanes waiting");

		synchronized (/*dummyWaiter*/this) {
			if (waitingMissile.size() == 1) {
				/*dummyWaiter.*/notify(); // to let know there is an airplane
										// waiting
			}
		}
	}

	public void fireMissile() {
		Enemy_Missile m = missleQueue.poll();
		if (m != null) {
			
		}
	}

	public Queue<Enemy_Missile> getMissleQueue() {
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
