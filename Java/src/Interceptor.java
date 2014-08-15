import java.util.Calendar;
import java.util.logging.Level;

public class Interceptor extends Thread {
	private int destructAfterLaunch;
	private String missleID;
	private Iron_Dome father;
	private Enemy_Missile missile = null;

	public Interceptor(String id, String destructAfterLaunch, Iron_Dome iron_Dome) {
		this.father = iron_Dome;
		this.destructAfterLaunch = Integer.parseInt(destructAfterLaunch);
		this.missleID = id;
		this.start();
	}

	public Interceptor(Iron_Dome iron_Dome, Enemy_Missile enemy_Missile) {
		this.father = iron_Dome;
		this.destructAfterLaunch = 5;
		this.missile = enemy_Missile; // need a way to find a way to get missile
										// (that is in the air) id
		this.start();
	}

	public Interceptor(Iron_Dome iron_Dome, Enemy_Missile enemy_Missile, String destructAfterLaunch) {
		this.father = iron_Dome;
		this.destructAfterLaunch = Integer.parseInt(destructAfterLaunch);
		this.missile = enemy_Missile; // need a way to find a way to get missile
										// (that is in the air) id
		this.start();
	}

	public Integer getDestructAfterLaunch() {
		return destructAfterLaunch;
	}

	public void setDestructAfterLaunch(int destructAfterLaunch) {
		this.destructAfterLaunch = destructAfterLaunch;
	}

	public String getMissleID() {
		return missleID;
	}

	public void setMissleID(String missleID) {
		this.missleID = missleID;
	}

	public void run() {
		try {
			sleep(destructAfterLaunch * 1000);
			intercept();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void intercept() {
		synchronized (missile) {
			if (destructAfterLaunch < missile.getFlyTime() + missile.getLaunchTime() && missile.getLaunchTime() <= destructAfterLaunch) {
				if (missile.isAlive && (Math.random() < 0.5)) {
					War.theLogger.log(Level.INFO, " Iron dome #" + father.getId() + " intercepted missile #" + missile.getID(), father);
					War.theLogger.log(Level.INFO, " Iron dome #" + father.getId() + " intercepted missile #" + missile.getID(), missile.getFather());
					missile.setIsAlive(false);
					missile.getFather().removeMissile(missile); // remove from
																// the launcher
				} else if (missile.isAlive) {
					War.theLogger.log(Level.INFO, " Iron dome #" + father.getId() + " failed to intercept missile #" + missile.getID(), father);
					War.theLogger.log(Level.INFO, " Iron dome #" + father.getId() + " failed to intercept #" + missile.getID(), missile.getFather());
				}
			}

			/*
			 * private void intercept() { for (Enemy_Missile missile :
			 * War.enemyMissile) { // need to run through the lunchers and look
			 * at the current missile if
			 * (missile.getID().equalsIgnoreCase(missleID)) { synchronized
			 * (missile) { if (destructAfterLaunch < missile.getFlyTime() +
			 * missile.getLaunchTime() && missile.getLaunchTime() <=
			 * destructAfterLaunch) { if (missile.isAlive && (Math.random() <
			 * 0.5)) { War.theLogger.log(Level.INFO, " Iron dome #" +
			 * father.getId() + " intercepted missile #" + missile.getID(),
			 * father); War.theLogger.log(Level.INFO, " Iron dome #" +
			 * father.getId() + " intercepted missile #" + missile.getID(),
			 * missile.getFather()); missile.setIsAlive(false);
			 * missile.getFather().removeMissile(missile); // remove from the
			 * launcher } else if (missile.isAlive) {
			 * War.theLogger.log(Level.INFO, " Iron dome #" + father.getId() +
			 * " failed to intercept missile #" + missile.getID(), father);
			 * War.theLogger.log(Level.INFO, " Iron dome #" + father.getId() +
			 * " failed to intercept #" + missile.getID(), missile.getFather());
			 * }
			 * 
			 * } }
			 * 
			 * } }
			 */
		}
	}
}
