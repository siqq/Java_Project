import java.io.IOException;
import java.security.AllPermission;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.logging.Logger;

public class War {
	private Queue<Enemy_Launcher> launchers = new LinkedList<Enemy_Launcher>();
	private Queue<Iron_Dome> ironDomes = new LinkedList<Iron_Dome>();
	private Queue<Launcher_Destroyer> LauncherDestroyers = new LinkedList<Launcher_Destroyer>();
	private Queue<Enemy_Missile> allMissiles = new LinkedList<Enemy_Missile>();
	private Enemy_Launcher enemy_launcher;
	private Iron_Dome iron_dome;
	private Launcher_Destroyer launcherDestroyer;
	static Logger theLogger = Logger.getLogger("myLogger");

	War() {
		try {
			theLogger.addHandler((new Handler("FullWarLog")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		new readXml(this, launchers, ironDomes, LauncherDestroyers, allMissiles);

	}

	public Iron_Dome getIronDomePeek() {
		return ironDomes.peek();
	}

	public Queue<Launcher_Destroyer> getWarLauncherDestroyer() {
		return LauncherDestroyers;
	}

	public void Create_enemy_launcher() throws SecurityException, IOException {
		enemy_launcher = new Enemy_Launcher();
		launchers.add(enemy_launcher);
		enemy_launcher.start();
	}

	public void Create_enemy_launcher(Enemy_Launcher enemy_launcher) {
		launchers.add(enemy_launcher);
		enemy_launcher.start();
	}

	public Launcher_Destroyer Create_Launcher_Destroyer(String type) {
		launcherDestroyer = new Launcher_Destroyer(type);
		LauncherDestroyers.add(launcherDestroyer);
		launcherDestroyer.start();
		return launcherDestroyer;
	}

	public Iron_Dome Create_Iron_Dome(String id) throws Exception {
		if (id == null) {
			iron_dome = new Iron_Dome();

		} else {
			iron_dome = new Iron_Dome(id);

		}
		ironDomes.add(iron_dome);
		iron_dome.start();
		return iron_dome;

	}

	public void LaunchMissile(String destination, int damage, int flytime) throws InterruptedException {
		if (launchers.size() == 0) {
			System.out.println("There are no active launchers");
		} else {
			Enemy_Launcher l = launchers.peek(); // fire missile from random
													// launcher
			Enemy_Missile em = new Enemy_Missile(damage, destination, flytime, l);
			allMissiles.add(em);
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

	public void DestroyLauncher() {
		Launcher_Destroyer d = null;
		for (Launcher_Destroyer destroyer : LauncherDestroyers) {
			if (destroyer != null) {
				d = destroyer;
				break;
			}
			for (Enemy_Launcher launcher : launchers) {
				if (launcher.iSAlive() && !launcher.isHidden()) {
					d.destroyLauncher(launcher);
				}
			}

		}

	}

	public void DestroyLauncher(String destructTime, String id, Launcher_Destroyer launcherDestroyer) {

		for (Enemy_Launcher launcher : launchers) {
			if (launcher.getID().equalsIgnoreCase(id)) {
				launcherDestroyer.addLauncherToDestroy(launcher, destructTime);
				break;
			}
		}

	}

	public void InterceptMissile(String destructAfterLaunch, String id, Iron_Dome ironDome) {
		for (Enemy_Missile missile : allMissiles) {
			if (missile.getID().equalsIgnoreCase(id)) {
				ironDome.addMissileToIntercept(missile, destructAfterLaunch);
				break;
			}
		}
	}

	public void InterceptMissileByUser() {
		Iron_Dome d;
		for (Iron_Dome dome : ironDomes) {
			if (dome != null) {
				d = dome;
				break;
			}

		}
	}

	
	public Queue<Enemy_Missile> getAllMissiles() {
		return allMissiles;
	}
	
	public Queue<Iron_Dome> getIronDomes() {
		return ironDomes;
	}

	public void addMissileToLauncher(Enemy_Launcher enemy_launcher, Enemy_Missile missile) throws InterruptedException {
		enemy_launcher.addMissile(missile);
		allMissiles.add(missile);
	}

}
