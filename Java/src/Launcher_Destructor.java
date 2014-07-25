
public class Launcher_Destructor extends Thread{
	
	private String type;
	private Missle_Launcher allocatedMissile = new Missle_Launcher();
	public Launcher_Destructor(String type) {
		this.type = type;
	}
	
	@Override
	public void run(){
		
	}
	@Override
	public String toString() {
		return "Missile_Launcher_Destructor type= " + type;
	}
	

}
