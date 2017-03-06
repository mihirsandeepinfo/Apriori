package controller;
import java.util.List;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import helper.AssociationRule;
import helper.ItemSet;
import helper.Transactions;

public class ImplementationOfApriori {
	
	public Transactions transactionsInDB;
	public ArrayList<String> transactionItemsinDB;
	public Double minSupportEntered, minConfidenceEntered;
	private ArrayList<ArrayList<String>> transactionsList = new 
			ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<ItemSet>> frequentSets = new ArrayList<ArrayList<ItemSet>>();;
	List<AssociationRule> ruleList = new ArrayList<AssociationRule>();
	private int numTransactions;
	private HashMap <String, Integer> supportPerItemSold;
	private int numItems;
	ImplementationOfApriori(Transactions transactions, Double minSupport, Double minConfidence, String dbnameEntered){
		
		this.transactionsInDB = transactions;
		this.minSupportEntered = minSupport;
		this.minConfidenceEntered = minConfidence;
		this.transactionItemsinDB = transactions.allTransactionElementsinDB;
		this.numItems = transactions.countNumOfTrans();
		this.numTransactions = transactionItemsinDB.size();
		this.transactionsList = transactions.transactionsArrayList;
	}
	void Apriori(){
		
		Map <String, Integer> unitsPerItemSold = unitsPerItemSoldinDB();
		HashSet<String> uniqueItem;
		frequentSets.add(new ArrayList<ItemSet>());
		Iterator<java.util.Map.Entry<String, Integer>> it = unitsPerItemSold.entrySet().iterator();
		try{
			while(it.hasNext()){
				Map.Entry<String, Integer> pairs = (Map.Entry<String, Integer>)it.next();
				uniqueItem = new HashSet<String>();
				uniqueItem.add(pairs.getKey());
				frequentSets.get(0).add(new ItemSet(uniqueItem, pairs.getValue().intValue()));
			}
				
				apriori();
				Associations();
			
			}catch(Exception e){
				e.printStackTrace();
		}
	}
	
	private void Associations(){
		
		ArrayList<ItemSet> associateItems = new ArrayList<ItemSet>();
		for(int i=1; i<this.frequentSets.size(); i++){
			ArrayList<ItemSet> temp = new ArrayList<ItemSet>();
			temp = this.frequentSets.get(i);
			for(int j=0; j<temp.size(); j++){
				ItemSet val = temp.get(j);
				associateItems.add(val);
			}
		}
		generateAssociations(associateItems);
	}
	
	private void generateAssociations(ArrayList<ItemSet> associateItems){
		
		List<AssociationRule> rulesBeforePruning = new ArrayList<AssociationRule>();
		for(ItemSet it : associateItems){
			Set<String> itemSet = it.getItem();
			int length = itemSet.size();
			int power = (int) Math.pow(2, length);
			String[] itemSetArray = itemSet.toArray(new String[itemSet.size()]);
			ArrayList<TreeSet<String>> Set = new ArrayList<TreeSet<String>>();
			for(int i=1; i<power-1; i++){
				int center =i;
				int pos=0;
				TreeSet<String> elements = new TreeSet<String>();
				while (center>0){
					if((center & 1) == 1){
						elements.add(itemSetArray[pos]);
					}
					center >>=1;
					pos++;
				}
				Set.add(elements);
			}
			for(Set<String> s: Set){
				Set<String> symmetricDiff = new HashSet<String>(itemSet);
				symmetricDiff.addAll(s);
				Set<String> tmp = new HashSet<String>(itemSet);
				tmp.retainAll(s);
				symmetricDiff.removeAll(tmp);
				AssociationRule rule = new AssociationRule(transactionsList);
				rule.setLeft(tmp);
				rule.setRight(symmetricDiff);
				rule.setLeftSupport(it.getSuppCount());
				rule.setRightSupport(it.getSuppCount());
				rulesBeforePruning.add(rule);
			}
		}
		for(int i=0;i< rulesBeforePruning.size(); i++){
			AssociationRule currentRule = rulesBeforePruning.get(i);
			if(currentRule.getLeft().containsAll(currentRule.getRight())){
				rulesBeforePruning.remove(currentRule);
				continue;
			}
			if(currentRule.computeAndGetConfidence()<(minConfidenceEntered/100d)){
				continue;
			}
			ruleList.add(currentRule);
		}
		for(int i=0;i<this.frequentSets.size();i++){    
			ArrayList<ItemSet> test = new ArrayList<ItemSet>();  
			test=this.frequentSets.get(i);      
			for(int j=0;j<test.size();j++){       
				ItemSet val = test.get(j);  
				Set<String> key = val.getItem();   
				Integer value = val.getSuppCount();   
				double support = (value / (double) transactionsList.size()) * 100.0;  
				System.out.println(Arrays.toString(key.toArray()) + ", "+ new DecimalFormat("0.##").format(support) + "%");    }   }   
				System.out.println("\n"+"Strong Association Rules (Support %,Confidence %):" + "\n"+"******************************************************");  
				for (int i = 0; i < ruleList.size(); i++) {  
					System.out.println(ruleList.get(i));   
			}
	  } 
	private void apriori(){
		int step = 0;
		while(frequentSets.get(step).size() !=0){
			
			frequentSets.add(new ArrayList<ItemSet>());
			Union(step);
			step++;
			SuppCount(step);
			}
			applyMinSuppCount(step);			
		}
	
