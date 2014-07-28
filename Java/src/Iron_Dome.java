import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;



public class Iron_Dome extends Thread {
	private String id;
	private Queue<Enemy_Missile> waitingMissile = new LinkedList<Enemy_Missile>();
	private Enemy_Missile enemy_missile;
	private boolean isAlive = true;
	public Iron_Dome() {
		this.id = "D" + (int) (Math.random() * 1000);
	}

	public Iron_Dome(String id) throws InterruptedException {
		this.id = id;
	}


	public void checkIfPossibleToIntercept(String destructAfterLaunch, String id) throws InterruptedException {
		int destruct_After_Launch = Integer.parseInt(destructAfterLaunch);
		for(Enemy_Missile enemy_missile : War.enemyMissile) { 
			if(enemy_missile.getID().equalsIgnoreCase(id)){
				synchronized (enemy_missile) {
					enemy_missile.notify();
					if(destruct_After_Launch >= enemy_missile.getFlyTime()+enemy_missile.getLaunchTime()){
						System.out.println("Failed to intercept, missile " + id + " and is going to boom ");
				//		Thread.sleep((long) (destruct_After_Launch * 1000));
					}
					else{
						System.out.println(Calendar.getInstance().getTime()
								+ " ironDome Interceptor # " + id + " is hitting enemy missile #" + id + " in " + destruct_After_Launch + " sec ");
						Thread.sleep((long) (destruct_After_Launch * 1000));
						System.out.println("Interception succesful ");
						destroyMissile(id);

					}
				}
			}

		}
	}


	public void emptyLauncher() {
		isAlive = false;
		synchronized (/*dummyWaiter*/this) {
			/*dummyWaiter.*/notifyAll();
		}
	}
	public void destroyMissile(String id) {
		for(Enemy_Missile enemy_missile : War.enemyMissile) { 
			if(enemy_missile.getID().equalsIgnoreCase(id)){
				System.out.println("Missile id# " + id + " destroy ");
			//	War.enemyMissile.remove(enemy_missile);
		//		enemy_missile.stop();
				enemy_missile.setIsAlive(false);

			}
		}
	}

	public void run() {
		while (isAlive) {
			if (!War.enemyMissile.isEmpty()) {
		//		notifyInterceptor();
			} else {
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

//	private void notifyInterceptor() {
//		Enemy_Missile currentMissile = War.enemyMissile.poll();
//		if (currentMissile != null) {
//			synchronized (currentMissile) {
//				if(currentMissile.isAlive()){
//					currentMissile.notifyAll();
//				}
//				
//			}
//		}
//		try {
//			wait();
//
//
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}



