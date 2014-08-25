import java.io.IOException;

public class Launcher_Destroyer extends Thread {

    private String type;
    private String name;
    private boolean isAlive = true;
    private String destructAfterLaunch;

    public Launcher_Destroyer(String type) {
	this.type = type;
	this.name = (type + "#" + (int) (Math.random() * 100));

	try {
	    War.theLogger.addHandler((new Handler(this.getClass().getName(),
		    name, this)));
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

    public void addLauncherToDestroy(Enemy_Launcher launcher,
	    String destructTime) {
	new Destroyer_Missile(launcher, destructTime, this);

    }
    // Andrey check this out - can you change the 0 to something else???? 
    public void destroyLauncher(Enemy_Launcher launcher) {
	new Destroyer_Missile(launcher.getLauncherId(), "0", this, launcher);
    }

    // Getters And setters
    public String getLauncherName() {
	return name;
    }

    public String getDestructAfterLaunch() {
	return destructAfterLaunch;
    }

    public void setDestructAfterLaunch(String destructAfterLaunch) {
	this.destructAfterLaunch = destructAfterLaunch;
    }

}
