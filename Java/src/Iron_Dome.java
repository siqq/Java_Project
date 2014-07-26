import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class Iron_Dome implements Runnable {
	private String id;
	private Vector<IronDomeMissile> allMissiles = new Vector<IronDomeMissile>();
	private Queue<IronDomeMissile> waitingMissile = new LinkedList<IronDomeMissile>();
	private Queue<Enemy_Launcher> enemyMissile = new LinkedList<Enemy_Launcher>();

	private boolean isAlive = true;
	Enemy_Launcher enemy_Launcher;

	public Iron_Dome() {
		this.id = "D" + (int) (Math.random() * 1000);
	}

	public Iron_Dome(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "missileDestructor id " + id;
	}

	public void addIronDomeMissile(IronDomeMissile newIronDomeMissile) {
		allMissiles.add(newIronDomeMissile);	
		newIronDomeMissile.start();
	}
//	public void addEnemyMissile(Enemy_Missile enemy_Missile) {
//		enemyMissile.add(enemy_Missile);
//	}
	public Queue<Enemy_Launcher> getEnemyMissile() {
		return enemyMissile;
	}

	public void emptyLauncher() {
		isAlive = false;
		synchronized (/*dummyWaiter*/this) {
			/*dummyWaiter.*/notifyAll();
		}
	}
	public void removeIronDomeMissile(IronDomeMissile newIronDomeMissile) {

		waitingMissile.remove(newIronDomeMissile);
	}

	public synchronized void addWaitinMissile(IronDomeMissile ironDomeMissile) {
		waitingMissile.add(ironDomeMissile);

		System.out.println("After adding missile #" + ironDomeMissile.getID()
				+ " there are " + waitingMissile.size()
				+ " missiles to intercept");

		synchronized (/*dummyWaiter*/this) {
			if (waitingMissile.size() == 1) {
				/*dummyWaiter.*/notify(); // to let know there is an airplane
				// waiting
			}
		}
	}

	public synchronized void notifyMissile() {
		IronDomeMissile firstMissile = waitingMissile.poll();
		if (firstMissile != null) {

			System.out.println("IronDome is warming interceptor to destroy missile id# "
					+ firstMissile.getID());
			synchronized (firstMissile) {
				firstMissile.notifyAll();
			}
		}

		try {

			System.out.println("IronDome waits that missile # "
					+ firstMissile.getID()
					+ " will announce it is finished");

			wait(); // wait till the airplane finishes

			System.out.println("IronDome was announced that  missile # "
					+ firstMissile.getID() + " is finished");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (isAlive) {
			if (!waitingMissile.isEmpty()) {
				notifyMissile();
			} else {
				synchronized (/*dummyWaiter*/this) {
					try {
						System.out.println("Iron dome has no missiles and waiting for loading missile");
						/*dummyWaiter.*/wait(); // wait till there is an airplane
						// waiting
						System.out.println("IronDome recieved a missile");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}
}
