import java.util.LinkedList;
import java.util.Queue;

public class Iron_Dome extends Thread {
	private String id;
	private Queue<Interceptor> allInterceptor = new LinkedList<Interceptor>();
	private boolean isAlive = true;
	private String destructAfterLaunch;
	private String EnemyM;

	public Iron_Dome(String id) throws Exception {
		this.id = id;
		War.theLogger.addHandler((new Handler(this.getClass().getName(), id,
				this)));
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

	public void setDestructAfterLaunch(String destrucTAfterLaunch) {
		this.destructAfterLaunch = destrucTAfterLaunch;
	}

	public String getEnemyM() {
		return EnemyM;
	}

	public void setEnemyM(String enemyM) {
		EnemyM = enemyM;
	}

	public void addMissileToIntercept(Enemy_Missile enemy_Missile,
			String destructAfterLaunch) {
		allInterceptor.add(new Interceptor(this, enemy_Missile,
				destructAfterLaunch));
	}

	public void addMissileToIntercept(String MissileId,
			String destructAfterLaunch) {
		allInterceptor
				.add(new Interceptor(MissileId, destructAfterLaunch, this));
	}
	
	public String getDomeId() {
		return id;
	}

	public Queue<Interceptor> getAllInterceptor() {
		return allInterceptor;
	}

}
