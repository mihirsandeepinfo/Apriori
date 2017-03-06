package helper;

import java.util.HashSet;

public class ItemSet {
	
	private HashSet<String> item;
	private int suppCount;
	public ItemSet(HashSet<String> item, int count){
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

}
