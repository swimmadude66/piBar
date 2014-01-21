package UI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Managers.Control;
import Models.Recipe;
import Models.RecipeType;

import javax.swing.JLabel;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;

public class NewRecipeScreen extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -853826749839065923L;

	/**
	 * Create the panel.
	 */
	
	final Control cont;
	final JFrame parent;
	public int w;
	public int h;
	
	JButton save;
	JButton cancel;
	JTextField txtName;
	JTextArea txtDesc;
	Recipe newRec;
	RecipeType glass;
	
	
	public NewRecipeScreen(Control c, JFrame p, RecipeType g) {
		cont = c;
		parent = p;
		w = parent.getSize().width;
		h = parent.getSize().height;
		this.setName("New Recipe");
		setLayout(null);
		glass =g;
		
		
		save = new JButton("Save");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveRecipe();
				dispose();
				}
		});
		save.setBounds((w/2)-110, (h/2)+50, 100, 50);
		add(save);
		
		cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				}
		});
		cancel.setBounds((w/2)+10, (h/2)+50, 100, 50);
		add(cancel);
		
		txtName = new JTextField();
		txtName.setBounds((w/2), 50, 150, 25);
		add(txtName);
		txtName.setColumns(100);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds((w/2)-75, 50, 50, 25);
		add(lblName);
		
		txtDesc = new JTextArea();
		txtDesc.setBounds((w/2), 100, 150, 150);
		add(txtDesc);
		
		JLabel lblDesc = new JLabel("Notes");
		lblDesc.setBounds((w/2)-75, 100, 50, 25);
		add(lblDesc);
	}
	
	public void saveRecipe(){
		if(txtName.getText()!=  null || txtName.getText().trim()!=""){
			String name = txtName.getText().trim();
			newRec = new Recipe(name, glass, cont.instructions, cont.recipes.size());
			newRec.description=txtDesc.getText().trim();
			cont.recipes.add(newRec);
			cont.saveRecipes();
		}
	}
	
	public void dispose(){
		Container contentPane = parent.getContentPane();
		CardLayout cl = (CardLayout)contentPane.getLayout();
		cl.show(contentPane, "Custom");
		Component[] comps = contentPane.getComponents();
		for(Component c : comps){
			if(c.getName().equals("New Recipe")){
				contentPane.remove(c);
				break;
			}
		}
	}
}
