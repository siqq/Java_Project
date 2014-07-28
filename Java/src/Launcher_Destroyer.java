import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class Launcher_Destroyer extends Thread {

	private String type;
	private boolean isAlive = true;
	private Vector<Enemy_Launcher> allLaunchers = new Vector<Enemy_Launcher>();
	private Queue<Enemy_Launcher> waitingLaunchers = new LinkedList<Enemy_Launcher>();

	// private Missle_Launcher allocatedMissile = new Missle_Launcher();
	public Launcher_Destroyer(String type) {
		this.type = type;
	}

	public void destroyLauncher(String id) {
		for (Enemy_Launcher enemy_Launcher : War.launchers) {
			if (enemy_Launcher.getID() == id) {
				if (enemy_Launcher.isHidden()) {
					System.out.println("can't destroy Launcher id# " + id);
					break;
				}
				synchronized (this) {
					System.out.println("Launcher id# " + id + " destroy ");
					enemy_Launcher.destroy();
					War.enemyMissile.remove(enemy_Launcher);
				}
			}
		}
	}

	public  void addWaitingLauncherToDesroy (String id , String destructTime) {



		for(Enemy_Launcher enemy_Launcher : War.launchers) { 
			if(enemy_Launcher.getID() == id){

				waitingLaunchers.add(enemy_Launcher);
			}
		}
	}

	public void notifyLauncher() {
		Enemy_Launcher firstLauncher = waitingLaunchers.poll();
		if (firstLauncher != null) {

			synchronized (firstLauncher) {
				firstLauncher.notifyAll();
			}
		}

	}


	public void run() {
		while (isAlive) {
			if (!waitingLaunchers.isEmpty()) {
				notifyLauncher();
			} else {
				synchronized (/* dummyWaiter */this) {
					try {
						System.out.println("Launcher has no missiles");
						/* dummyWaiter. */wait(); // wait till there is an
						// airplane
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
