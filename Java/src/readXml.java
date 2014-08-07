import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import java.io.File;


public class readXml{
	private static Enemy_Launcher enemy_launcher;
	private static Iron_Dome ironDome;
	private static Launcher_Destroyer launcherDestroyer;
	public readXml(){
		try {
//			File file = new File("C:/Users/Andrey/git/Java_Project/Java/src/war.xml");
			File file = new File("C:/Users/DELL-PC/git/Java_Project/Java/src/war2.xml");
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder(); 
			Document doc = dBuilder.parse(file); 
			if (doc.hasChildNodes()) {
				printNote(doc.getChildNodes());
			}
		} catch (Exception e) {}
	}	

	private static void printNote(NodeList nodeList) throws Exception {
		for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);

			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				if (tempNode.hasAttributes()) {
					NamedNodeMap nodeMap = tempNode.getAttributes();
					for (int i = 0; i < nodeMap.getLength(); i++) {
						if(tempNode.getNodeName() == "launcher"){
							enemy_launcher = new Enemy_Launcher(nodeMap.item(i).getNodeValue(), nodeMap.item(++i).getNodeValue());
							War.launchers.add(enemy_launcher);
							enemy_launcher.start();
						}
						else if(tempNode.getNodeName() == "missile"){
							Enemy_Missile enemyMissile = new Enemy_Missile(nodeMap.item(i).getNodeValue(), nodeMap.item(++i).getNodeValue(),
									nodeMap.item(++i).getNodeValue(),nodeMap.item(++i).getNodeValue(),nodeMap.item(++i).getNodeValue(), enemy_launcher  );
							War.enemyMissile.add(enemyMissile);
							enemy_launcher.addMissile(enemyMissile);
							
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
						else if(tempNode.getNodeName() == "destructedLanucher"){
							launcherDestroyer.addLauncherToDestroy(nodeMap.item(i).getNodeValue(),nodeMap.item(++i).getNodeValue());
						}	
						else if(tempNode.getNodeName() == "destructdMissile"){
							ironDome.addMissileToIntercept((nodeMap.item(i).getNodeValue()),nodeMap.item(++i).getNodeValue());
							
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
