import java.util.Calendar;
import java.util.logging.Level;

public class Destroyer_Missile extends Thread {

	private int destructTime;
	private String id;
	private Launcher_Destroyer father;
	Enemy_Launcher launcherToDestroy;

	public Destroyer_Missile(String time, String id, Launcher_Destroyer launcher_Destroyer, Enemy_Launcher launcher) {
		this.launcherToDestroy = launcher;
		this.destructTime = Integer.parseInt(time);
		this.id = id;
		this.father = launcher_Destroyer;
		start();
	}

	public Destroyer_Missile(Enemy_Launcher launcher, String destructTime, Launcher_Destroyer launcher_Destroyer) {
		this.launcherToDestroy = launcher;
		this.destructTime = Integer.parseInt(destructTime);
		this.launcherToDestroy = launcher;
		this.father = launcher_Destroyer;
		start();
	}

	public int getDestructTime() {
		return destructTime;
	}

	public void setDestructTime(int destructTime) {
		this.destructTime = destructTime;
	}

	public String getMissileId() {
		return id;
	}

	public void setMissileId(String id) {
		this.id = id;
	}

	public Launcher_Destroyer getFather() {
		return father;
	}

	public void setFather(Launcher_Destroyer father) {
		this.father = father;
	}

	public void run() {
		try {
			sleep(destructTime * War.THREAD_SLEEP_TIME);
			destroyLauncher(launcherToDestroy);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void destroyLauncher(Enemy_Launcher launcherToDestroy) {
		synchronized (this) {
			if (launcherToDestroy.isHidden()) {
				War.theLogger.log(Level.INFO, " " +father.getLauncherType() +  "#" + father.getLauncherId() + "Failed to destroy launcher " + launcherToDestroy.getLauncherId(), father);
			} else {
				War.theLogger.log(Level.INFO," " + father.getLauncherType() +  "#" + father.getLauncherId() + " destroyed launcher " + launcherToDestroy.getLauncherId(), father);
				War.theLogger.log(Level.INFO," " + father.getLauncherType() + "#" + father.getLauncherId() + " destroyed launcher " + launcherToDestroy.getLauncherId(), launcherToDestroy);
				launcherToDestroy.setIsAlive(false);
				

			}

		}

	}

}
