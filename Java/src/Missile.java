import java.util.Calendar;


public class Missile extends Thread {
	private String id;
	private String destination;
	private int launchTime = (int) ((Math.random() * 5000)+Calendar.getInstance().getTimeInMillis());
	private int flyTime;
	private int damage;
	
	public Missile(String destination) throws InterruptedException {
		this.id = "M"+(int)Math.random()*100;
		this.destination = destination;
		this.flyTime = flyTime;
		this.damage = damage;
		this.start();
		Thread.sleep(launchTime);
	}

	public Missile(String damage, String destination, String flytime,
			String id, String launchtime) {
		this.id = id;
		this.destination = destination;
		this.launchTime = Integer.parseInt(launchtime);
		this.flyTime = Integer.parseInt(flytime);
		this.damage = Integer.parseInt(damage);
		this.start();
		
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
	public String toString() {
		return "Missile id=" + id + ", destination=" + destination
				+ ", launchTime=" + launchTime + ", flyTime=" + flyTime
				+ ", damage=" + damage ;
	}
	
	
}
