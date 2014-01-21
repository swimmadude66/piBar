package Models;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

	private static int counter =0;
	private final int id;
	
	public String name;
	public String description;
	public List<Ingredient> ingredients;
	public double totalVolume;
	
	public final RecipeType glass;
	public final List<Instruction> instructions;

	public Recipe(String n, RecipeType g, List<Instruction> instr){
		name =n;
		id = counter++;
		instructions = instr;
		glass = g;
		totalVolume = 0;
		ingredients = new ArrayList<Ingredient>();
		for(Instruction i : instr){
			ingredients.add(i.getIngredient());
			totalVolume += i.getQuantity();
		}
		
		if(totalVolume>glass.volume){
			for(Instruction i : instructions){
				i.setQuantity(i.getQuantity()/totalVolume);
			}
		}
		
	}
	
	public Recipe(String n, RecipeType g,List<Instruction> instr, int identity){
		name =n;
		instructions = instr;
		id = identity;
		counter = identity +1;
		
		glass = g;
		totalVolume = 0;
		ingredients = new ArrayList<Ingredient>();
		for(Instruction i : instr){
			ingredients.add(i.getIngredient());
			totalVolume += i.getQuantity();
		}
		
		if(totalVolume>glass.volume){
			for(Instruction i : instructions){
				i.setQuantity(i.getQuantity()/totalVolume);
			}
		}
	}
	
	public List<Instruction> getInstructions() {
		return instructions;
	}

	
	public int getId(){
		return id;
	}
	
	
}
