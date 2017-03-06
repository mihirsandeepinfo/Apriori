package helper;

import java.util.ArrayList;
import java.util.HashMap;

public class EachTransaction {
	
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
