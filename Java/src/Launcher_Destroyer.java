import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class Launcher_Destroyer implements Runnable{
	
	private String type;
	private Vector<Launcher_Destroyer_missile> allMissiles = new Vector<Launcher_Destroyer_missile>();
	private Queue<Launcher_Destroyer_missile> waitingMissiles = new LinkedList<Launcher_Destroyer_missile>();
	private boolean isAlive = true;
	//private Missle_Launcher allocatedMissile = new Missle_Launcher();
	public Launcher_Destroyer(String type) {
		this.type = type;
	}
	public void addMissile(Launcher_Destroyer_missile newLauncherDestroyerMissile) {
		allMissiles.add(newLauncherDestroyerMissile);
		newLauncherDestroyerMissile.start();
	}

	public void closeLauncher() {
		isAlive = false;
		synchronized (/*dummyWaiter*/this) {
			/*dummyWaiter.*/notifyAll();
		}
	}

	public synchronized void addWaitingAirplane(Launcher_Destroyer_missile a) {
		waitingMissiles.add(a);

		System.out.println("After adding missile #" + a.getTheId()
				+ " there are " + waitingMissiles.size()
				+ " missiles waiting");

		synchronized (/*dummyWaiter*/this) {
			if (waitingMissiles.size() == 1) {
				/*dummyWaiter.*/notify(); // to let know there is an airplane
										// waiting
			}
		}
	}

	public synchronized void notifyMissile() {
		Launcher_Destroyer_missile firstMissile = waitingMissiles.poll();
		if (firstMissile != null) {

			System.out.println("Launcher is notifying missile #"
					+ firstMissile.getTheId());
			synchronized (firstMissile) {
				firstMissile.notifyAll();
			}
		}
			try {

				System.out.println("Launcher waits that  missile #"
						+ firstMissile.getTheId()
						+ " will announce it is finished");

				wait(); // wait till the airplane finishes

				System.out.println("Airport was announced that  Missile #"
						+ firstMissile.getTheId() + " is finished");

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

	public void run() {
		System.out.println("In Airport::run");
		while (isAlive) {
			if (!waitingMissiles.isEmpty()) {
				notifyMissile();
			} else {
				synchronized (/*dummyWaiter*/this) {
					try {
						System.out.println("Launcher has no missiles");
						/*dummyWaiter.*/wait(); // wait till there is an airplane
											// waiting
						System.out.println("Launcher recieved a missile ");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	@Override
	public String toString() {
		return "Missile_Launcher_Destructor type= " + type;
	}
	

}
