import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class Iron_Dome implements Runnable {
	private String id;
	private Vector<IronDomeMissile> allMissiles = new Vector<IronDomeMissile>();
	private Queue<IronDomeMissile> waitingMissile = new LinkedList<IronDomeMissile>();
	private boolean isAlive = true;

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

	public void emptyLauncher() {
		isAlive = false;
		synchronized (/*dummyWaiter*/this) {
			/*dummyWaiter.*/notifyAll();
		}
	}

	public synchronized void addWaitinMissile(IronDomeMissile ironDomeMissile) {
		waitingMissile.add(ironDomeMissile);

		System.out.println("After adding missile #" + ironDomeMissile.getId()
				+ " there are " + waitingMissile.size()
				+ " missiles");

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

			System.out.println("Airport is notifying missile #"
					+ firstMissile.getId());
			synchronized (firstMissile) {
				firstMissile.notifyAll();
			}
		}

		try {

			System.out.println("IronDome waits that  missile #"
					+ firstMissile.getId()
					+ " will announce it is finished");

			wait(); // wait till the airplane finishes

			System.out.println("IronDome was announced that  missile #"
					+ firstMissile.getId() + " is finished");

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
						System.out.println("Iron dome has no missiles");
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
