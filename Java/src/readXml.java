import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

public class readXml {

	private Enemy_Launcher enemy_launcher;
	private Iron_Dome ironDome;
	private static Launcher_Destroyer launcherDestroyer;


	public readXml(War war, Queue<Enemy_Launcher> launchers, Queue<Iron_Dome> ironDomes, Queue<Launcher_Destroyer> launcherDestroyers, Queue<Enemy_Missile> enemyMissiles) {
		try {
			File file = new File("C:/Users/Andrey/git/Java_Project/Java/src/war.xml");
			// File file = new File("war2.xml");
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			if (doc.hasChildNodes()) {
				printNote(doc.getChildNodes(), war);
			}
		} catch (Exception e) {
			e.getCause();
			e.getMessage();
			e.getStackTrace();
		}
	}

	private  void printNote(NodeList nodeList, War war) throws Exception {
		for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);

			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				if (tempNode.hasAttributes()) {
					NamedNodeMap nodeMap = tempNode.getAttributes();
					for (int i = 0; i < nodeMap.getLength(); i++) {
						if (tempNode.getNodeName() == "launcher") {
							enemy_launcher = new Enemy_Launcher(nodeMap.item(i).getNodeValue(), nodeMap.item(++i).getNodeValue());
							
							war.Create_enemy_launcher(enemy_launcher);

						} else if (tempNode.getNodeName() == "missile") {
							Enemy_Missile missile = new Enemy_Missile(nodeMap.item(i).getNodeValue(), nodeMap.item(++i).getNodeValue(), nodeMap.item(++i).getNodeValue(), nodeMap.item(++i).getNodeValue(), nodeMap.item(++i).getNodeValue(), enemy_launcher);
							war.addMissileToLauncher(enemy_launcher, missile);

						} else if (tempNode.getNodeName() == "destructor") {
							if (nodeMap.item(i).getNodeName() == "id") {
								ironDome = war.Create_Iron_Dome(nodeMap.item(i).getNodeValue());
							} else {
								launcherDestroyer = war.Create_Launcher_Destroyer(nodeMap.item(i).getNodeValue());
							}
						} else if (tempNode.getNodeName() == "destructedLanucher") {
							war.DestroyLauncher(nodeMap.item(i).getNodeValue(), nodeMap.item(++i).getNodeValue(), launcherDestroyer);
						} else if (tempNode.getNodeName() == "destructdMissile") {
							war.InterceptMissile(nodeMap.item(i).getNodeValue(), nodeMap.item(++i).getNodeValue(), ironDome);
						}
					}
				}
				if (tempNode.hasChildNodes()) {
					// loop again if has child nodes
					printNote(tempNode.getChildNodes(), war);
				}
			}
		}
	}
}
