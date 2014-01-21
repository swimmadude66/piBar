package Managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Backend.DAO;
import Backend.HardwareControl;
import Backend.ReadWriter;
import Models.Ingredient;
import Models.IngredientType;
import Models.Instruction;
import Models.Recipe;

import com.google.gson.*;


public class Control {
	HardwareControl hc;
	DAO dao;
	public List<Recipe> recipes;
	public List<Instruction> instructions;
	public List<Ingredient> buckets;
	public List<Integer> bucketIds;
	public List<Ingredient> ingredients;
	
	final ReadWriter rw;
	final Gson gson;
	
	public Control(){
		hc = new HardwareControl(this);
		dao = new DAO();
		rw = new ReadWriter();
		gson = new Gson();
		instructions= new ArrayList<Instruction>();
		buckets = new ArrayList<Ingredient>();
		recipes = new ArrayList<Recipe>();
		bucketIds = new ArrayList<Integer>();
		ingredients = new ArrayList<Ingredient>();
		getRecipes();
		getConfig();
		getIngredients();
		
	}
	
	public void makeRecipe(Recipe r){
		hc.makeRecipe(r);
	}
	
	public void changeConfig(List<Ingredient> config){
		buckets.clear();
		bucketIds.clear();
		for(Ingredient i : config){
			buckets.add(i);
			bucketIds.add(i.Id);
		}
	}
	
	public void addIngredient(Ingredient ingredient){
		List<String> serial = rw.loadIngredients();
		String ing = gson.toJson(ingredient);
		serial.add(ing);
		rw.saveIngredients(serial);
	}
	
	
	/*
	 * Domain Calls
	 */
	public void saveConfig(){
		List<String> serial = new ArrayList<String>();
		for(Ingredient b : buckets){
			String bucket = gson.toJson(b);
			serial.add(bucket);
		}
		rw.saveConfig(serial);
	}
	
	public void getConfig(){
		List<String> ser = rw.loadConfig();
		buckets.clear();
		for(String s : ser){
			Ingredient b = gson.fromJson(s, Ingredient.class);
			buckets.add(b);
			bucketIds.add(b.Id);
		}
	}
	
	public void saveRecipes(){
		List<String> serial = new ArrayList<String>();
		for(Recipe r : recipes){
			String rec = gson.toJson(r);
			serial.add(rec);
		}
		rw.saveRecipes(serial);
	}
	
	public void getRecipes(){
		List<String> recs = rw.loadRecipes();
		recipes.clear();
		for(String rec : recs){
			Recipe r = gson.fromJson(rec, Recipe.class);
			recipes.add(r);
		}
		
	}
	
	public List<Ingredient> getIngredients(){
		ingredients.clear();
		List<String> ings = rw.loadIngredients();
		List<Ingredient> result= new ArrayList<Ingredient>();
		List<Ingredient> liquors = new ArrayList<Ingredient>();
		List<Ingredient> liqueurs = new ArrayList<Ingredient>();
		List<Ingredient> mixers = new ArrayList<Ingredient>();
		for(String s  : ings){
			Ingredient i = gson.fromJson(s, Ingredient.class);
			if(i.category.equals(IngredientType.LIQUOR)){
				liquors.add(i);
			}
			else if(i.category.equals(IngredientType.LIQUEUR)){
				liqueurs.add(i);
			}
			else if(i.category.equals(IngredientType.MIXER)){
				mixers.add(i);
			}
			else{
				result.add(i);
			}
		}
		Collections.sort(liquors);
		Collections.sort(liqueurs);
		Collections.sort(mixers);
		result.addAll(liquors);
		result.addAll(liqueurs);
		result.addAll(mixers);
		ingredients.addAll(result);		
		return ingredients;
	}
	
	
	/*
	 * Testing Area
	 */
	public void pourValve(int valveId, long time){
		hc.pourValve(valveId, time);
	}
	
	
}
