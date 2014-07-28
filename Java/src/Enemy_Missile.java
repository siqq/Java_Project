import java.util.Calendar;

public class Enemy_Missile extends Thread {
	private String id;
	private String destination;
	private Enemy_Launcher enemy_Launcher;
	private int launchTime = (int) ((Math.random() * 5000)+Calendar.getInstance().getTimeInMillis());
	private int flyTime;
	private int damage;
	boolean isAlive;

	public Enemy_Missile(int damage, String destination, int flyTime , Enemy_Launcher enemy_Launcher) throws InterruptedException {
		this.id = "M"+(int)Math.random()*100;
		this.destination = destination;
		this.flyTime = flyTime;
		this.damage = damage;
		this.enemy_Launcher = enemy_Launcher;	
	}

	public Enemy_Missile(String damage, String destination, String flytime,String id, String launchtime , Enemy_Launcher enemy_Launcher) throws InterruptedException {
		this.id = id;
		this.destination = destination;
		this.launchTime = Integer.parseInt(launchtime);
		this.flyTime = Integer.parseInt(flytime);
		this.damage = Integer.parseInt(damage);
		this.enemy_Launcher = enemy_Launcher;
		this.isAlive = true;

	}
	public void launch() throws InterruptedException {
		synchronized (this) {
			enemy_Launcher.addWaitingMissile(this);
			Thread.sleep((long) launchTime * 1000);
			System.out.println(Calendar.getInstance().getTime()
					+ " Missile # " + getID() + " started launching");
		}
	}
	public void fly() throws InterruptedException {
		synchronized (enemy_Launcher) {
			System.out.println(Calendar.getInstance().getTime()+ " Missile #" + getID() + " starts flying for " + flyTime + "sec");
			Thread.sleep((long) flyTime * 1000);
			if(this.isAlive == true){
				System.out.println(Calendar.getInstance().getTime()+ " Missile #" + getID() + " hit " + getDestination() + " and the damage is " + getDamage());
			}

		}


	}

	public String getID() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDestination() {
		return destination;
	}
	public void setIsAlive(boolean bool) {
		this.isAlive = bool;
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
		try{

			launch();
			fly();
			synchronized (this) {
				wait();

			}

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
