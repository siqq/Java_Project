import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.logging.Logger;

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
					end_War(war);
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

	private static void end_War(War War) {

	}

	private static void show_Statistics(War War) {
		System.out.println("Total Missiles Fired:");
		System.out.println("Total Missiles Intercepted:");
		System.out.println("Total Missiles that hit target:");
		System.out.println("Total Launchers destroyed:");
		System.out.println("Total Missiles Fired:");
	}

	private static void intercept_missile(War War) {
		if (War.getIronDomePeek() == null) {
			System.out.println("\t ##############################");
			System.out.println("\t No Iron Dome Available");
			System.out.println("\t Please enter iron Dome first");
			System.out.println("\t ##############################");
			
		}
		else{
			War.InterceptMissileByUser();
		}
	}

	private static void destroy_Launcher(War War) throws InterruptedException {
		if (War.getWarLauncherDestroyer().isEmpty()) {
			System.out.println("No Destroyer up to hit the launcher");
		} else {
			War.DestroyLauncher();

			}
		}
	

	private static void add_Enemy_Launcher(War War) throws SecurityException, IOException {
		War.Create_enemy_launcher();
	}

	private static void add_Iron_Dome(War War) throws Exception {
		War.Create_Iron_Dome(null);
	}

	private static void add_Luncher_Destructor(War War) {
		System.out.println(" please select launcher detroyer type: Plane or Ship");
		String type = scanner.next();
		War.Create_Launcher_Destroyer(type);
		
	}

	private static void launch_missile(War War) throws InterruptedException {
		System.out.println("Destination of the missile: ");
		String destination = scanner.next();
		System.out.println("Damage of the missile: ");
		int damage = scanner.nextInt();
		System.out.println("Flytime of the missile: ");
		int flytime = scanner.nextInt();		
		War.LaunchMissile(destination,damage,flytime);

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
		System.out.println("8.\t End the War \n");
		// Getting user option from above menu
		System.out.println("Choose your next move...");
		option = scanner.nextInt();
		return option;
	}

}
