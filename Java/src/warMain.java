import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

public class warMain {
	public static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) throws IOException {	

		new readXml();
		try {
			int menuOption = -1;
			do {
				menuOption = showMenu();
				// Switching on the value given from user
				switch (menuOption) {

				case 1:
					new Missile_Launcher_Destructor();
					break;
				case 2:
					new missileDestructor();
					break;
				case 3:
					new Launcher();
					break;
				case 4:
					createNewMissile();
					break;
				case 5:
				//	printLoop();
					break;
				case 6:
				//	printLoop();
					break;
				case 7:
					System.out.println("Quitting Program...");
					break;
			//	default:
				//	System.out.println("Sorry, please enter valid Option");

				}// End of switch statement

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


	private static void createNewMissile() throws InterruptedException {
		System.out.println("Please provide the destination of the missile");   
		String destination = scanner.next();
		System.out.println("Please provide the damage of the missile");  
		int damage = scanner.nextInt();
		System.out.println("Please provide the flytime of the missile");
		int flytime = scanner.nextInt();

		new Missile(damage, destination, flytime);

	}

	//	Launcher l = new Launcher();
	//System.out.println(l.getId() + "  " + l.isHidden() );
	public static int showMenu() {
		int option = 0;
		// Printing menu to screen
		System.out.println("What Would you like to do, press 0 to EXIT");       
		System.out.println("1.Create new Missile Launcher Destructor");
		System.out.println("2.Create new MissileDestructor");
		System.out.println("3.Missile");
		System.out.println("4.Intercept Launcher");
		System.out.println("5.Show Stastistics");
		System.out.println("6.Intercept Missile");
		System.out.println("7.End of War + Show Statistics");
		// Getting user option from above menu
		System.out.println("Enter Option from above...");
		option = scanner.nextInt();
		return option;
	}

}
