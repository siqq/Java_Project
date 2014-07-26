import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;



import java.io.File;


public class readXml {
	private static Enemy_Launcher enemy_launcher;
	private static Iron_Dome ironDome;
	private static Launcher_Destroyer launcherDestroyer;
	public readXml(){

		try {
			File file = new File("C:/Users/DELL-PC/git/Java_Project/Java/src/war.xml");		 
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder(); 
			Document doc = dBuilder.parse(file); 
			if (doc.hasChildNodes()) {
				//	System.out.println(doc.getChildNodes());
				printNote(doc.getChildNodes());

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
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
							Thread enemyLauncherThread = new Thread(enemy_launcher);
							enemyLauncherThread.start();
						}
						else if(tempNode.getNodeName() == "missile"){
							enemy_launcher.addMissile(new Enemy_Missile(nodeMap.item(i).getNodeValue(), nodeMap.item(++i).getNodeValue(),
									nodeMap.item(++i).getNodeValue(),nodeMap.item(++i).getNodeValue(),nodeMap.item(++i).getNodeValue(), enemy_launcher ));

						}
						else if(tempNode.getNodeName() == "destructor"){
							if(nodeMap.item(i).getNodeName() == "id"){
								ironDome = new Iron_Dome(nodeMap.item(i).getNodeValue());
								Thread ironDomeThread = new Thread(ironDome);
								ironDomeThread.start();
							}							
							else{
								launcherDestroyer = new Launcher_Destroyer(nodeMap.item(i).getNodeValue());
								Thread launcherDestroyerThread = new Thread(launcherDestroyer);
								launcherDestroyerThread.start();
							}
						}
						else if(tempNode.getNodeName() == "destructdMissile"){
							ironDome.addIronDomeMissile(new IronDomeMissile(nodeMap.item(i).getNodeValue(), nodeMap.item(++i).getNodeValue(),ironDome));
						}
						else if(tempNode.getNodeName() == "destructedLanucher"){
							launcherDestroyer.addMissile(new Launcher_Destroyer_missile(nodeMap.item(i).getNodeValue(), nodeMap.item(++i).getNodeValue(), launcherDestroyer));

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