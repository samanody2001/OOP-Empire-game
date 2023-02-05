package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class WelcomeView extends JPanel implements ActionListener{
	
	private MainView parentView;
	
	private ButtonGroup G1;
	private JRadioButton jrb1;
	private JRadioButton jrb2;
	private JRadioButton jrb3;
	
	private JLabel cairo;
	private JLabel rome;
	private JLabel sparta;
	
	private LineBorder line;
	private JTextField name;
	private JButton start;
	
	public WelcomeView(MainView parentView){
		
		this.parentView = parentView;
		this.setLayout(null);
		
		JLabel l1 = new JLabel("Enter your name:");
		l1.setFont(new Font("Tempus Sans ITC", Font.BOLD, 32));
		l1.setBounds(130, 30, 1000, 100);
		this.add(l1);
		
		name = new JTextField();
		name.setBounds(420, 55, 300, 50);
		name.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 22));
		this.add(name);
		
		JLabel l2 = new JLabel("Select a city:");
		l2.setFont(new Font("Tempus Sans ITC", Font.BOLD, 32));
		l2.setBounds(130, 110, 1000, 100);
		this.add(l2);
		
		cairo = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/cairo.png")).getImage();
		cairo.setIcon(new ImageIcon(img));
		cairo.setBounds(130, 260, 300, 300);
		this.add(cairo);
		
		rome = new JLabel("");
		Image img1 = new ImageIcon(this.getClass().getResource("/rome.png")).getImage();
		rome.setIcon(new ImageIcon(img1));
		rome.setBounds(520, 260, 300, 300);
		this.add(rome);
		
		sparta = new JLabel("");
		Image img2 = new ImageIcon(this.getClass().getResource("/sparta.png")).getImage();
		sparta.setIcon(new ImageIcon(img2));
		sparta.setBounds(910, 260, 300, 300);
		this.add(sparta);
		
		jrb1 = new JRadioButton("Cairo");
		jrb1.setBounds(240, 210, 200, 50);
		jrb1.setFont(new Font("Tempus Sans ITC", Font.BOLD, 22));
		jrb1.setOpaque(false);
		jrb1.addActionListener(this);
		this.add(jrb1);
		
		jrb2 = new JRadioButton("Rome");
		jrb2.setOpaque(false);
		jrb2.setFont(new Font("Tempus Sans ITC", Font.BOLD, 22));
		jrb2.setBounds(620, 210, 200, 50);
		jrb2.addActionListener(this);
		this.add(jrb2);
		
		jrb3 = new JRadioButton("Sparta");
		jrb3.setOpaque(false);
		jrb3.setFont(new Font("Tempus Sans ITC", Font.BOLD, 22));
		jrb3.setBounds(1010, 210, 200, 50);
		jrb3.addActionListener(this);
		this.add(jrb3);
	    
	    G1 = new ButtonGroup();
	    G1.add(jrb1);
        G1.add(jrb2);
        G1.add(jrb3);
		
        start = new JButton("Start game");
        start.setBounds(570, 600, 200, 50);
        start.setFont(new Font("Tempus Sans ITC", Font.BOLD, 22));
        start.addActionListener(this);
        this.add(start);
        
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		line = new LineBorder(Color.BLUE, 2, true);
		String cityName = "";
		if(e.getSource() == jrb1){
			cairo.setBorder(line);
			rome.setBorder(null);
			sparta.setBorder(null);
			cityName = "Cairo";
		} else if(e.getSource() == jrb2){
			rome.setBorder(line);
			cairo.setBorder(null);
			sparta.setBorder(null);
			cityName = "Rome";
		} else{
			sparta.setBorder(line);
			cairo.setBorder(null);
			rome.setBorder(null);
			cityName = "Sparta";
		}
		if(e.getSource() == start){
			if (name.getText().equals("")){
				JOptionPane.showMessageDialog(this, "Please enter a name", "Error", JOptionPane.ERROR_MESSAGE);
			} else if(G1.getSelection() == null){
				JOptionPane.showMessageDialog(this, "Please select a city", "Error", JOptionPane.ERROR_MESSAGE); 
			} else{
				String playerName = name.getText();
				parentView.changeToGameMode(playerName, cityName);
			}
		}
	}	
}
