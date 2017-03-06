package resources;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import helper.EachTransaction;

import org.w3c.dom.Element;

public class ReadXMLFile {
		public ArrayList<EachTransaction> getTransactions(String fileName){
			EachTransaction eachTransaction;
			String transactionNum;
			ArrayList<String> eachTransactionArray;
			ArrayList<EachTransaction> transactions = new ArrayList<EachTransaction>();
	try{
		fileName = fileName+".xml";
		File fXmlFile = new File("./src/resources/"+fileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("eachTransaction");
		
		for(int temp=0; temp<nList.getLength(); temp++){
			Node nNode = nList.item(temp);
			if(nNode.getNodeType() == Node.ELEMENT_NODE){
				eachTransaction = new EachTransaction();
				eachTransactionArray = new ArrayList<String>();
				
				Element eElement = (Element) nNode;
				transactionNum = eElement.getAttribute("id");
				NodeList items = eElement.getElementsByTagName("item");
				for(int i=0;i<items.getLength();i++){
					eachTransactionArray.add(items.item(i).getTextContent());
				}
				eachTransaction.addEachTransaction(transactionNum,eachTransactionArray);
				transactions.add(eachTransaction);
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return transactions;
}
}

