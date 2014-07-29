import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;






import java.io.File;
import java.util.LinkedList;
import java.util.Queue;


public class readXml {
	private static Enemy_Launcher enemy_launcher;
	private static Iron_Dome ironDome;
	private static Launcher_Destroyer launcherDestroyer;
	
	public readXml(){
		try {
			File file = new File("D:/git/Java/src/war.xml");		 
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder(); 
			Document doc = dBuilder.parse(file); 
			if (doc.hasChildNodes()) {
				//	System.out.println(doc.getChildNodes());
				printNote(doc.getChildNodes());

			}
//			for(Enemy_Launcher enemy_l : War.launchers) { 
//				enemy_l.start();
//				enemyMissile = enemy_l.getMissleQueue();
//				for(Enemy_Missile enemy_m : enemyMissile)  {
//					enemy_m.start();
//					ironDome.start();
//					//ironDome.notifyAll();
//				}
//				enemyMissile.notifyAll();
//				
//			}
		} catch (Exception e) {}
	}
	private static void printNote(NodeList nodeList) throws DOMException, InterruptedException {
		for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);

			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				if (tempNode.hasAttributes()) {
					NamedNodeMap nodeMap = tempNode.getAttributes();
					for (int i = 0; i < nodeMap.getLength(); i++) {
						if(tempNode.getNodeName() == "launcher"){
							enemy_launcher = new Enemy_Launcher(nodeMap.item(i).getNodeValue(), nodeMap.item(i++).getNodeValue());
							War.launchers.add(enemy_launcher);
							enemy_launcher.start();
						}
						else if(tempNode.getNodeName() == "missile"){
							Enemy_Missile enemyMissile = new Enemy_Missile(nodeMap.item(i).getNodeValue(), nodeMap.item(++i).getNodeValue(),
									nodeMap.item(++i).getNodeValue(),nodeMap.item(++i).getNodeValue(),nodeMap.item(++i).getNodeValue(), enemy_launcher );
							War.enemyMissile.add(enemyMissile);
							enemy_launcher.addMissile(enemyMissile);
							enemyMissile.start();
							


						}
						else if(tempNode.getNodeName() == "destructor"){
							if(nodeMap.item(i).getNodeName() == "id"){
								ironDome = new Iron_Dome(nodeMap.item(i).getNodeValue());
								War.ironDomes.add(ironDome);
								ironDome.start();
							}							
							else{
								launcherDestroyer = new Launcher_Destroyer(nodeMap.item(i).getNodeValue());
								War.LauncherDestroyer.add(launcherDestroyer);
								launcherDestroyer.start();
							}
						}
						else if(tempNode.getNodeName() == "destructdMissile"){
							ironDome.checkIfPossibleToIntercept(nodeMap.item(i).getNodeValue(), nodeMap.item(++i).getNodeValue());
		
							
						}
						else if(tempNode.getNodeName() == "destructedLanucher"){
							launcherDestroyer.addWaitingLauncherToDesroy(nodeMap.item(i).getNodeValue(), nodeMap.item(++i).getNodeValue());
						}									
					}
				}
				if (tempNode.hasChildNodes()) {
					// loop again if has child nodes
					printNote(tempNode.getChildNodes());
				}
			}
		}

	}

}