	private void SuppCount(int state){
		
		for(int i=0; i<frequentSets.get(state).size(); i++){
			for(int j=0; j<transactionsList.size(); j++){
				if(transactionsList.get(j).containsAll(frequentSets.get(state).get(i).getItem()));
				frequentSets.get(state).get(i).setSuppCount(frequentSets.get(state).get(i).getSuppCount()+1);
			}
		}
	}
	
	private void Union(int step){
		
		HashSet<String> itemSet = new HashSet<String>();
		ArrayList<HashSet<String>> list = new ArrayList<HashSet<String>>();
		boolean exists = false;
		for(int i=0; i<frequentSets.get(step).size(); i++){
			for(int j=i+1; j<frequentSets.get(step).size(); j++){
				itemSet = new HashSet<String>();
				int dupCounter = 0;
				HashSet<String> duplicate = frequentSets.get(step).get(j).getItem();
				for(String dup : duplicate){
					
					if(frequentSets.get(step).get(i).getItem().contains(dup)){
						dupCounter++;
					}
				}
				if(dupCounter==step){
					itemSet.addAll(frequentSets.get(step).get(i).getItem());
					itemSet.addAll(frequentSets.get(step).get(j).getItem());
					list.add(itemSet);
				}
			}
		}
		System.out.println("\n");
		for(HashSet<String> val :list){
			for(int k=0; k<frequentSets.get(step+1).size() && !exists; k++){
				if(frequentSets.get(step+1).get(k).getItem().equals(val))
					exists = true;
			}
			if(!exists){
				frequentSets.get(step+1).add(new ItemSet(val,0));
			}
			else{
				exists = false;
			}
		}
	}
	
	private void applyMinSuppCount(int state){
		for(int i=0; i<frequentSets.get(state).size(); i++){
			if(((frequentSets.get(state).get(i).getSuppCount()/(double) transactionsList.size())*100)<minSupportEntered){
				frequentSets.get(state).remove(i);
				i--;
			}
		}
	}
	
	private HashMap<String, Integer> unitsPerItemSoldinDB(){
		HashMap<String,Integer>unitsPerItemSoldinDB = new HashMap<String, Integer>();
		Integer countPerItem = 0;
		ArrayList<ArrayList<String>> itemsObject = transactionsInDB.transactionsArrayList;
		ArrayList<String> itemsPerTransaction = new ArrayList<String>();
		for(int i=0;i<transactionItemsinDB.size();i++){
			for(int j=0; j<itemsObject.size(); j++){
				itemsPerTransaction = itemsObject.get(j);
				for(int k=0; k<itemsPerTransaction.size(); k++){
					if(itemsPerTransaction.get(k).equals(transactionItemsinDB.get(i)))
						countPerItem++;
				}
			}
			unitsPerItemSoldinDB.put(transactionItemsinDB.get(i),countPerItem);
			countPerItem = 0;
		}
		return unitsPerItemSoldinDB;
	}
	private HashSet<String> item;
	private int suppCount;
	public void ItemSet(HashSet<String> item, int count){
		this.item = item;
		this.suppCount = count;
	}
	public HashSet<String> getItem(){
		return item;
	}
	public void setItem(HashSet<String> item){
		this.item = item;
	}
	public int getSuppCount(){
		return suppCount;
	}
	public void setSuppCount(int suppCount){
		this.suppCount = suppCount;
	}
	Set<String> left;
	Set<String> right;
	int leftSupport, rightSupport, support;
	double confidence;
	ArrayList<ArrayList<String>> transactionList = new ArrayList<ArrayList<String>>();
	public void AssociationRule(ArrayList<ArrayList<String>> t1){
		transactionList = t1;
		left = new HashSet<String>();
		right = new HashSet<String>();
	}
	private int getSupportCount(Set<String> set){
		int count = 0;
		for(int i=0; i<transactionList.size(); i++){
			if(transactionList.get(i).containsAll(set)){
				count++;
			}
		}
		return count;
	}
	@Override
	public String toString(){
		return Arrays.toString(left.toArray())+"==>" + Arrays.toString(right.toArray())+ "(Supp: " + new DecimalFormat("0.##").format((getSupportCount(union(left, right))/(double) transactionList.size())*100)+ "% , Conf: " + new DecimalFormat("0.##").format(confidence*100)+"%)";
	}
	public double computeAndGetConfidence(){
	
		confidence = getSupportCount(union(left, right));
		support = (int) confidence;
		confidence /= getSupportCount(left);
		return confidence;
	}
	public <T> Set<T> union(Set<T> setA, Set<T> setB){
		Set<T> tmp = new HashSet<T>(setA);
		tmp.addAll(setB);
		return tmp;
	}
	public Set<String> getLeft(){
		return left;
	}
	public void setLeft(Set<String> left){
		this.left = left;
	}
	public Set<String> getRight(){
		return right;
	}
	public void setRight(Set<String> right){
		this.right = right;
	}
	public int getLeftSupport(){
		return leftSupport;
	}
	public void setLeftSupport(int leftSupport){
		this.leftSupport = leftSupport;
	}
	public int getRightSupport(){
		return rightSupport;
	}
	public void setRightSupport(int rightSupport){
		this.rightSupport = rightSupport;
	}
	public double getConfidence(){
		return confidence;
	}
	public void setConfidence(double confidence){
		this.confidence = confidence;
	}
	public double getSupport(){
		return support;
	}
	public void setSupport(int support){
		this.support = support;
	}
	public ArrayList<ArrayList<String>> getTransactionList(){
		return transactionList;
	}
	public void setTransactionList(ArrayList<ArrayList<String>> transactionList){
		this.transactionList = transactionList;
	}
}

