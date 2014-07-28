import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class War {

	public static Scanner scanner = new Scanner(System.in);
	public static Queue<Enemy_Launcher> launchers = new LinkedList<Enemy_Launcher>();
	public static Queue<Iron_Dome> ironDomes = new LinkedList<Iron_Dome>();
	public static Queue<Launcher_Destroyer> LauncherDestroyer = new LinkedList<Launcher_Destroyer>();
	public static Queue<Enemy_Missile> enemyMissile = new LinkedList<Enemy_Missile>();



	public static void main(String[] args) throws IOException {

//		for(Enemy_Launcher enemy_launcher : launchers) { 
//			Thread launcherThread = new Thread(enemy_launcher);
//			launcherThread.start(); 
//		}
//		for(Iron_Dome ironDomes : ironDomes) { 
//			Thread launcherThread = new Thread(ironDomes);
//			launcherThread.start(); 
//		}

		new readXml();
		

		try {
			int menuOption = -1;
			do {
				menuOption = showMenu(); // show Menu
				switch (menuOption) {

				case 1:
					add_Luncher_Destructor();
					break;
				case 2:
					add_Iron_Dome();
					break;
				case 3:
					add_Enemy_Launcher();
					break;
				case 4:
					enemy_missile_launch();
					break;
				case 5:
					destroy_Launcher();
					break;
				case 6:
					destroy_Enemy_missile();
					break;
				case 7:
					show_Statistics();
					break;
				case 8:
					end_War();
					break;
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

	private static void end_War() {

	}

	private static void show_Statistics() {

	}

	private static void destroy_Enemy_missile() {

	}

	private static void destroy_Launcher() {
		if(launchers.peek().isHidden() != true ){
	//		LauncherDestroyer.peek().addMissile(new Launcher_Destroyer_missile(launchers.poll().getID()));
		}
		else{
			System.out.println("the launcher you just try to destroy is hidden");
		}

	}

	private static void add_Enemy_Launcher() {
		launchers.add(new Enemy_Launcher());
		Thread launcherThread = new Thread(launchers.peek());
		launcherThread.start(); 

	}

	private static void add_Iron_Dome() {
		ironDomes.add(new Iron_Dome());
		Thread launcherThread = new Thread(ironDomes.peek());
		launcherThread.start(); 
	}

	private static void add_Luncher_Destructor() 
	{
		System.out.println(" please select launcher detroyer type: Plane or Ship");
		String type = scanner.next();
		LauncherDestroyer.add(new Launcher_Destroyer(type));
		//for(Launcher_Destroyer LauncherDestroyer : LauncherDestroyer) { 
		Thread launcherThread = new Thread(LauncherDestroyer.peek());
		launcherThread.start(); 
		//	}
	}

	private static void enemy_missile_launch() throws InterruptedException {
		System.out.println("Destination of the missile: ");
		String destination = scanner.next();
		System.out.println("Damage of the missile: ");
		int damage = scanner.nextInt();
		System.out.println("Flytime of the missile: ");
		int flytime = scanner.nextInt();
//		launchers.peek().addMissile(new Enemy_Missile(damage, destination, flytime));
//		ironDomes.peek().a
	}
	public static int showMenu() {
		int option = 0;
		// Printing menu to screen
//		System.out.println("\n\t\t Missile Management system \n");
//		System.out.println("1.\t Add new launcher destructor");
//		System.out.println("2.\t Add new Iron dome");
//		System.out.println("3.\t Add new Missile launcher");
//		System.out.println("4.\t Fire a missile");
//		System.out.println("5.\t Destroy a Missile launcher");
//		System.out.println("6.\t Intercept a Missile");
//		System.out.println("7.\t Show statistics");
//		System.out.println("8.\t End the War \n");
		// Getting user option from above menu
//		System.out.println("Choose your next move...");
		option = scanner.nextInt();
		return option;
	}

}
