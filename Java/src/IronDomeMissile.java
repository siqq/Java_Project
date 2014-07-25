import java.util.Calendar;


public class IronDomeMissile extends Thread {
	private Iron_Dome ironDome;
	private String id;
	private int destructAfterLaunch;
	//private Missle_Launcher allocatedMissile = new Missle_Launcher();
	public IronDomeMissile() {
		this.id = "M"+(int)Math.random()*100;
	}
	public IronDomeMissile(String destructAfterLaunch, String id) {
		this.id = id;
		this.destructAfterLaunch = Integer.parseInt(destructAfterLaunch);
	}
	@Override
	public String toString() {
		return "destructMissile id " + id + ", destructAfterLaunch= "
				+ destructAfterLaunch + ", allocatedMissile= ";
	//			+ allocatedMissile ;
	}
//	public Airplane( theAirport) {
//		this.theAirport = theAirport;
//	}

	public void launch() throws InterruptedException {
		synchronized (this) {
			ironDome.addWaitinMissile(this);

			System.out.println(Calendar.getInstance().getTimeInMillis()
					+ " Missile #" + getID() + " is launching");

			wait();
		}

		synchronized (ironDome) {
			System.out.println(Calendar.getInstance().getTimeInMillis()
					+ " --> missile #" + getID() + " started launching");
			Thread.sleep((long) (Math.random() * 3000));
			System.out.println(Calendar.getInstance().getTimeInMillis()
					+ " <-- missile #" + getID() + " finished launching");
		
			ironDome.notifyAll();
		}
	}

	public void fly() throws InterruptedException {
	//	long flyingTime = (long) (Math.random() * 5000);
		System.out.println(Calendar.getInstance().getTimeInMillis()
				+ " missile #" + getID() + " starts flying for " + destructAfterLaunch
				+ "ms");
		Thread.sleep(destructAfterLaunch);
		System.out.println(Calendar.getInstance().getTimeInMillis()
				+ " missile #" + getID() + " finished flying");
	}

	public void run() {
		try {
			launch();
			fly();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getID() {
		return id;
	}
}
