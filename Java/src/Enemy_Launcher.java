import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Enemy_Launcher extends Thread {
    private String id;
    private boolean isHidden;
    private Queue<Enemy_Missile> missileQueue = new LinkedList<Enemy_Missile>();
    private Queue<Enemy_Missile> allMissiles = new LinkedList<Enemy_Missile>(); // statistics
    private boolean iSAlive = true;
    private Enemy_Missile currentMissile;
    /*
     * Empty constructor for new Enemy launcher
     */
    public Enemy_Launcher() throws SecurityException, IOException {
	this.id = "L" + (int) (Math.random() * 1000);
	this.isHidden = (Math.random() < War.LAUNCHER_HIDE_RATE);
	War.theLogger.addHandler((new Handler(this.getClass().getName(), id,
		this)));
    }
    /**
     * Constructor for Enemy launcher
     * @param id --> String, the id for the launcher
     * @param isHidden --> String, if the launcher is hidden or not, User write true/false
     */
    public Enemy_Launcher(String id, String isHidden) {
	this.id = id;
	this.isHidden = Boolean.parseBoolean(isHidden);
	try {
	    War.theLogger.addHandler((new Handler(this.getClass().getName(),
		    id, this)));
	} catch (Exception e) {

	}
    }
    /**
     * Add a new missile to current launcher and start Thread
     * @param newMissile --> Enemy_Missile, the missile you want to add
     */
    public void addMissile(Enemy_Missile newMissile)
	    throws InterruptedException {
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


    public void setIsAlive(boolean isAlive) {
	this.iSAlive = isAlive;
	destroyAllMissiles();
	emptyMissileQueue();

    }

    private void destroyAllMissiles() {
	for (Enemy_Missile enemy_Missile : missileQueue) {
	    if (!(enemy_Missile.getMode() == Enemy_Missile.Mode.Launched))
		enemy_Missile.setIsAlive(false);
	}
	for (Enemy_Missile enemy_Missile : allMissiles) {
	    if (!(enemy_Missile.getMode() == Enemy_Missile.Mode.Launched))
		enemy_Missile.setIsAlive(false);
	}

    }

    public void removeMissile(Enemy_Missile enemy_Missile) {
	if (enemy_Missile != null && !enemy_Missile.isAlive()) {
	    enemy_Missile.setIsAlive(false);
	}
	missileQueue.remove(enemy_Missile);
    }
    @Override
    public String toString() {
	return "Launcher id= " + id + ", isHidden= " + isHidden + " ";
    }
    public Enemy_Missile getCurrentMissile() {
	return currentMissile;
    }
    public String getLauncherId() {
	return id;
    }
    public Queue<Enemy_Missile> getAllMissiles() {
	return allMissiles;
    }
    public Queue<Enemy_Missile> getMissleQueue() {
	return allMissiles;
    }

    public void setId(String id) {
	this.id = id;
    }

    public boolean isHidden() {
	return isHidden;
    }

    public void emptyMissileQueue() {
	this.missileQueue.clear();
    }

    public void setHidden(boolean isHidden) {
	this.isHidden = isHidden;
    }
    /** 
     * @return The live status of the missile
     */
    public boolean iSAlive() {
	return iSAlive;
    }

}
