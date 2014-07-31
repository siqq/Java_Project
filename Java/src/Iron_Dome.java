import java.io.IOException;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;

public class Iron_Dome extends Thread {
	private String id;
	private boolean isAlive = true;
	private String destructAfterLaunch;
	private String EnemyM;

	public Iron_Dome() {
		this.id = "D" + (int) (Math.random() * 1000);
	}

	public Iron_Dome(String id) throws Exception {
		this.id = id;
		War.theLogger.addHandler((new Handler(this.getClass().getName(), id, this)));
	}

	public void checkIfPossibleToIntercept(String destructAfterLaunch, String id) throws InterruptedException {
		int destruct_After_Launch = Integer.parseInt(destructAfterLaunch);
		for (Enemy_Missile enemy_missile : War.enemyMissile) {
			if (enemy_missile.getID().equalsIgnoreCase(id)) {
				synchronized (enemy_missile) {
					if (!(destruct_After_Launch >= enemy_missile.getFlyTime() + enemy_missile.getLaunchTime())) {
						if (enemy_missile.isAlive)
							War.theLogger.log(Level.INFO, "Iron dome #" + this.id + " attempting to intercept missile #" + enemy_missile.getID() + " in " + destruct_After_Launch + " sec ", this);

						System.out.println(Calendar.getInstance().getTime() + "\t Iron dome #" + this.id + " is hitting enemy missile #" + enemy_missile.getID() + " in " + destruct_After_Launch + " sec ");
						Thread.sleep((long) (destruct_After_Launch * 1000));
						if (enemy_missile.isAlive) {
							War.theLogger.log(Level.INFO, "Iron dome #" + this.id + " successfully intercepted missile #" + enemy_missile.getID(), this);
							War.theLogger.log(Level.INFO, "Iron dome #" + this.id + " successfully intercepted missile #" + enemy_missile.getID(), enemy_missile.getFather());
							System.out.println(Calendar.getInstance().getTime() + "\t Iron dome #" + this.id + " successfully intercepted missile #" + id);
							destroyMissile(id);
						}
					}
				}
			}
		}
	}

	public void destroyMissile(String id) {
		for (Enemy_Missile enemy_missile : War.enemyMissile) {
			if (enemy_missile.getID().equalsIgnoreCase(id)) {
				enemy_missile.setIsAlive(false);
			}
		}
	}

	public void run() {
		while (isAlive) {
			try {
				checkIfPossibleToIntercept(destructAfterLaunch, EnemyM);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			synchronized (this) {
				try {
					// cause the missile to wait till there are other action,
					// but doesn't need notify because he will end his life
					// anyway
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void setISalive(boolean bool) {
		this.isAlive = bool;
	}

	public String getDestructAfterLaunch() {
		return destructAfterLaunch;
	}

	public void setDestructAfterLaunch(String destrucTAfterLaunch) {
		this.destructAfterLaunch = destrucTAfterLaunch;
	}

	public String getEnemyM() {
		return EnemyM;
	}

	public void setEnemyM(String enemyM) {
		EnemyM = enemyM;
	}

}
