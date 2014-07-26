import java.util.Calendar;
import java.util.Queue;


public class IronDomeMissile extends Thread {
	private Iron_Dome ironDome;
	private Enemy_Launcher enemy_launcher;
	private String id;
	private int destructAfterLaunch;
	
	public IronDomeMissile() {
		this.id = "M"+(int)Math.random()*100;
	}
	public IronDomeMissile(String destructAfterLaunch, String id , Iron_Dome ironDome) {
		this.id = id;
		this.destructAfterLaunch = Integer.parseInt(destructAfterLaunch);
		this.ironDome = ironDome;
	}
	public void takeOff() throws InterruptedException {
		synchronized (this) {
			ironDome.addWaitinMissile(this);

			System.out.println(Calendar.getInstance().getTime()
					+ " Interceptor is going to destroy missile id# " + getID());

			wait();
		}

		synchronized (ironDome) {
			System.out.println(Calendar.getInstance().getTimeInMillis()
					+ " --> iron dome Interceptor is on his way to missile#: " + getID());
		
			ironDome.notifyAll();
		}
	}
	public void checkIfPossibleToIntercept() throws InterruptedException {
		enemy_launcher = new Enemy_Launcher();
		synchronized (this) {
			for(Enemy_Missile enemy_missile : enemy_launcher.getMissleQueue()) { 
				if(enemy_launcher.getID() == this.getID()){
					System.out.println("Targer aquired");
					if(this.destructAfterLaunch > enemy_missile.getFlyTime()+enemy_missile.getLaunchTime()){
						System.out.println("Failed to intercept, missile " + getID() + " is going to destroy in " + destructAfterLaunch + " sec");
						Thread.sleep((long) (destructAfterLaunch * 1000));
					}
					else{
						System.out.println(Calendar.getInstance().getTime()
								+ " ironDome Interceptor # " + getID() + " is launching to missile " + getID());
						Thread.sleep((long) (destructAfterLaunch * 1000));
						System.out.println("Interception succesful ");
						
					}
				}
			}


			wait();
		}

		synchronized (ironDome) {
			System.out.println(Calendar.getInstance().getTime()
					+ " --> missile #" + getID() + " started launching and flying to target");
			Thread.sleep((long) (destructAfterLaunch * 1000));
			System.out.println(Calendar.getInstance().getTime()
					+ " <-- missile #" + getID() + " inerception sucess");

			ironDome.notifyAll();
		}
	}
	@Override
	public String toString() {
		return "destructMissile id " + id + ", destructAfterLaunch= "
				+ destructAfterLaunch ;
	}

//	public void launchAndFly() throws InterruptedException {
//		synchronized (this) {
//			ironDome.addWaitinMissile(this);
//
//			System.out.println(Calendar.getInstance().getTime()
//					+ " ironDome Interceptor # is launching over missile " + getID());
//
//			wait();
//		}
//
//		synchronized (ironDome) {
//			System.out.println(Calendar.getInstance().getTime()
//					+ " --> missile #" + getID() + " started launching and flying to target");
//			Thread.sleep((long) (destructAfterLaunch * 1000));
//			System.out.println(Calendar.getInstance().getTime()
//					+ " <-- missile #" + getID() + " inerception sucess");
//
//			ironDome.notifyAll();
//		}
//	}

	//	public void fly() throws InterruptedException {
	//	//	long flyingTime = (long) (Math.random() * 5000);
	//		System.out.println(Calendar.getInstance().getTime()
	//				+ " missile #" + getID() + " starts flying for " + destructAfterLaunch
	//				+ "ms");
	//		Thread.sleep(destructAfterLaunch);
	//		System.out.println(Calendar.getInstance().getTime()
	//				+ " missile #" + getID() + " finished flying");
	//	}

	public void run() {
		try {
			takeOff();
			checkIfPossibleToIntercept();
			//	fly();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getID() {
		return id;
	}
}
