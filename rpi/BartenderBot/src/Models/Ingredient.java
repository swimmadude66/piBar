package Models;

public class Ingredient implements Comparable<Ingredient>{

	public String name;
	public static int counter;
	public final int Id;
	public final IngredientType category;
	//liquor=0;
	//liqueur=1;
	//mixer=2;
	
	public Ingredient(String n, IngredientType type){
		name =n;
		Id = counter++;
		category = type;
	}
	public Ingredient(String n, IngredientType type, int id){
		name = n;
		Id = id;
		counter = id+1;
		category = type;
	}
	
	public String toString(){
		return name;
	}
	
	public int compareTo(Ingredient i) {
		return name.compareTo(i.name);
	}

	
}
