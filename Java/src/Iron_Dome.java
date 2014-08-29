import java.util.LinkedList;
import java.util.Queue;

import logger.Handler;

public class Iron_Dome extends Thread {
    private String id;
    private Queue<Interceptor> allInterceptor = new LinkedList<Interceptor>();
    private boolean isAlive = true;
    private String destructAfterLaunch;
    private String EnemyM;
    /**
     * Creates Iron Dome by given id
     * @param id --> String, the id of the iron dome 
     */
    public Iron_Dome(String id) throws Exception {
	this.id = id;
	War.theLogger.addHandler((new Handler(this.getClass().getName(), id,
		this)));
    }
    /**
     * The thread is running while the iron dome is alive
     */
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
    /**
     * Add missile to intercept 
     * @param enemy_Missile --> Enemy_Missile, the missile you want to intercept
     * @param destructAfterLaunch --> String, how much time after the launch the interception will occur
     */
    public void addMissileToIntercept(Enemy_Missile enemy_Missile,
	    String destructAfterLaunch) {
	allInterceptor.add(new Interceptor(this, enemy_Missile,
		destructAfterLaunch));
    }
    /**
     * Add missile to intercept (For reading from XML)
     * @param MissileId --> String, the missile you want to intercept
     * @param destructAfterLaunch --> String, how much time after the launch the interception will occur
     */
    public void addMissileToIntercept(String MissileId,
	    String destructAfterLaunch) {
	allInterceptor
		.add(new Interceptor(MissileId, destructAfterLaunch, this));
    }

    // Getters Setters
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

    public String getDomeId() {
	return id;
    }

    public Queue<Interceptor> getAllInterceptor() {
	return allInterceptor;
    }

}
