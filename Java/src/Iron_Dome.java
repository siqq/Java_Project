import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

public class Iron_Dome extends Thread {
	private String id;
	private Queue<Enemy_Missile> waitingMissile = new LinkedList<Enemy_Missile>();
	private boolean isAlive = true;
	public Iron_Dome() {
		this.id = "D" + (int) (Math.random() * 1000);
	}

	public Iron_Dome(String id) {
		this.id = id;
	}

	public void checkIfPossibleToIntercept(String id, String destructAfterLaunch) throws InterruptedException {
		int destruct_After_Launch = Integer.parseInt(destructAfterLaunch);
		for(Enemy_Missile enemy_missile : War.enemyMissile) { 
			if(enemy_missile.getID() == id){
				synchronized (enemy_missile) {
					System.out.println("Targer aquired");
					if(destruct_After_Launch > enemy_missile.getFlyTime()+enemy_missile.getLaunchTime()){
						System.out.println("Failed to intercept, missile " + id + " is going to destroy in " + destruct_After_Launch + " sec");
						Thread.sleep((long) (destruct_After_Launch * 1000));
					}
					else{
						System.out.println(Calendar.getInstance().getTime()
								+ " ironDome Interceptor # " + id + " is launching to missile " + id);
						Thread.sleep((long) (destruct_After_Launch * 1000));
						System.out.println("Interception succesful ");

					}
				}
			}

		}


		//		synchronized (ironDome) {
		//			System.out.println(Calendar.getInstance().getTime()
		//					+ " --> missile #" + getID() + " started launching and flying to target");
		//			Thread.sleep((long) (destructAfterLaunch * 1000));
		//			System.out.println(Calendar.getInstance().getTime()
		//					+ " <-- missile #" + getID() + " inerception sucess");
		//
		//			ironDome.notifyAll();
		//		}
	}



	public void emptyLauncher() {
		isAlive = false;
		synchronized (/*dummyWaiter*/this) {
			/*dummyWaiter.*/notifyAll();
		}
	}
	public void destroyMissile(String id) {
		for(Enemy_Missile enemy_missile : War.enemyMissile) { 
			if(enemy_missile.getID() == id){
				System.out.println("Missile id# " + id + " destroy ");
				War.enemyMissile.remove(enemy_missile);

			}
		}
	}

	public void run() {
		while (isAlive) {
			if (!waitingMissile.isEmpty()) {
			} else {
				synchronized (/*dummyWaiter*/this) {
					try {
						System.out.println("Iron dome has no missiles and waiting for loading missile");
						System.out.println(this.getState());
						/*dummyWaiter.*/wait();
						System.out.println("IronDome recieved a missile");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}


}
