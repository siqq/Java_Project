import java.util.LinkedList;
import java.util.Queue;

public class Enemy_Launcher implements Runnable {
	private String id;
	private boolean isHidden;
	private Queue<Enemy_Missile> missleQueue = new LinkedList<Enemy_Missile>();
	private Queue<Enemy_Missile> waitingMissile = new LinkedList<Enemy_Missile>();
	private boolean isAlive = true;

	public Enemy_Launcher() {
		this.id = "L" + (int) (Math.random() * 1000);
		this.isHidden = (Math.random() < 0.5);
		this.missleQueue = new LinkedList<Enemy_Missile>();
	}

	public Enemy_Launcher(String id, String isHidden) {
		this.id = id;
		this.isHidden = Boolean.parseBoolean(isHidden);
		this.missleQueue = new LinkedList<Enemy_Missile>();
		//this.start();
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
	public void destroyedLauncher() {
		isAlive = false;
		synchronized (/*dummyWaiter*/this) {
			/*dummyWaiter.*/notifyAll();
		}
	}
	public synchronized void notifyMissile() {
		Enemy_Missile firstMissile = waitingMissile.poll();
		if (firstMissile != null) {

			System.out.println("Missile number #"
					+ firstMissile.getId() + " can go");
			synchronized (firstMissile) {
				firstMissile.notifyAll();
			}
		}

		try {

			System.out.println("Launcher waits that  missile #"
					+ firstMissile.getId()
					+ " will finish");

			wait(); // wait till the airplane finishes

			System.out.println("Launcher announced that missile #"
					+ firstMissile.getId() + " is finished");

		} catch (InterruptedException e) {
			e.printStackTrace();
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

	public void run() {
		//System.out.println("In Airport::run");
		while (isAlive) {
			if (!waitingMissile.isEmpty()) {
				notifyMissile();
			} else {
				synchronized (/*dummyWaiter*/this) {
					try {
						System.out.println("Launcher id: " + this.id + " has no missile on it");
						/*dummyWaiter.*/wait(); // wait till there is an missile waiting
						System.out.println("New missile has been loaded on the launcher");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	@Override
	public String toString() {
		return "Launcher id= " + id + ", isHidden= " + isHidden + " ";
	}

}
