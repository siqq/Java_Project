
public class destructMissile {

	private String id;
	private int destructAfterLaunch;
	//private Missle_Launcher allocatedMissile = new Missle_Launcher();
	public destructMissile() {
		this.id = "M"+(int)Math.random()*100;
	}
	public destructMissile(String destructAfterLaunch, String id) {
		this.id = id;
		this.destructAfterLaunch = Integer.parseInt(destructAfterLaunch);
	}
	@Override
	public String toString() {
		return "destructMissile id " + id + ", destructAfterLaunch= "
				+ destructAfterLaunch + ", allocatedMissile= ";
	//			+ allocatedMissile ;
	}
}
