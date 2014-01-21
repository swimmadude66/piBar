package Backend;

/*import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;*/

public class DAO {
	/*Connection con;
	Statement stmt;
	
	public void connectToDatabase() { 
		System.out.println("Connecting to Database...");
	    try {
			con = DriverManager.getConnection(
			                     "jdbc:sqlserver:/;databaseName=BartenderBotDB;",
			                     "bartender",
			                     "mixology");
			 System.out.println("Connection Established!");

		} catch (SQLException e) {
			System.out.println("Connection Failed!");
			e.printStackTrace();
		}
	}

	public void saveSerializedToDB(String state){
		System.out.println("Saving...");
		try{
			connectToDatabase();
			stmt = con.createStatement();
			String sql = "INSERT INTO BartenderBotDB.dbo.Recipes"
					+ " VALUES('" + state +"')";
			stmt.executeUpdate(sql);
			System.out.println("SAVED TO DB!");
		}
		catch(Exception e){
			System.out.println("Save to Serialized Failed");
			e.printStackTrace();
		}
	}
	
	public String loadSerializedFromDB(int id){
		System.out.println("Loading...");
		try{
			connectToDatabase();
			stmt = con.createStatement();
			String query = "SELECT *"
					+ " FROM BartenderBotDB.dbo.Recipes"
					+ " WHERE ID = "+ id;
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			System.out.println("Loaded from DB!");
			return(rs.getString("RecipeJSON"));
		}
		catch(Exception e){
			System.out.println("Load Serialized from DB Failed!");
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> getSerializedRecipes(){
		System.out.println("Loading...");
		List<String> recipes = new ArrayList<String>();
		try{
			connectToDatabase();
			stmt = con.createStatement();
			String query = "SELECT *"
					+ " FROM BartenderBotDB.dbo.Recipes";
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				recipes.add(rs.getString("RecipeJSON"));
			}
			System.out.println("Loaded from DB!");
			return(recipes);
		}
		catch(Exception e){
			System.out.println("Load Serialized from DB Failed!");
			e.printStackTrace();
			return null;
		}
	}
*/
}
