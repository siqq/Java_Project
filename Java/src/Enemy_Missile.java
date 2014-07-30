import java.util.Calendar;
import java.util.concurrent.CountDownLatch;

public class Enemy_Missile extends Thread {
	private String id;
	private String destination;
	private Enemy_Launcher enemy_Launcher;
	private int launchTime = (int) ((Math.random() * 5000) + Calendar
			.getInstance().getTimeInMillis());
	private int flyTime;
	private int damage;
	// CountDownLatch latch = null;
	boolean isAlive;

	public Enemy_Missile(int damage, String destination, int flyTime,
			Enemy_Launcher enemy_Launcher) throws InterruptedException {
		this.id = "M" + (int) Math.random() * 100;
		this.destination = destination;
		this.flyTime = flyTime;
		this.damage = damage;
		this.enemy_Launcher = enemy_Launcher;
	}

	public Enemy_Missile(String damage, String destination, String flytime,
			String id, String launchtime, Enemy_Launcher enemy_Launcher)
			throws InterruptedException {
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
			Thread.sleep((long) launchTime * 1000);
		}
	}

	public void fly() throws InterruptedException {

		synchronized (enemy_Launcher) {
			boolean status = enemy_Launcher.isHidden();
			if (status) {
				enemy_Launcher.setHidden(false);
			}
			if(this.isAlive){
			System.out.println(Calendar.getInstance().getTime()
					+ "\t Missile #" + getID() + " starts flying for "
					+ flyTime + "sec from Launcher #"+enemy_Launcher.getID());
			}
			Thread.sleep((long) flyTime * 1000);
			if (this.isAlive) {
				System.out.println(Calendar.getInstance().getTime()
						+ "\t Missile #" + getID() + " hit " + getDestination()
						+ " and the damage is " + getDamage());
			}
			if (status) {
				enemy_Launcher.setHidden(true);

			}

		}
		this.setIsAlive(false);

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
		while (isAlive) {
			try {
				launch();
				// latch.countDown();
				if (enemy_Launcher.iSAlive() && this.isAlive) {
					fly();
				}
				synchronized (this) {
					wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
