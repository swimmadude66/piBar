package UI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Managers.Control;
import java.awt.CardLayout;


public class BartenderFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -318681067289236938L;
	private JPanel contentPane;
	private Control cont;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BartenderFrame frame = new BartenderFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BartenderFrame() {
		cont = new Control();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setVisible(true);
		System.out.println(""+getSize().width+" x " + getSize().height);
		setBounds(0, 0, getSize().width, getSize().height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		contentPane.add("Recipes", new RecipeScreen(cont, this));
		contentPane.add("Custom", new CustomScreen(cont, this));
		contentPane.add("Config", new ConfigScreen(cont, this));
				
	}
	
	public JPanel getContentPane(){
		return contentPane;
	}
}
