package UI;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;

import Managers.Control;
import Models.Ingredient;
import Models.IngredientType;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.JTextField;


public class ConfigScreen extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1947291809157762142L;
	final Control cont;
	final JFrame parent;
	public int w;
	public int h;
	
	final JComboBox<Ingredient>[] buckets;
	final JComboBox<IngredientType> type;
	final JLabel[] titles;
	IngredientType[] typeChoice ={IngredientType.LIQUOR,IngredientType.LIQUEUR,IngredientType.MIXER};
	
	public JLabel successLabel;
	private JTextField txtIngredient;
	/**
	 * Create the panel.
	 */
	@SuppressWarnings("unchecked")
	public ConfigScreen(Control c, JFrame p) {
		cont = c;
		parent = p;
		w = parent.getWidth();
		h = parent.getHeight();
		this.setName("Config");
		
		List<Ingredient> choices = cont.getIngredients();
		
		buckets = new JComboBox[8];
		titles = new JLabel[8];
		for(int i=0; i<8; i++){
			Ingredient[] ings = new Ingredient[choices.size()];
			JComboBox<Ingredient>bucket = new JComboBox<Ingredient>(choices.toArray(ings));
			bucket.setBounds((i*w/8)+25,h/5,(w/8)-50,20);
			buckets[i] = bucket;
			if(!choices.isEmpty()){
				int selectIndex = 0;
				if(cont.buckets.size()>i){
					while(choices.get(selectIndex).Id!=cont.buckets.get(i).Id){
						selectIndex++;
					}
				}
				bucket.setSelectedIndex(selectIndex);
			}
			add(bucket);
			JLabel title = new JLabel("Bucket "+ (i+1));
			title.setHorizontalAlignment(SwingConstants.CENTER);
			title.setBounds((i*w/8)+25,(h/5)-45,(w/8)-50,20);
			add(title);
		}
		
				
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<Ingredient> config = new ArrayList<Ingredient>();
				for(JComboBox<Ingredient> b : buckets){
					config.add((Ingredient)b.getSelectedItem());
				} 
				cont.changeConfig(config);
				cont.saveConfig();
				successLabel.setText("Configuration updated!");
			}
		});
		btnSave.setBounds((w/2)-50, (h)-200, 100, 50);
		add(btnSave);
		
		successLabel = new JLabel("");
		successLabel.setHorizontalAlignment(SwingConstants.CENTER);
		successLabel.setBounds((w/2)-100, (h/2)+50, 200, 25);
		add(successLabel);
		
		
		JButton btnExit = new JButton("");
		btnExit.setBackground(new Color(240,240,240));
		btnExit.setIcon(null);
		btnExit.setBounds(w-100, h-100, 100, 100);
		btnExit.setBorder(null);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(ABORT);
			}
		});
		setLayout(null);
		add(btnExit);
		
		JButton btnRecipes = new JButton("Recipes");
		btnRecipes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				successLabel.setText("");
				
				Container contentPane = parent.getContentPane();
				CardLayout cards = (CardLayout) contentPane.getLayout();
				cards.show(contentPane, "Recipes");
			}
		});
		btnRecipes.setBounds(w/2-200, h-100, 100, 50);
		add(btnRecipes);
		
		JButton btnCustom = new JButton("Custom");
		btnCustom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				successLabel.setText("");
				
				Container contentPane = parent.getContentPane();
				CardLayout cards = (CardLayout) contentPane.getLayout();
				cards.show(contentPane, "Custom");
			}
		});
		btnCustom.setBounds((w/2)+100, h-100, 100, 50);
		add(btnCustom);
		
		type = new JComboBox<IngredientType>(typeChoice);
		type.setBounds((w/2)-250,(h/2)-50,150,20);
		add(type);
		
		
		txtIngredient = new JTextField();
		txtIngredient.setBounds((w/2)-100, (h/2)-50, 200, 25);
		add(txtIngredient);
		txtIngredient.setColumns(100);
		
		JButton btnAddIngredient = new JButton("Add Ingredient");
		btnAddIngredient.setBounds((w/2)+150, (h/2)-50, 200, 25);
		btnAddIngredient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cont.addIngredient(new Ingredient(txtIngredient.getText(),(IngredientType)type.getSelectedItem(),cont.getIngredients().size()-1));
				txtIngredient.setText("");
			}
		});
		add(btnAddIngredient);
		
	}
}
