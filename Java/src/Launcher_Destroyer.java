import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.FileHandler;
import java.util.logging.Level;

public class Launcher_Destroyer extends Thread {

	private String type;
	private String name;
	private boolean isAlive = true;
	private Queue<Enemy_Launcher> waitingToDestroy = new LinkedList<Enemy_Launcher>();
	private String destructAfterLaunch;
	private String LaunchID;

	public Launcher_Destroyer(String type) {
		this.type = type;
		this.name = (type + "#" + (int) (Math.random() * 100));

		try {
			War.theLogger.addHandler((new Handler(this.getClass().getName(), name, this)));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (isAlive) {
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getDestructAfterLaunch() {
		return destructAfterLaunch;
	}

	public void setDestructAfterLaunch(String destructAfterLaunch) {
		this.destructAfterLaunch = destructAfterLaunch;
	}

	public String getLaunchID() {
		return LaunchID;
	}

	public void setLaunchID(String launchID) {
		LaunchID = launchID;
	}

	public void addLauncherToDestroy(Enemy_Launcher launcher, String destructTime) {
		new Destroyer_Missile(launcher, destructTime, this);

	}

	public String getLauncherName() {
		return name;
	}

	public void destroyLauncher(Enemy_Launcher launcher) {
		new Destroyer_Missile(launcher.getID(), "0", this,launcher);
	}

}
