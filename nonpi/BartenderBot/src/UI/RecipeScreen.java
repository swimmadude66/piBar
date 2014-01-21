package UI;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import Managers.Control;
import Models.Instruction;
import Models.Recipe;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;

import sun.awt.VerticalBagLayout;

public class RecipeScreen extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6824035303859422415L;
	final Control cont;
	final JFrame parent;
	JScrollPane scrollPane;
	JPanel listContainer;
	JList<JButton> list;
	List<Recipe> recipes;
	
	public int w;
	public int h;
	
	/**
	 * Create the panel.
	 */
	public RecipeScreen(Control c, JFrame p) {
		cont = c;
		parent = p;
		
		w = parent.getWidth();
		h = parent.getHeight();
		recipes = new ArrayList<Recipe>();
		this.setName("Recipes");
		
		setLayout(null);
		
		JButton btnCustom = new JButton("Custom");
		btnCustom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Container contentPane = parent.getContentPane();
				CardLayout cards = (CardLayout) contentPane.getLayout();
				cards.show(contentPane, "Custom");
			}
		});
		btnCustom.setBounds(w/2-200, h-100, 100, 50);
		add(btnCustom);
		
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
		
		listContainer = new JPanel(new VerticalBagLayout());
		listContainer.setLayout(new VerticalBagLayout());
		
		scrollPane = new JScrollPane(listContainer);
		scrollPane.setBounds(50, 50, w-100, (h/2)-50);
		add(scrollPane);
				
		showRecipes();
		addComponentListener(new ComponentAdapter(){
			public void componentShown(ComponentEvent e){
				showRecipes();
				revalidate();
				repaint();
			}
		});
	}
	
	private void showRecipes(){
		filterRecipes();
		listContainer.removeAll();
		for(Recipe r : recipes){
			final JPanel recPanel = new JPanel();
			JLabel name = new JLabel(r.name);
			name.setFont(new Font(name.getFont().getFontName(), Font.PLAIN, 25));
			recPanel.setBorder(BorderFactory.createRaisedBevelBorder());
			recPanel.add(name);
			final Recipe rec = r;
			recPanel.addMouseListener(new MouseListener(){
				public void mouseClicked(MouseEvent arg0) {
					cont.makeRecipe(rec);
					System.out.println("clicked " + rec.name);
				}
				public void mouseEntered(MouseEvent e) {/* TODO Auto-generated method stub*/}
				public void mouseExited(MouseEvent e) {/* TODO Auto-generated method stub*/}
				public void mousePressed(MouseEvent e) {/* TODO Auto-generated method stub*/}
				public void mouseReleased(MouseEvent e) {/* TODO Auto-generated method stub*/}				
			});
			listContainer.add(recPanel);
			SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                recPanel.scrollRectToVisible(recPanel.getBounds());
	            }
	        });		
		}
	}
	
	public void filterRecipes(){
		recipes.clear();
		List<Recipe> recs = cont.recipes;
		List<Integer> bucketIds = cont.bucketIds;
		
		for(Recipe r : recs){
			if(r!=null){
				List<Integer> required = new ArrayList<Integer>();
				for(Instruction i : r.instructions){
					required.add(i.getIngredient().Id);
				}
				if(bucketIds.containsAll(required)){
					recipes.add(r);
				}
			}
		}
		
	}

}
