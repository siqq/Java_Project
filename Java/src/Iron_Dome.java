import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.logging.FileHandler;
import java.util.logging.Level;

public class Iron_Dome extends Thread {
	private String id;
	private boolean isAlive = true;
	private String destructAfterLaunch;
	private String EnemyM;

	public Iron_Dome() throws Exception {
		
		this.id = "D" + (int) (Math.random() * 1000);
		War.theLogger.addHandler((new Handler(this.getClass().getName(), id, this)));
		
	}

	public Iron_Dome(String id) throws Exception {
		this.id = id;
		War.theLogger.addHandler((new Handler(this.getClass().getName(), id, this)));
	}

	public void run() {
		while (isAlive) {

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

	public void addMissileToIntercept(String destructAfterLaunch, String id) {
		new Interceptor(id, destructAfterLaunch, this);
	}

}
