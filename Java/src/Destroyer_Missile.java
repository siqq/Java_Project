import java.util.Calendar;
import java.util.logging.Level;


public class Destroyer_Missile extends Thread {

	private int destructTime;
	private String id;
	private Launcher_Destroyer father;
	
	public Destroyer_Missile(String time, String id, Launcher_Destroyer launcher_Destroyer) {
		this.destructTime = Integer.parseInt(time);
		this.id = id;		
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
			sleep(destructTime * 1000);
			destroyLauncher();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void destroyLauncher() {
		for (Enemy_Launcher enemy_l : War.launchers) {
			if (enemy_l.getID().equalsIgnoreCase(this.id) && enemy_l.iSAlive()) {
				synchronized (this) {
					if (enemy_l.isHidden()) {
			//			System.out.println(Calendar.getInstance().getTime() + "\t Failed to destroy launcher " + enemy_l.getID());
						War.theLogger.log(Level.INFO,  father.getId()+ " Failed to destroy launcher " +  enemy_l.getID(), father);

						break;
					} else {
						War.theLogger.log(Level.INFO,father.getLauncherName() +"#" + " destroyed launcher " +  enemy_l.getID(), father);
						War.theLogger.log(Level.INFO,father.getLauncherName() +"#" + " destroyed launcher " +  enemy_l.getID(), enemy_l);
				//		 System.out.println(Calendar.getInstance().getTime() + "\t Launcher #" + enemy_l.getID() + " is destroyed ");
						enemy_l.setIsAlive(false);
						War.launchers.remove(enemy_l);
						break;
					}
				}
			}
			
				
			}
			
		}
		
	}
	

