
public class destructLauncher {
	private String id;
	private int destructTime;
	private Missle_Launcher allocatedMissile = new Missle_Launcher();
	public destructLauncher() {
		this.id = "M"+(int)Math.random()*100;
	}
	public destructLauncher(String destructTime, String id) {
		this.id = id;
		this.destructTime = Integer.parseInt(destructTime);
	}
	@Override
	public String toString() {
		return "destructLauncher id " + id + ", destructAfterLaunch="
				+ destructTime + ", allocatedMissile="
				+ allocatedMissile ;
	}
}
