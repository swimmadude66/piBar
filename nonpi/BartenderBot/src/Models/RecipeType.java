package Models;

public enum RecipeType {
	HIGHBALL (0,12), 
	LOWBALL (1,6),
	MARTINI(2,4),
	SHOT (2,1);
	
	public int Id;
	public int volume;
	
	RecipeType(int id, int vol){
		this.Id= id;
		this.volume = vol;
	}
}
