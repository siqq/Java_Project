import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

public class Enemy_Launcher extends Thread{
	private String id;
	private boolean isHidden;
	private Queue<Enemy_Missile> missileQueue = new LinkedList<Enemy_Missile>();
	private Queue<Enemy_Missile> waitingMissile = new LinkedList<Enemy_Missile>();
	private static Queue<Enemy_Missile> allMissiles = new LinkedList<Enemy_Missile>();
	private boolean isAlive = true;

	public Enemy_Launcher() {
		this.id = "L" + (int) (Math.random() * 1000);
		this.isHidden = (Math.random() < 0.5);
	}

	public Enemy_Launcher(String id, String isHidden) {
		this.id = id;
		this.isHidden = Boolean.parseBoolean(isHidden);
		
	}
	public Enemy_Launcher(String id) {
		this.id = id;
		this.missileQueue = new LinkedList<Enemy_Missile>();
	}
	public void addMissile(Enemy_Missile newMissile) throws InterruptedException {
		allMissiles.add(newMissile);

	}
	public synchronized void addWaitingMissile(Enemy_Missile newMissile) {
		waitingMissile.add(newMissile);
		System.out.println(Calendar.getInstance().getTime() +"\t Launcher #"+this.getID()+" added Missile #"+newMissile.getID()+" total: "+waitingMissile.size()+ " Missiles waiting");
	}

	public void notifyMissile() {
		Enemy_Missile currentMissile = waitingMissile.poll();
		if (currentMissile != null) {
			synchronized (currentMissile) {
				if(currentMissile.isAlive()){
					currentMissile.notifyAll();
				}
				
			}
		}
		try {
			wait();


		} catch (InterruptedException e) {
			e.printStackTrace();
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

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}



	public void run() {
		while (isAlive) {
			if (!waitingMissile.isEmpty()) {
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


}



