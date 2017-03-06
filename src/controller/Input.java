package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import helper.Transactions;
import helper.EachTransaction;


public class Input {
	
	public ArrayList<EachTransaction> getTransactions(String file){
		EachTransaction eachTransaction;
		String transactionNum;
		ArrayList<String> eachTransactionArray;
		ArrayList<EachTransaction> transactions = new ArrayList<EachTransaction>();
	try{
		file = file+".xml";
		File fXmlFile = new File("./src/resources/"+file);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		NodeList List = doc.getElementsByTagName("eachTransaction");
	
		for(int temp=0; temp<List.getLength(); temp++){
			Node node = List.item(temp);
			if(node.getNodeType() == Node.ELEMENT_NODE){
				eachTransaction = new EachTransaction();
				eachTransactionArray = new ArrayList<String>();
			
				Element eElement = (Element) node;
				transactionNum = eElement.getAttribute("transaction");
				NodeList items = eElement.getElementsByTagName("product");
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

	static String dbnameEntered = "";
	static String minSupportEntered = "0";
	static String minConfidenceEntered = "0";
	
	public static void main(String args[]){
		try{
			read();
			Double minSupport = Double.parseDouble(minSupportEntered);
			Double minConfidence = Double.parseDouble(minConfidenceEntered);
			
			Transactions transactions = new Transactions (dbnameEntered);
			ImplementationOfApriori aprori = new ImplementationOfApriori(transactions, minSupport, minConfidence, dbnameEntered);
			aprori.Apriori();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	private static void read(){
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try{
			System.out.println("Enter minimum support: ");
			minSupportEntered = reader.readLine();
			
			System.out.println("Enter minimum confidence: ");
			minConfidenceEntered = reader.readLine();
			
			System.out.println("Enter database name: ");
			dbnameEntered = reader.readLine();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
