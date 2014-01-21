package Models;

public class Instruction {
		
	private Ingredient ingredient;
	private double quantity;
	
	public Instruction(Ingredient i, double amount){
		setIngredient(i);
		quantity = amount;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}
	
	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

}
