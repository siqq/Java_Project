import java.util.Calendar;
import java.util.logging.Level;

public class Interceptor extends Thread {
	public enum Status {
		Intercept, missed
	};

	private int destructAfterLaunch;
	private String missleID;
	private Iron_Dome father;
	private Enemy_Missile missile = null;
	private Status status;

	public Interceptor(String id, String destructAfterLaunch, Iron_Dome iron_Dome) {
		this.father = iron_Dome;
		this.destructAfterLaunch = Integer.parseInt(destructAfterLaunch);
		this.missleID = id;
		this.start();
	}

	public Interceptor(Iron_Dome iron_Dome, Enemy_Missile enemy_Missile) {
		this.father = iron_Dome;
		this.destructAfterLaunch = 5;
		this.missile = enemy_Missile;

		this.start();
	}

	public Interceptor(Iron_Dome iron_Dome, Enemy_Missile enemy_Missile, String destructAfterLaunch) {
		this.father = iron_Dome;
		this.destructAfterLaunch = Integer.parseInt(destructAfterLaunch);
		this.missile = enemy_Missile;
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

	public Status getStatus() {
		return status;
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
				if (missile.isAlive  && missile.getMode() == Enemy_Missile.Mode.Launched) {
					War.theLogger.log(Level.INFO, " Iron dome #" + father.getDomeId() + " intercepted missile #" + missile.getID(), father);
					War.theLogger.log(Level.INFO, " Iron dome #" + father.getDomeId() + " intercepted missile #" + missile.getID(), missile.getFather());
					missile.setIsAlive(false);
					missile.getFather().removeMissile(missile);
					// Setting status to intercept
					this.status = Status.Intercept;
					missile.interrupt(); // stop thread sleep if missile is intercepted

				} else if (missile.isAlive && missile.getMode() == Enemy_Missile.Mode.Launched) {
					War.theLogger.log(Level.INFO, " Iron dome #" + father.getDomeId() + " failed to intercept missile #" + missile.getID(), father);
					War.theLogger.log(Level.INFO, " Iron dome #" + father.getDomeId() + " failed to intercept #" + missile.getID(), missile.getFather());
					// Setting enum status to missed
					this.status = Status.missed;
				}
			}

		}
	}
}
