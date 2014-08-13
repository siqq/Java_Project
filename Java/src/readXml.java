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
	private War warXML;
	private static Launcher_Destroyer launcherDestroyer;
	private Queue<Enemy_Launcher> launchers = new LinkedList<Enemy_Launcher>();
	private Queue<Iron_Dome> ironDomes = new LinkedList<Iron_Dome>();
	private Queue<Launcher_Destroyer> LauncherDestroyers = new LinkedList<Launcher_Destroyer>();
	private Queue<Enemy_Missile> enemyMissile = new LinkedList<Enemy_Missile>();

	public readXml(War war) {
		try {
			File file = new File("war.xml");
			// File file = new File("war2.xml");
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			if (doc.hasChildNodes()) {
				printNote(doc.getChildNodes(), war,enemy_launcher,ironDome,launcherDestroyer,ironDomes,LauncherDestroyers,enemyMissile);
			}
		} catch (Exception e) {
		}

	}

	public readXml(War war, Queue<Enemy_Launcher> Prevlaunchers, 
			Queue<Iron_Dome> PrevironDomes, 
			Queue<Launcher_Destroyer> PrevlauncherDestroyer, Queue<Enemy_Missile> PrevenemyMissile) {
		ironDomes=PrevironDomes;
		LauncherDestroyers =PrevlauncherDestroyer;
		launchers=Prevlaunchers;
		enemyMissile=PrevenemyMissile;
	}

	private static void printNote(NodeList nodeList, War war, Enemy_Launcher enemy_launcher, Iron_Dome ironDome, Launcher_Destroyer launcherDestroyer, Queue<Iron_Dome> ironDomes, Queue<Launcher_Destroyer> launcherDestroyers, Queue<Enemy_Missile> enemyMissile) throws Exception {
		for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);

			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				if (tempNode.hasAttributes()) {
					NamedNodeMap nodeMap = tempNode.getAttributes();
					for (int i = 0; i < nodeMap.getLength(); i++) {
						if (tempNode.getNodeName() == "launcher") {
							Enemy_Launcher el = new Enemy_Launcher(nodeMap.item(i).getNodeValue(), nodeMap.item(++i).getNodeValue());
							enemy_launcher = el;
							war.addLauncher(enemy_launcher);

							enemy_launcher.start();
						} else if (tempNode.getNodeName() == "missile") {
							Enemy_Missile mis = new Enemy_Missile(nodeMap.item(i).getNodeValue(), nodeMap.item(++i).getNodeValue(), nodeMap.item(++i).getNodeValue(), nodeMap.item(++i).getNodeValue(), nodeMap.item(++i).getNodeValue(), enemy_launcher);
							enemy_launcher.addMissile(mis);
							enemyMissile.add(mis);							
							

						} else if (tempNode.getNodeName() == "destructor") {
							if (nodeMap.item(i).getNodeName() == "id") {
								ironDome = new Iron_Dome(nodeMap.item(i).getNodeValue());
								ironDomes.add(ironDome);
								ironDome.start();
							} else {
								launcherDestroyer = new Launcher_Destroyer(nodeMap.item(i).getNodeValue());
								launcherDestroyers.add(launcherDestroyer);
								launcherDestroyer.start();
							}
						} else if (tempNode.getNodeName() == "destructedLanucher") {
							launcherDestroyer.addLauncherToDestroy(nodeMap.item(i).getNodeValue(), nodeMap.item(++i).getNodeValue());
						} else if (tempNode.getNodeName() == "destructdMissile") {
							ironDome.addMissileToIntercept((nodeMap.item(i).getNodeValue()), nodeMap.item(++i).getNodeValue());

						}
					}
				}
				if (tempNode.hasChildNodes()) {
					// loop again if has child nodes
					printNote(tempNode.getChildNodes(),war,enemy_launcher,ironDome,launcherDestroyer,ironDomes,launcherDestroyers,enemyMissile);
				}
			}
		}
	}
}
