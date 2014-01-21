package UI;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Managers.Control;
import Models.Ingredient;
import Models.Instruction;
import Models.Recipe;
import Models.RecipeType;

import javax.swing.JSlider;
import javax.swing.event.*;
import javax.swing.JScrollPane;

import sun.awt.VerticalBagLayout;

import java.util.ArrayList;
import java.util.List;

public class CustomScreen extends JPanel {

	private static final long serialVersionUID = 2051089170590690008L;
	/**
	 * Create the panel.
	 */
	private final boolean DEBUG = false;
	final Control cont;
	final JFrame parent;
	public int w;
	public int h;
	
	JSlider slider;
	JLabel sliderVal;
	JPanel listContainer;
	List<Instruction> customInstr;
	JComboBox<Ingredient> JCBingredient;
	List<JLabel> ingDisplay;
	JButton	Add;
	JComboBox<RecipeType> type;
	
	final public JButton[] valves;
	private JScrollPane scrollPane;
	private final RecipeType[] typeChoice = {RecipeType.HIGHBALL,RecipeType.LOWBALL,RecipeType.MARTINI,RecipeType.SHOT};
	
	public CustomScreen(Control c, JFrame p) {

		cont = c;
		parent = p;
		w = parent.getSize().width;
		h= parent.getSize().height;
		this.setName("Custom");
		valves = new JButton[8];
		customInstr = new ArrayList<Instruction>();
		ingDisplay = new ArrayList<JLabel>();
		setLayout(null);
		
		if(DEBUG){
			List<Ingredient> optlist = cont.getIngredients();
			Ingredient [] choices = new Ingredient[optlist.size()];
			JCBingredient = new JComboBox<Ingredient>(optlist.toArray(choices));
			JCBingredient.setBounds((w/2)-100,h-200 ,(w/8)-50,50);
			Add = new JButton("Add");
			Add.setBounds((w/2)+25,h-200 ,(w/8)-50,50);
			Add.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(slider.getValue()>0)
						addInstruction((Ingredient)JCBingredient.getSelectedItem(),slider.getValue()*0.25);
				}
			});
			add(JCBingredient);
			add(Add);
		}
		else{
			for(int i=0; i<8; i++){
				String name;
					if(i<cont.buckets.size()){
						Ingredient bucket = cont.buckets.get(i);	
						if(bucket!=null){name = bucket.name;}
						else{name = "N/A";}
					}
					else{name ="N/A";}
				JButton valve = new JButton(name);
				if(i>cont.buckets.size()-1){
					valve.setEnabled(false);
				}
				else if(cont.buckets.get(i) == null){
					valve.setEnabled(false);
				}
				valve.setBounds((i*w/8)+25,h-200 ,(w/8)-50,50);
				final int index = i;
				valve.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(slider.getValue()>0)
							addInstruction(getBucketContents(index),slider.getValue()*0.25);
					}
				});
				valves[i] = valve;
				add(valve);
			}
		}
		
		JButton btnRecipes = new JButton("Recipes");
		btnRecipes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Container contentPane = parent.getContentPane();
				CardLayout cards = (CardLayout) contentPane.getLayout();
				cards.show(contentPane, "Recipes");
			}
		});
		btnRecipes.setBounds(w/2-200, h-100, 100, 50);
		add(btnRecipes);
		
		JButton btnSettings = new JButton("Settings");
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Container contentPane = parent.getContentPane();
				CardLayout cards = (CardLayout) contentPane.getLayout();
				cards.show(contentPane, "Config");
			}
		});
		btnSettings.setBounds(w/2+100, h-100, 100, 50);
		add(btnSettings);
		
		slider = new JSlider(0,40,0);
		slider.setBounds(25,h-300,(w/2)-50,50);
		slider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent arg0) {
                sliderVal.setText(String.valueOf(slider.getValue()*0.25)+" oz");
            }
        });
		add(slider);
		
		sliderVal = new JLabel("0.0 oz");
		sliderVal.setBounds((w/2),h-300,50,50);
		add(sliderVal);
		
		listContainer = new JPanel();
		scrollPane = new JScrollPane(listContainer);
		listContainer.setLayout(new VerticalBagLayout());
		
		scrollPane.setBounds(50, 50, w-100, (h/2)-50);
		add(scrollPane);
		
		JButton btnNewRecipe = new JButton("Save as Recipe");
		btnNewRecipe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(customInstr.size()>0){
					Container contentPane =parent.getContentPane();
					cont.instructions.addAll(customInstr);
					contentPane.add("New Recipe", new NewRecipeScreen(cont, parent,(RecipeType)type.getSelectedItem()));
					CardLayout cl = (CardLayout)contentPane.getLayout();
					cl.show(contentPane, "New Recipe");
					customInstr.clear();
					ingDisplay.clear();
					listContainer.removeAll();
				}
			}
		});
		btnNewRecipe.setBounds(w-525,h-300,200,50);
		add(btnNewRecipe);
		
		type = new JComboBox<RecipeType>(typeChoice);
		type.setBounds(100,h-200 ,(w/8)-50,50);
		add(type);
		
		JButton btnMakeRecipe = new JButton("Make Now");
		btnMakeRecipe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(customInstr.size()>0){
					sendCustom();
				}
			}
		});
		btnMakeRecipe.setBounds(w-225,h-300,200,50);
		add(btnMakeRecipe);
		
		addComponentListener(new ComponentAdapter(){
			public void componentShown(ComponentEvent e){
				refresh();
				revalidate();
				repaint();
			}
	});
		
	}	
	
	public void refresh(){
		if(!DEBUG){
			for(int i=0; i<8; i++){
				String name = "None";
					if(i<cont.buckets.size()){
						Ingredient bucket = cont.buckets.get(i);	
						if(bucket!=null){name = bucket.name;}
						else{name = "None";}
					}
					else{name ="None";}
				valves[i].setText(name);
				if(i>cont.buckets.size()-1){
					valves[i].setEnabled(false);
				}
				else if(cont.buckets.get(i) == null){
					valves[i].setEnabled(false);
				}
				else if(cont.buckets.get(i).Id==-1){
					valves[i].setEnabled(false);
				}
				else{
					valves[i].setEnabled(true);
				}
			}
		}

	}
	
	private Ingredient getBucketContents(int bucketid){
		if(bucketid > cont.buckets.size()){
			return null;
		}
		Ingredient bucket = cont.buckets.get(bucketid);
		return bucket;
	}
	
	private void addInstruction(Ingredient bucket, double amount){
		boolean dup = false;
		for(int i=0; i<customInstr.size();i++){
			Instruction ins = customInstr.get(i);
			if(ins.getIngredient().Id==bucket.Id){
				dup = true;
				ins.setQuantity(ins.getQuantity()+amount);
				JLabel info = ingDisplay.get(i);
				info.setText(ins.getQuantity() + " oz of " + ins.getIngredient().name);
			}
		}
		if(!dup){
			final Instruction step = new Instruction(bucket, amount);
			final JPanel instr = new JPanel();
			JLabel info = new JLabel(step.getQuantity() + " oz of " + step.getIngredient().name);
			info.setFont(new Font(info.getFont().getFontName(), Font.PLAIN, 25));
			JButton remove = new JButton("Remove");
			final JPanel del = instr;
			remove.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int i = 0;
					int index =-1;
					while(i<customInstr.size() && index<0){
						if(customInstr.get(i).getIngredient().Id == step.getIngredient().Id){
							index = i;
						}
						i++;
					}
					customInstr.remove(index);
					ingDisplay.remove(index);
					listContainer.remove(del);
					revalidate();
					repaint();
				}
			});
			instr.add(info);
			instr.add(remove);
			listContainer.add(instr);
			customInstr.add(step);
			ingDisplay.add(info);
			SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                instr.scrollRectToVisible(instr.getBounds());
	            }
	        });
		}
		
		slider.setValue(0);
		sliderVal.setText("0.0 oz");
		
		revalidate();
		repaint();
	}
	
	public void sendCustom(){
		Recipe r = new Recipe("",(RecipeType)type.getSelectedItem(), customInstr);
		cont.makeRecipe(r);
		customInstr.clear();
		ingDisplay.clear();
		listContainer.removeAll();
		revalidate();
		repaint();
	}
}
