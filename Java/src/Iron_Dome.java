import java.util.Calendar;

public class Iron_Dome extends Thread {
	private String id;
	private boolean isAlive = true;

	public Iron_Dome() {
		this.id = "D" + (int) (Math.random() * 1000);
	}

	public Iron_Dome(String id) throws InterruptedException {
		this.id = id;
	}

	public void checkIfPossibleToIntercept(String destructAfterLaunch, String id)
			throws InterruptedException {
		int destruct_After_Launch = Integer.parseInt(destructAfterLaunch);
		for (Enemy_Missile enemy_missile : War.enemyMissile) {
			if (enemy_missile.getID().equalsIgnoreCase(id)) {
				synchronized (enemy_missile) {

					if (!(destruct_After_Launch >= enemy_missile.getFlyTime() + enemy_missile.getLaunchTime()))
//					{
//						System.out.println(Calendar.getInstance().getTime()
//								+ "\t Iron dome #" + this.id
//								+ " Failed to intercept, missile #" + id);
//						// Thread.sleep((long) (destruct_After_Launch * 1000));
//					} else
					{
						System.out.println(Calendar.getInstance().getTime()
								+ "\t Iron dome #" + this.id
								+ " is hitting enemy missile #" + id + " in "
								+ destruct_After_Launch + " sec ");
						Thread.sleep((long) (destruct_After_Launch * 1000));
						System.out.println(Calendar.getInstance().getTime()
								+ "\t Iron dome #" + this.id
								+ " successfully intercepted missile #" + id);
						destroyMissile(id);

					}
				}
			}

		}
	}

	public void destroyMissile(String id) {
		for (Enemy_Missile enemy_missile : War.enemyMissile) {
			if (enemy_missile.getID().equalsIgnoreCase(id)) {
				enemy_missile.setIsAlive(false);
			}
		}
	}

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

}
