import java.util.Calendar;



public class Enemy_Missile extends Thread {
	private String id;
	private String destination;
	private Enemy_Launcher enemy_Launcher;
	private int launchTime = (int) ((Math.random() * 5000)+Calendar.getInstance().getTimeInMillis());
	private int flyTime;
	private int damage;
	
	public Enemy_Missile(int damage, String destination, int flyTime) throws InterruptedException {
		this.id = "M"+(int)Math.random()*100;
		this.destination = destination;
		this.flyTime = flyTime;
		this.damage = damage;
		sleep(launchTime);
		this.start();		
	}
	public Enemy_Missile(String damage, String destination, String launchtime,String id, String flytime) throws InterruptedException {
		this.id = id;
		this.destination = destination;
		this.launchTime = Integer.parseInt(launchtime);
		this.flyTime = Integer.parseInt(flytime);
		this.damage = Integer.parseInt(damage);
		sleep(launchTime);
		this.start();		
	}
	public void launch() throws InterruptedException {
		synchronized (this) {
			enemy_Launcher.addWaitingMissile(this);

			System.out.println(Calendar.getInstance().getTimeInMillis()
					+ " Missile #" + getId() + " is waiting to launch ");

			wait();
		}

		synchronized (enemy_Launcher) {
			System.out.println(Calendar.getInstance().getTimeInMillis()
					+ " Missile #" + getId() + " started launching");
			Thread.sleep((long) (Math.random() * 3000));
			System.out.println(Calendar.getInstance().getTimeInMillis()
					+ " <-- Airplane #" + getId() + " finished launching");
		
			enemy_Launcher.notifyAll();
		}
	}
	public void fly() throws InterruptedException {
		//long flyingTime = (long) (Math.random() * 5000);
		System.out.println(Calendar.getInstance().getTimeInMillis()
				+ " Missile #" + getId() + " starts flying for " + flyTime
				+ "ms");
		Thread.sleep(flyTime);
		System.out.println(Calendar.getInstance().getTimeInMillis()
				+ " Missile #" + getId() + " finished flying");
	}

//	public void land() throws InterruptedException {
//		synchronized (this) {
//			System.out.println(Calendar.getInstance().getTimeInMillis()
//					+ " Airplane #" + getId() + " is waiting to land");
//
//			theAirport.addWaitingAirplane(this);
//
//			wait();
//		}
//
//		synchronized (theAirport) {
//			System.out.println(Calendar.getInstance().getTimeInMillis()
//					+ " --> Airplane #" + getId() + " started landing");
//			Thread.sleep((long) (Math.random() * 3000));
//			System.out.println(Calendar.getInstance().getTimeInMillis()
//					+ " <-- Airplane #" + getId() + " finished landing");
//
//			theAirport.notifyAll();
//		}
//	}

	public String getID() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public int getLaunchTime() {
		return launchTime;
	}

	public void setLaunchTime(int launchTime) {
		this.launchTime = launchTime;
	}

	public int getFlyTime() {
		return flyTime;
	}

	public void setFlyTime(int flyTime) {
		this.flyTime = flyTime;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	@Override
	public void run() {
		try {
			launch();
			fly();
			//land();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public String toString() {
		return "Missile id=" + id + ", destination=" + destination
				+ ", launchTime=" + launchTime + ", flyTime=" + flyTime
				+ ", damage=" + damage ;
	}
	
	
}
