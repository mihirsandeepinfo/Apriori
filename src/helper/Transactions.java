package helper;

import java.util.ArrayList;
import java.util.HashMap;

import resources.ReadXMLFile;

public class Transactions {
	public ArrayList<EachTransaction> transactions = new ArrayList<EachTransaction> ();
	public ArrayList<ArrayList<String>> transactionsArrayList = new ArrayList<ArrayList<String>>();
	public ArrayList<String> allTransactionElementsinDB = new ArrayList<String>();
	
	public Transactions(String databaseName){
		transactions = getAllTransactions(databaseName);
		transactionsArrayList = getTransactionsArrayLists();
		allTransactionElementsinDB = getAllTransactionElementsinDB();
		
	}
	public EachTransaction get(int i){
		return transactions.get(i);
	}
	public static ArrayList<EachTransaction> getAllTransactions(String databaseName){
		
		ReadXMLFile readXMLFile = new ReadXMLFile();
		String fileName = "walmart";
		if(databaseName.equalsIgnoreCase("Walmart")){
			fileName = "walmart";
		}
		else if(databaseName.equalsIgnoreCase("Amazon")){
			fileName = "amazon";
		}
		if(databaseName.equalsIgnoreCase("DollarStore")){
			fileName = "dollarstore";
		}
		if(databaseName.equalsIgnoreCase("Walgreen")){
			fileName = "walgreen";
		}
		if(databaseName.equalsIgnoreCase("ebay")){
			fileName = "ebay";
		}
		return readXMLFile.getTransactions(fileName);
	}
	public ArrayList<ArrayList<String>> getTransactionsArrayLists(){
	
		String transactionNumber;
		for(int i=0;i<transactions.size();i++){
			transactionNumber = transactions.get(i).getTransactionNumber();
			transactionsArrayList.add(transactions.get(i).itemsInTransaction(transactionNumber));
		}
		return transactionsArrayList;
	}
	public int countNumOfTrans(){
		return transactions.size();
	}
	public ArrayList<String> getAllTransactionElementsinDB(){
		ArrayList<String> allElementsinTransaction = new ArrayList<String>();
		ArrayList<String> itemsinEachTransaction = new ArrayList<String>();
		String eachItem;
		
		for(int i=0;i<transactionsArrayList.size();i++){
			itemsinEachTransaction = transactionsArrayList.get(i);
			for(int j=0;j<itemsinEachTransaction.size();j++){
				eachItem = itemsinEachTransaction.get(j);
				if(!allElementsinTransaction.contains(eachItem))
					allElementsinTransaction.add(eachItem);
			}
		}
		return allElementsinTransaction;
	}
	HashMap<String, ArrayList<String>> eachTransaction = new HashMap<String, ArrayList<String>>();
	
	public void addEachTransaction(String transactionNum, ArrayList<String> eachTransactionArray){
		eachTransaction.put(transactionNum, eachTransactionArray);
	}
	public ArrayList<String> itemsInTransaction(String transactionNumber){
		return eachTransaction.get(transactionNumber);
	}
	public int numOfItemsInTransaction(String transactionNumber){
		return eachTransaction.get(transactionNumber).size();
	}
	public String getTransactionNumber(){
		String transactionNumber = eachTransaction.keySet().iterator().next();
		return transactionNumber;
	}
	public void clear(){
		eachTransaction.clear();
	}
}
