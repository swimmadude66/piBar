package Backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ReadWriter {

	private BufferedWriter writer;
	
	/*
	 * Paths for my different dev environments
	 */
	private final String piPath ="/root/BartenderBot/Data/";
	private final String laptopPath = "/";
	private final String pcPath ="/";
	private final String path = laptopPath;
	
	public ReadWriter(){
		
	}
	
	public void saveRecipes(List<String> states){
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path+"Recipes.cfg"), "utf-8"));
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(String state: states){
			try {
			    writer.write(state+"\n");
			    
			} 
			catch (IOException ex){
			  System.out.println("Recipes not Saved");
			} 
			   
		}
		try {
			   writer.flush();
			   writer.close();
			   } 
		   catch (Exception ex) {} 
		System.out.println("Saved!");
	}
		
	public List<String> loadRecipes(){
			BufferedReader br= null;
			List<String> recs = new ArrayList<String>();
			try {
				br = new BufferedReader(new FileReader(path + "Recipes.cfg"));
		        String line = br.readLine();
		        while (line != null) {
		            recs.add(line.trim());
		            line = br.readLine();
		        }
		    } 
			catch (Exception ex){
				System.out.println("SAVE CANNOT BE FOUND");
			}
			
			finally {
				try{
					if(br!=null)
						br.close();
				}
				catch(Exception ex){
					
				}
		    }
			return recs;
	}
	
	public void saveConfig(List<String> states){
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path+"Buckets.cfg"), "utf-8"));
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(String state: states){
			try {
			    writer.write(state+"\n");
			    
			} 
			catch (IOException ex){
			  System.out.println("Configuration not Saved");
			} 
			   
		}
		try {
			   writer.flush();
			   writer.close();
			   } 
		   catch (Exception ex) {} 
		System.out.println("Saved!");
	}

	public List<String> loadConfig(){
		BufferedReader br= null;
		List<String> config = new ArrayList<String>();
		try {
			br = new BufferedReader(new FileReader(path +"Buckets.cfg"));
	        String line = br.readLine();
	        while (line != null) {
	            config.add(line);
	            line = br.readLine();
	        }
	    } 
		catch (Exception ex){
			System.out.println("SAVE CANNOT BE FOUND");
		}
		
		finally {
			try{
				if(br!=null)
					br.close();
			}
			catch(Exception ex){
				
			}
	    }
		return config;
	}

	public void saveIngredients(List<String> states){
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path+"Ingredients.cfg"), "utf-8"));
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(String state: states){
			try {
			    writer.write(state+"\n");
			    
			} 
			catch (IOException ex){
			  System.out.println("Ingredients not Saved");
			} 
			   
		}
		try {
			   writer.flush();
			   writer.close();
			   } 
		   catch (Exception ex) {} 
		System.out.println("Saved!");
	}

	public List<String> loadIngredients(){
		BufferedReader br= null;
		List<String> config = new ArrayList<String>();
		try {
			br = new BufferedReader(new FileReader(path+"Ingredients.cfg"));
	        String line = br.readLine();
	        while (line != null) {
	            config.add(line);
	            line = br.readLine();
	        }
	    } 
		catch (Exception ex){
			System.out.println("SAVE CANNOT BE FOUND");
		}
		
		finally {
			try{
				if(br!=null)
					br.close();
			}
			catch(Exception ex){
				
			}
	    }
		return config;
	}
}
