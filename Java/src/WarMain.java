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

public class WarMain {
	public static void main(String[] args) {
		try {
			File file = new File("/Users/DELL-PC/git/Java_Project/Java/src/war.xml");		 
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder(); 
			Document doc = dBuilder.parse(file); 
			if (doc.hasChildNodes()) {
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
					int i =0;
					while (i < nodeMap.getLength()-1) {
						if(tempNode.getNodeName() == "launcher"){
							System.out.println(new Launcher(nodeMap.item(i).getNodeValue(), nodeMap.item(++i).getNodeValue()));
						}
						else if(tempNode.getNodeName() == "missile"){
							System.out.println(new Missile(nodeMap.item(i).getNodeValue(), nodeMap.item(++i).getNodeValue(),
									nodeMap.item(++i).getNodeValue(),nodeMap.item(++i).getNodeValue(),nodeMap.item(++i).getNodeValue() ));
						}
						else if(tempNode.getNodeName() == "destructor"){
							break;
						}
						else if(tempNode.getNodeName() == "destructdMissile"){
							break;						
						}
						else if(tempNode.getNodeName() == "destructedLanucher"){
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