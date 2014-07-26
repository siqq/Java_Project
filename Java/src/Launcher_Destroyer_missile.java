import java.util.Calendar;


public class Launcher_Destroyer_missile extends Thread{
	private Launcher_Destroyer launcher_destroyer;
	private String id;
	private int destructTime;
	//private Missle_Launcher allocatedMissile = new Missle_Launcher();
	public Launcher_Destroyer_missile(Launcher_Destroyer theLauncherDestroyer) {
		this.id = "M"+(int)Math.random()*100;
		this.launcher_destroyer = theLauncherDestroyer;
	}
	public Launcher_Destroyer_missile(String destructTime, String id , Launcher_Destroyer launcherDestroyer) {
		this.id = id;
		this.destructTime = Integer.parseInt(destructTime);
		this.launcher_destroyer = launcherDestroyer;
	}
	public Launcher_Destroyer_missile(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "destructLauncher id " + id + ", destructAfterLaunch="
				+ destructTime + ", allocatedMissile=";
		//				+ allocatedMissile ;
	}

	public void launch() throws InterruptedException {
		synchronized (this) {
			launcher_destroyer.addWaitingMissile(this);

			System.out.println(Calendar.getInstance().getTimeInMillis()
					+ " Missile #" + getId() + " is waiting to launch");

			wait();
		}

		synchronized (launcher_destroyer) {
			System.out.println(Calendar.getInstance().getTimeInMillis()
					+ " --> Missile #" + getId() + " started launching");
			Thread.sleep((long) (Math.random() * 3000));
			System.out.println(Calendar.getInstance().getTimeInMillis()
					+ " <-- Missile #" + getId() + " finished taking off");

			launcher_destroyer.notifyAll();
		}
	}

	public void fly() throws InterruptedException {
		long flyingTime = (long) (Math.random() * 5000);
		System.out.println(Calendar.getInstance().getTimeInMillis()
				+ " Airplane #" + getId() + " starts flying for " + flyingTime
				+ "ms");
		Thread.sleep(flyingTime);
		System.out.println(Calendar.getInstance().getTimeInMillis()
				+ " Airplane #" + getId() + " finished flying");



		synchronized (launcher_destroyer) {
			System.out.println(Calendar.getInstance().getTimeInMillis()
					+ " --> Missile #" + getId() + " started landing");
			Thread.sleep((long) (Math.random() * 3000));
			System.out.println(Calendar.getInstance().getTimeInMillis()
					+ " <-- Missile #" + getId() + " finished landing");

			launcher_destroyer.notifyAll();
		}
	}


	@Override
	public void run() {
		try {
			launch();
			fly();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public long getTheId() {
		return getId();
	}
}
