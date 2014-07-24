import java.util.Calendar;
import java.util.Vector;


public class missileDestructor extends Thread  {
	private String id;
	private String type;
	private Launcher allocatedMissile = new Launcher();
	public missileDestructor() {
		this.id = "D" + (int) (Math.random() * 1000);
	}
	public missileDestructor(String id) {
		this.id = id;
	}

	public void addAlocatingMissile(Missile peek) throws InterruptedException {
		synchronized (this) {
			allocatedMissile.getMissile();

		}


	}
	@Override
	public String toString() {
		return "missileDestructor id " + id ;
	}
}

