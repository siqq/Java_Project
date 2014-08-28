import java.io.IOException;

import logger.Handler;

public class Launcher_Destroyer extends Thread {
    
    public static int runningId = 1;
    int id;
    private String type;
    private boolean isAlive = true;
    private String destructAfterLaunch;

    public Launcher_Destroyer(String type) {
	this.type = type;
	this.id = runningId++;
//	this.name = (type + "#" + (int) (Math.random() * 100));

	try {
	    War.theLogger.addHandler((new Handler(type,
		    id, this)));
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
    public String getLauncherType() {
	return type;
    }
    public int getLauncherId() {
	return id;
    }

    public String getDestructAfterLaunch() {
	return destructAfterLaunch;
    }

    public void setDestructAfterLaunch(String destructAfterLaunch) {
	this.destructAfterLaunch = destructAfterLaunch;
    }
    public boolean alive(){
	return isAlive;
    }

}
