package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import units.Archer;
import units.Army;
import units.Cavalry;
import units.Unit;

public class ArmyView extends JLabel implements ActionListener {
	private JComboBox<String> armies;
	private JComboBox<String> units;
	private JButton targetCity;
	private JButton map;
	
	private MainView parentView;
	
	public ArmyView(MainView parentView) {
		this.parentView = parentView;
		this.setLayout(new GridLayout(4, 1));
		this.setBounds(20, 270, 210, 200);
		this.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Controlled Armies"));

		armies = new JComboBox<String>();
		armies.addActionListener(this);
		this.add(armies);

		units = new JComboBox<String>();
		units.addActionListener(this);
		this.add(units);

		targetCity = new JButton("Target City");
		targetCity.addActionListener(this);
		this.add(targetCity);
		
		map = new JButton("World Map");
		map.addActionListener(this);
		this.add(map);
	}

	public void updateComboBoxes() {
		if (parentView.getGame().getPlayer().getControlledArmies().size() > 0) {
			String[] aNames = new String[parentView.getGame().getPlayer().getControlledArmies().size()];
			for (int i = 0; i < aNames.length; i++) {
				aNames[i] = parentView.getGame().getPlayer().getControlledArmies().get(i).getCurrentLocation()
						+ " Army";
			}
			armies.removeAllItems();
			armies.setModel(new DefaultComboBoxModel<String>(aNames));

			updateArmyUnits(0);
		}
	}

	private void updateArmyUnits(int armyIndex) {
		String[] u = new String[parentView.getGame().getPlayer().getControlledArmies().get(armyIndex).getUnits()
				.size()];

		for (int i = 0; i < u.length; i++) {
			int level = parentView.getGame().getPlayer().getControlledArmies().get(armyIndex).getUnits().get(i)
					.getLevel();
			if (parentView.getGame().getPlayer().getControlledArmies().get(armyIndex).getUnits()
					.get(i) instanceof Archer)
				u[i] = "Archer - " + level;
			else if (parentView.getGame().getPlayer().getControlledArmies().get(armyIndex).getUnits()
					.get(i) instanceof Cavalry)
				u[i] = "Cavalry - " + level;
			else
				u[i] = "Infantry - " + level;
		}
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(u);
		units.setModel(model);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == armies) {
			int armyIndex = armies.getSelectedIndex();
			if (armyIndex >= 0) {
				Army a = parentView.getGame().getPlayer().getControlledArmies().get(armyIndex);
				parentView.updateArmyInfo(a);
			}
		} else if (e.getSource() == units) {
			int armyIndex = armies.getSelectedIndex();
			Army a = parentView.getGame().getPlayer().getControlledArmies().get(armyIndex);

			int unitIndex = units.getSelectedIndex();
			Unit u = a.getUnits().get(unitIndex);

			parentView.updateUnitInfo(u);
		} else if (e.getSource() == targetCity) {
			int armyIndex = armies.getSelectedIndex();
			Army a = parentView.getGame().getPlayer().getControlledArmies().get(armyIndex);

			String[] x = new String[3 - parentView.getGame().getPlayer().getControlledCities().size()];
			int j = 0;
			for (int i = 0; i < parentView.getGame().getAvailableCities().size(); i++) {
				if (!parentView.getGame().getAvailableCities().get(i).getName().equals(a.getCurrentLocation())) {
					x[j] = parentView.getGame().getAvailableCities().get(i).getName();
					j++;
				}
			}

			JComboBox<String> citiesCombo = new JComboBox<String>(x);
			JOptionPane.showMessageDialog(null, citiesCombo, "City Selection", JOptionPane.QUESTION_MESSAGE);

			parentView.getGame().targetCity(a, (String) citiesCombo.getSelectedItem());
		} else if(e.getSource() == map){
			JFrame f = new JFrame();
			JLabel world = new JLabel("");
		    world.setBounds(0, 0, 800, 600);
		    Image img1 = new ImageIcon(this.getClass().getResource("/worldMap.jpg")).getImage();
		    Image img = img1.getScaledInstance(world.getWidth(), world.getHeight(), Image.SCALE_SMOOTH);
		    world.setIcon(new ImageIcon(img));
		    f.getContentPane().add(world); 
	        f.setBounds(300, 100, world.getWidth(), world.getHeight());
		    f.setVisible(true);
		}

	}

}
