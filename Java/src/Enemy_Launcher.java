import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class Enemy_Launcher extends Thread {
	private String id;
	private boolean isHidden;
	private Queue<Enemy_Missile> missileQueue = new LinkedList<Enemy_Missile>();
	private Queue<Enemy_Missile> allMissiles = new LinkedList<Enemy_Missile>();
	private boolean iSAlive = true;
	private Enemy_Missile currentMissile;

	public Queue<Enemy_Missile> getAllMissiles() {
		return allMissiles;
	}

	public Enemy_Launcher() throws SecurityException, IOException {
		this.id = "L" + (int) (Math.random() * 1000);
		this.isHidden = (Math.random() < 0.5);
		War.theLogger.addHandler((new Handler(this.getClass().getName(), id, this)));
	}

	public Enemy_Launcher(String id, String isHidden) {
		this.id = id;
		this.isHidden = Boolean.parseBoolean(isHidden);
		try {
			War.theLogger.addHandler((new Handler(this.getClass().getName(), id, this)));
		} catch (Exception e) {

		}

	}

	public Enemy_Launcher(String id) {
		this.id = id;
		this.missileQueue = new LinkedList<Enemy_Missile>();
	}

	public void addMissile(Enemy_Missile newMissile) throws InterruptedException {
		allMissiles.add(newMissile);
		missileQueue.add(newMissile);
		newMissile.start();	
	}

	public void notifyMissile() {
		 currentMissile = missileQueue.poll();
		if (currentMissile != null) {
			synchronized (currentMissile) {
				if (currentMissile.isAlive()) {
					currentMissile.notifyAll();
				}

			}
		}
	}

	public void removeMissile() {
		Enemy_Missile currentMissile = allMissiles.poll();
		if (currentMissile != null && !currentMissile.isAlive()) {
			currentMissile.setIsAlive(false);
		}
	}

	public void fireMissile() {
		Enemy_Missile m = missileQueue.poll();
		if (m != null) {

		}
	}

	public Queue<Enemy_Missile> getMissleQueue() {
		return allMissiles;
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
	
	public void emptyMissileQueue(){
		this.allMissiles.clear();
		this.missileQueue.clear();
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	public boolean iSAlive() {
		return iSAlive;
	}

	public void run() {
		while (iSAlive) {
			if (!missileQueue.isEmpty()) {
				notifyMissile();

			} else {
				synchronized (this) {
					try {
						wait();
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

	public void setIsAlive(boolean isAlive) {
		this.iSAlive = isAlive;

	}

	public void removeMissile(Enemy_Missile enemy_Missile) {
		if (enemy_Missile != null && !enemy_Missile.isAlive()) {
			enemy_Missile.setIsAlive(false);
		}
		missileQueue.remove(enemy_Missile);
		
	}

	public Enemy_Missile getCurrentMissile() {
		return currentMissile;
	}

}
