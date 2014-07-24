
public class Missile_Launcher_Destructor extends Thread{
	
	private String type;
	private Launcher allocatedMissile = new Launcher();
	public Missile_Launcher_Destructor(String type) {
		this.type = type;
	}
	public Missile_Launcher_Destructor( ) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Missile_Launcher_Destructor type= " + type;
	}
	

}
