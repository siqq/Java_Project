import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

public class Launcher_Destroyer extends Thread {

	private String type;
	private boolean isAlive = true;
	private Queue<Enemy_Launcher> waitingLaunchers = new LinkedList<Enemy_Launcher>();

	public Launcher_Destroyer(String type) {
		this.type = type;
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
			if (!waitingLaunchers.isEmpty()) {
			//	notifyLauncher();
			} else {
				synchronized (this) {
					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}


	public String toString() {
		return "Missile_Launcher_Destructor type= " + type;
	}

	public void checkIfPossibleToIntercept(String destructTime, String id) throws InterruptedException {
		int destruct_After_Launch = Integer.parseInt(destructTime);
		for(Enemy_Launcher enemy_l : War.launchers) { 
			if(enemy_l.getID().equalsIgnoreCase(id) && enemy_l.iSAlive()){
		//		synchronized (this) {
				//	enemy_l.notifyAll()
					if(enemy_l.isHidden()){
						System.out.println(Calendar.getInstance().getTime() + " Failed to intercept launcher " + id );
						//		Thread.sleep((long) (destruct_After_Launch * 1000));
					}
					else{
						Thread.sleep((long) (destruct_After_Launch * 1000));
						System.out.println(Calendar.getInstance().getTime()
								+ " Launcher #" + id + " is destroyed #" );
						enemy_l.setIsAlive(false);
						//	Thread.sleep((long) (destruct_After_Launch * 1000));

					}
		//		}
			}
		}	
	}

	public void checkIfPossibleToIntercept() throws InterruptedException {
		for(Enemy_Launcher enemy_l : War.launchers) { 
			if(enemy_l.iSAlive()){
				synchronized (this) {
					if(enemy_l.isHidden()){
						System.out.println(Calendar.getInstance().getTime() + " Failed to intercept launcher " + enemy_l.getID());
						//		Thread.sleep((long) (destruct_After_Launch * 1000));
						break;
					}
					else{
						Thread.sleep(5000);
						System.out.println(Calendar.getInstance().getTime()
								+ " Launcher #" + enemy_l.getID() + " is destroyed #" );
						enemy_l.setIsAlive(false);
						break;
						//	Thread.sleep((long) (destruct_After_Launch * 1000));

					}
				}
			}
		}	
		
	}
}
