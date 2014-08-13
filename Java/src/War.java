import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.logging.Logger;

public class War {
	private int totalFiredMissiles, totalInterceptedMissiles, totalHitMissiles, totalLaunchersDestroyed, totalDamage;
	private Queue<Enemy_Launcher> launchers = new LinkedList<Enemy_Launcher>();
	private Queue<Iron_Dome> ironDomes = new LinkedList<Iron_Dome>();
	private Queue<Launcher_Destroyer> LauncherDestroyers = new LinkedList<Launcher_Destroyer>();
	private Queue<Enemy_Missile> enemyMissile = new LinkedList<Enemy_Missile>();
	private Enemy_Launcher enemy_launcher;
	private Iron_Dome iron_dome;
	private Launcher_Destroyer launcherDestroyer;
	static Logger theLogger = Logger.getLogger("myLogger");

	War() {
		try {
			theLogger.addHandler((new Handler("FullWarLog")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 new readXml(this,launchers,ironDomes,LauncherDestroyers,enemyMissile);

	}

	public Iron_Dome getIronDomePeek() {
		return ironDomes.peek();
	}

	public Queue<Launcher_Destroyer> getWarLauncherDestroyer() {
		return LauncherDestroyers;
	}

	public Enemy_Launcher Create_enemy_launcher() throws SecurityException, IOException {
		enemy_launcher = new Enemy_Launcher();
		return enemy_launcher;
	}

	public Launcher_Destroyer Create_Launcher_Destroyer(String type) {
		launcherDestroyer = new Launcher_Destroyer(type);
		return launcherDestroyer;
	}

	public void addLauncher(Enemy_Launcher enemy_launcher) {
		launchers.add(enemy_launcher);
		enemy_launcher.start();

	}

	public void addIronDome(Iron_Dome ironDome) {
		ironDomes.add(ironDome);
		ironDome.start();

	}

	public Iron_Dome Create_Iron_Dome() throws Exception {
		iron_dome = new Iron_Dome();
		return iron_dome;
	}

	public void addDestroyer(Launcher_Destroyer ld) {
		LauncherDestroyers.add(ld);
		ld.start();
	}

	public void LaunchMissile(String destination, int damage, int flytime) throws InterruptedException {
		if (launchers.size() == 0) {
			System.out.println("There are no active launchers");
		} else {
			Enemy_Launcher l = launchers.peek();
			Enemy_Missile em = new Enemy_Missile(damage, destination, flytime, l);
			enemyMissile.add(em);
			l.addMissile(em);
		}
	}

	public Queue<Enemy_Launcher> getLaunchers() {
		return launchers;
	}

	public String findMissileToIntercept() {
		Queue<Enemy_Launcher> l = getLaunchers();
		
		return null;
	}

	public void Intercept() {
		for(Iron_Dome ironDome : ironDomes){
			if(ironDome != null){
				ironDome.InerceptMissile(launchers);
			}
		}
		
	}

	public void DestroyLauncher() {
		Launcher_Destroyer d = null;
		for(Launcher_Destroyer destroyer : LauncherDestroyers){
			if(destroyer != null){
				d = destroyer;
				break;
			}
			for(Enemy_Launcher launcher : launchers){
				if(launcher.iSAlive() && !launcher.isHidden()){
					d.destroyLauncher(launcher);
				}
			}
				
		}
		
	}



}
