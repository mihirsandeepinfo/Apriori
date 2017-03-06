package helper;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
public class AssociationRule {

	Set<String> left;
	Set<String> right;
	int leftSupport, rightSupport, support;
	double confidence;
	ArrayList<ArrayList<String>> transactionList = new ArrayList<ArrayList<String>>();
	public AssociationRule(ArrayList<ArrayList<String>> t1){
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
