import java.util.logging.Level;

public class Enemy_Missile extends Thread {
	//enum for the statistics, showing current status
	public enum Mode {Ready, Launched, Hit};
	private Mode mode;
	private String id;
	private String destination;
	private Enemy_Launcher enemy_Launcher;
	private int launchTime;
	private int flyTime;
	private int damage;
	boolean isAlive;
	

	public Enemy_Missile(int damage, String destination, int flyTime, Enemy_Launcher enemy_Launcher) throws InterruptedException {
		this.id = "M" + (int) (Math.random() * 100);
		this.destination = destination;
		this.flyTime = flyTime;
		this.damage = damage;
		this.enemy_Launcher = enemy_Launcher;
		this.launchTime = 4;
		this.isAlive = true;
		this.mode=Mode.Ready;
	}

	public Enemy_Missile(String damage, String destination, String flytime, String id, String launchtime, Enemy_Launcher enemy_Launcher) throws InterruptedException {
		this.id = id;
		this.destination = destination;
		this.launchTime = Integer.parseInt(launchtime);
		this.flyTime = Integer.parseInt(flytime);
		this.damage = Integer.parseInt(damage);
		this.enemy_Launcher = enemy_Launcher;
		this.isAlive = true;
		this.mode=Mode.Ready;

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
			if (this.isAlive && enemy_Launcher.alive()) {
				War.theLogger.log(Level.INFO, " Missile " + getID() + " fired to " + getDestination() + " and will hit in " + getFlyTime() + "s", enemy_Launcher);
				this.mode=Mode.Launched;
			}
			Thread.sleep((long) flyTime * 1000);
			if (this.isAlive) {
				War.theLogger.log(Level.INFO, " Missile " + getID() + " hit " + getDestination() + " for " + getDamage() + " damage", enemy_Launcher);
				//Setting the missile as "dead"
				enemy_Launcher.removeMissile(this);
				this.mode=Mode.Hit;
			}

			if (status) {
				enemy_Launcher.setHidden(true);

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

	public Enemy_Launcher getFather() {
		return this.enemy_Launcher;

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
	

	public Mode getMode() {
		return mode;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	@Override
	public void run() {
		while (isAlive) {
			try {
				launch();
				if (enemy_Launcher.alive() && this.isAlive) {
					fly();
				}

				synchronized (this) {
					wait();
				}
			} catch (InterruptedException e) {
			}
		}
	}
}
