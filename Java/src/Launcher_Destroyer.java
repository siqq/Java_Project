import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.FileHandler;

public class Launcher_Destroyer extends Thread {

	private String type;
	private int id;
	private boolean isAlive = true;
	private Queue<Enemy_Launcher> waitingLaunchers = new LinkedList<Enemy_Launcher>();
	private String destructAfterLaunch;
	private String LaunchID;

	public Launcher_Destroyer(String type) {
		this.type = type;
		this.id = (int)( Math.random()*100);	
		FileHandler theHandler;
		try {
			theHandler = new FileHandler("LauncherDestroyer" + id + ".txt");
			theHandler.setFilter(new ObjectFilter(this));
			theHandler.setFormatter(new LogFormatter());
			War.theLogger.addHandler(theHandler);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void destroyLauncher(String id) throws InterruptedException {
		for (Enemy_Launcher enemy_Launcher : War.launchers) {
			if (enemy_Launcher.getID().equalsIgnoreCase(id)) {
				synchronized (enemy_Launcher) {	
					enemy_Launcher.wait();
				}
			}
		}
	}

	public void run() {	
		while (isAlive) {	
			try {
				checkIfPossibleToIntercept(destructAfterLaunch, LaunchID);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			synchronized (this) {
				try {
					//launcher destroyer is waiting all the program because after bombing launcher/ missing he is destroyed any way = isAive(false)
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void checkIfPossibleToIntercept(String destructTime, String id) throws InterruptedException {
		int destruct_After_Launch = Integer.parseInt(destructTime);
		for(Enemy_Launcher enemy_l : War.launchers) { 
			if(enemy_l.getID().equalsIgnoreCase(id) && enemy_l.iSAlive()){
				synchronized (this) {
					Thread.sleep((long) (destruct_After_Launch * 1000));

					if(enemy_l.isHidden()){
						System.out.println(Calendar.getInstance().getTime() +"\t " + this.type +" #"+this.id  + " Failed to intercept launcher #" + id );
					}
					else{
						System.out.println(Calendar.getInstance().getTime() +"\t " + this.type +" #"+this.id  + " sucsessfuly destroyed launcher #" + id );
						enemy_l.setIsAlive(false);
					}
				}
			}
		}	
	}

	public void checkIfPossibleToIntercept() throws InterruptedException {
		synchronized(this){
			for(Enemy_Launcher enemy_l : War.launchers) { 
				if(enemy_l.iSAlive()){
					synchronized (enemy_l) {
						if(enemy_l.isHidden()){
							System.out.println(Calendar.getInstance().getTime() + " Failed to intercept launcher " + enemy_l.getID());
							break;
						}
						else{
							Thread.sleep(5000);
							System.out.println(Calendar.getInstance().getTime()
									+ " Launcher #" + enemy_l.getID() + " is destroyed #" );
							enemy_l.setIsAlive(false);
							break;
						}
					}
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
}
