import java.util.Calendar;
import java.util.logging.Level;

public class Interceptor extends Thread {
	private int destructAfterLaunch;
	private String missleID;
	private Iron_Dome father;

	public Interceptor(String id, String destructAfterLaunch, Iron_Dome iron_Dome) {
		this.father = iron_Dome;
		this.destructAfterLaunch = Integer.parseInt(destructAfterLaunch);
		this.missleID = id;
		this.start();
	}
	public Interceptor(Iron_Dome iron_Dome) {
		this.father = iron_Dome;
		this.destructAfterLaunch = 5;
		this.missleID = War.enemyMissile.peek().getID();
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
		for (Enemy_Missile missile : War.enemyMissile) {
			if (missile.getID().equalsIgnoreCase(missleID)) {
				synchronized (missile) {
					if (destructAfterLaunch < missile.getFlyTime() + missile.getLaunchTime() && missile.getLaunchTime() <= destructAfterLaunch) {
						if (missile.isAlive && (Math.random() < 0.5)) {
							War.theLogger.log(Level.INFO, " Iron dome #" + father.getId() + " intercepted missile #" + missile.getID(), father);
							War.theLogger.log(Level.INFO, " Iron dome #" + father.getId() + " intercepted missile #" + missile.getID(), missile.getFather());
							missile.setIsAlive(false);
							War.enemyMissile.remove(missile);
						} else if (missile.isAlive) {
							War.theLogger.log(Level.INFO, " Iron dome #" + father.getId() + " failed to intercept missile #" + missile.getID(), father);
							War.theLogger.log(Level.INFO, " Iron dome #" + father.getId() + " failed to intercept #" + missile.getID(), missile.getFather());
						}

					}
				}

			}
		}
	}

}
