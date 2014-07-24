import java.util.Calendar;
import java.util.Vector;


public class MissileDestructor extends Thread  {
	private String id;
	private Launcher allocatedMissile = new Launcher();
	public MissileDestructor() {
		//this.id = id;
	}
	public void addAlocatingMissile(Missile peek) throws InterruptedException {
		synchronized (this) {
			allocatedMissile.getMissile();

		}


	}
}

