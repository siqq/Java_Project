import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class War {

	public War(){

		try {
			File file = new File("/Users/DELL-PC/git/Java_Project/Java/src/war.xml");		 
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
	private static void printNote(NodeList nodeList) {
		for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				if (tempNode.hasAttributes()) {
					NamedNodeMap nodeMap = tempNode.getAttributes();
					for (int i = 0; i < nodeMap.getLength(); i++) {
						if(tempNode.getNodeName() == "launcher"){
							System.out.println(new Launcher(nodeMap.item(i).getNodeValue(), nodeMap.item(i++).getNodeValue()));
						}
						else if(tempNode.getNodeName() == "missile"){
							System.out.println(new Missile(nodeMap.item(i).getNodeValue(), nodeMap.item(++i).getNodeValue(),
									nodeMap.item(++i).getNodeValue(),nodeMap.item(++i).getNodeValue(),nodeMap.item(++i).getNodeValue() ));
						}
						else if(tempNode.getNodeName() == "destructor"){
							if(nodeMap.item(i).getNodeName() == "id"){
								System.out.println(new missileDestructor(nodeMap.item(i).getNodeValue()));
							}							
							else{
								System.out.println(new Missile_Launcher_Destructor(nodeMap.item(i).getNodeValue()));
							}
						}
						else if(tempNode.getNodeName() == "destructdMissile"){
							System.out.println(new destructMissile(nodeMap.item(i).getNodeValue(), nodeMap.item(++i).getNodeValue()));					
							break;
						}
						else if(tempNode.getNodeName() == "destructedLanucher"){
							System.out.println(new destructLauncher(nodeMap.item(i).getNodeValue(), nodeMap.item(++i).getNodeValue()));
							break;
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