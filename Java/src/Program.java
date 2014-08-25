import java.io.IOException;
import java.util.Queue;
import java.util.Scanner;

public class Program {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
	War war = new War();
	menu(war);
    }

    private static void menu(War war) {

	try {
	    int menuOption = -1;
	    do {
		menuOption = showMenu(); // show Menu
		switch (menuOption) {

		case 1:
		    add_Luncher_Destructor(war);
		    break;
		case 2:
		    add_Iron_Dome(war);
		    break;
		case 3:
		    add_Enemy_Launcher(war);
		    break;
		case 4:
		    launch_missile(war);
		    break;
		case 5:
		    destroy_Launcher(war);
		    break;
		case 6:
		    intercept_missile(war);
		    break;
		case 7:
		    show_Statistics(war);
		    break;
		case 8:
		    end_war(war);
		    System.exit(0);
		default:
		    System.out.println("Sorry, please enter valid Option");

		}

	    } while (menuOption != 0);

	    // Exiting message when user decides to quit Program
	    System.out.println("Thanks for using this Program...");

	} catch (Exception ex) {
	    // Error Message
	    System.out.println("Sorry problem occured within Program");
	    // flushing scanner
	    scanner.next();

	} finally {
	    // Ensuring that scanner will always be closed and cleaning up
	    // resources
	    scanner.close();
	}

    }

    private static void end_war(War war) {
	show_Statistics(war);
	System.exit(0);
    }

    private static void show_Statistics(War war) {
	int hit = 0;
	int damage = 0;
	int launched = 0;
	int intercept = 0;
	int destroyed_launcher = 0;
	for (Enemy_Missile missile : war.getAllMissiles()) {
	    if (missile.getMode() == Enemy_Missile.Mode.Hit) {
		hit += 1;
		launched += 1;
		damage += missile.getDamage();
	    } else if (missile.getMode() == Enemy_Missile.Mode.Launched) {
		launched += 1;
	    }
	}
	for (Iron_Dome iron_dome : war.getIronDomes()) {
	    for (Interceptor interceptor : iron_dome.getAllInterceptor()) {
		if (interceptor.getStatus() == Interceptor.Status.Intercept) {
		    intercept += 1;
		}
	    }
	}
	for (Enemy_Launcher launcher : war.getLaunchers()) {
	    // launcher is down
	    if (launcher.iSAlive() == false) {
		destroyed_launcher += 1;
	    }
	}
	System.out.println("Total Missiles Fired: " + launched);
	System.out.println("Total Missiles Intercepted:" + intercept);
	System.out.println("Total Missiles that hit target: " + hit);
	System.out.println("Total Launchers destroyed: " + destroyed_launcher);
	System.out.println("Total Missiles damage: " + damage);
    }

    private static void intercept_missile(War war) {
	if (war.getIronDomePeek() == null) {
	    System.out
		    .println("\t No Iron Dome Available, Enter Iron Dome First");
	} else {
	    System.out.println("Choose Iron Dome to shot from: ");
	    for (Iron_Dome dome : war.getIronDomes()) {
		System.out.print("\t" + dome.getDomeId());
	    }
	    System.out.println("\n Your selection: ");
	    String domeId = scanner.next();

	    System.out.println("Select missile to intercept:");
	    for (Enemy_Missile missile : war.getAllMissiles()) {
		if (missile.isAlive) {
		    System.out.print("\t" + missile.getID());
		}
	    }
	    System.out.println("\n Your selection: ");
	    String missileId = scanner.next();

	    for (Iron_Dome dome : war.getIronDomes()) {
		if (dome.getDomeId().equalsIgnoreCase(domeId)) {
		    for (Enemy_Missile missile : war.getAllMissiles()) {
			if (missile.getID().equalsIgnoreCase(missileId)) {
			    int waitOnlaunch = (int) (Math.random() * 10);
			    dome.addMissileToIntercept(missile, ""
				    + waitOnlaunch);
			}
		    }

		}
	    }
	}
    }

    private static void destroy_Launcher(War war) throws InterruptedException {
	Launcher_Destroyer destroyer;
	Enemy_Launcher user_launcher = null;
	String destroyerId, launcherId;
	if (war.getWarLauncherDestroyer().isEmpty()) {
	    System.out.println("No Destroyer available to hit the launcher");
	} else {

	    System.out.println("Choose destroyer");
	    for (Launcher_Destroyer launcher_destroyer : war
		    .getWarLauncherDestroyer()) {
		System.out.print("\t" + launcher_destroyer.getLauncherName());
	    }
	    System.out.println("\n Your selection: ");
	    destroyerId = scanner.next();
	    destroyer = war.findDestroyerById(destroyerId);
	    System.out.println("Choose launcher to destroy");
	    
	    for (Enemy_Launcher launcher : war.getLaunchers()) {
		System.out.print("\t" + launcher.getLauncherId());
	    }
	    System.out.println("\n Your selection: ");
	    launcherId = scanner.next();
	    user_launcher = war.findLauncherById(launcherId);

	}

    }

    private static void add_Enemy_Launcher(War war) throws SecurityException,
	    IOException {
	Enemy_Launcher launcher;
	Queue<Enemy_Launcher> launchers = war.getLaunchers();
	while (true) {
	    launchers = war.getLaunchers();
	    launcher = null;
	    System.out.println("Enter new Launcher ID: ");
	    String id = scanner.next();
	    for (Enemy_Launcher l : launchers) {
		if (l.getLauncherId().equalsIgnoreCase(id)) {
		    launcher = l;
		    break;
		}
	    }
	    if (launcher == null) {
		war.Create_enemy_launcher();
		break;
	    } else {
		System.out.println("This ID already exists! ");
	    }
	}

    }

    private static void add_Iron_Dome(War war) throws Exception {
	Iron_Dome dome;
	Queue<Iron_Dome> IronDomes = war.getIronDomes();
	while (true) {
	    dome = null;
	    System.out.println("Enter Iron Dome ID: ");
	    String id = scanner.next();
	    for (Iron_Dome idome : IronDomes) {
		if (idome.getDomeId().equalsIgnoreCase(id)) {
		    dome = idome;
		    break;
		}
	    }
	    if (dome == null) {
		war.Create_Iron_Dome(id);
		break;
	    } else {
		System.out.println("This ID already exists! ");
	    }
	}
    }

    private static void add_Luncher_Destructor(War war) {
	System.out.println("Select launcher detroyer type: Plane or Ship: ");
	String type = scanner.next();
	while (true) { // user
	    if (type.equalsIgnoreCase("ship") || type.equalsIgnoreCase("plane")) {
		war.Create_Launcher_Destroyer(type);
		break;

	    } else {
		System.out
			.println("Type should be Plane/Ship only \nType again: ");
		type = scanner.next();
	    }

	}

    }

    private static void launch_missile(War war) throws InterruptedException {
	Enemy_Launcher user_launcher = null;
	System.out.println("Destination of the missile: ");
	String destination = scanner.next();
	System.out.println("Damage of the missile: ");
	int damage = scanner.nextInt();
	System.out.println("Flytime of the missile: ");
	int flytime = scanner.nextInt();
	while (true) {
	    System.out.println("Choose Launcher to shoot from: ");
	    for (Enemy_Launcher launcher : war.getLaunchers()) {
		System.out.print("\t" + launcher.getLauncherId());
	    }
	    System.out.println("\n Your selection: ");
	    String launcherId = scanner.next();
	    user_launcher = war.findLauncherById(launcherId);
	    if (user_launcher != null) {
		war.LaunchMissile(destination, damage, flytime, user_launcher);
		break;
	    } else {
		System.out.println("Launcher does not exist");
	    }
	}

    }

    public static int showMenu() {
	int option = 0;
	// Printing menu to screen
	System.out.println("\n\t\t Missile Management system \n");
	System.out.println("1.\t Add new launcher destructor");
	System.out.println("2.\t Add new Iron dome");
	System.out.println("3.\t Add new Missile launcher");
	System.out.println("4.\t Fire a missile");
	System.out.println("5.\t Destroy a Missile launcher");
	System.out.println("6.\t Intercept a Missile");
	System.out.println("7.\t Show statistics");
	System.out.println("8.\t End the war \n");
	// Getting user option from above menu
	System.out.print("Enter: ");
	option = scanner.nextInt();
	return option;
    }

}
