package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import buildings.ArcheryRange;
import buildings.Barracks;
import buildings.Building;
import buildings.Farm;
import engine.City;
import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import exceptions.NotEnoughGoldException;
import units.Archer;
import units.Cavalry;
import units.Unit;

public class CityView extends JLabel implements ActionListener {
	private JComboBox<String> cities;
	private JComboBox<String> eBuildings;
	private JComboBox<String> mBuildings;
	
	private JButton newBuilding;
	private JButton upgradeEBuilding;
	private JButton upgradeMBuilding;

	// Defending army part
	private JComboBox<String> units;
	private JButton initArmy;
	private JButton recruitUnit;

	private MainView parentView;
	
	public CityView(MainView parentView) {
		this.parentView = parentView;
		this.setLayout(new GridLayout(10, 1));
		this.setBounds(1130, 20, 200, 450);
		this.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Controlled Cities"));

		cities = new JComboBox<String>();
		cities.addActionListener(this);
		this.add(cities);

		eBuildings = new JComboBox<String>();
		eBuildings.addActionListener(this);
		this.add(eBuildings);

		mBuildings = new JComboBox<String>();
		mBuildings.addActionListener(this);
		this.add(mBuildings);

		newBuilding = new JButton("New Building");
		newBuilding.addActionListener(this);
		this.add(newBuilding);

		upgradeEBuilding = new JButton("Upgrade E Building");
		upgradeEBuilding.addActionListener(this);
		this.add(upgradeEBuilding);

		upgradeMBuilding = new JButton("Upgrade M Building");
		upgradeMBuilding.addActionListener(this);
		this.add(upgradeMBuilding);

		this.add(new JLabel("Defending Army"));

		units = new JComboBox<String>();
		units.addActionListener(this);
		this.add(units);

		initArmy = new JButton("Init Army");
		initArmy.addActionListener(this);
		this.add(initArmy);

		recruitUnit = new JButton("Recruit Unit");
		recruitUnit.addActionListener(this);
		this.add(recruitUnit);

		updateComboBoxes();
	}

	public void updateComboBoxes() {
		String[] cNames = new String[parentView.getGame().
		                             getPlayer().getControlledCities().size()];
		for (int i = 0; i < cNames.length; i++) {
			cNames[i] = parentView.getGame().getPlayer().getControlledCities().get(i).getName();
		}
		cities.removeAllItems();
		cities.setModel(new DefaultComboBoxModel<String>(cNames));

		updateEMBuildings(0);
		updateDefArmyUnits(0);
	}

	private void updateDefArmyUnits(int cityIndex) {
		String[] u = new String[parentView.getGame().getPlayer().getControlledCities().get(cityIndex).getDefendingArmy()
				.getUnits().size()];

		for (int i = 0; i < u.length; i++) {
			int level = parentView.getGame().getPlayer().getControlledCities().get(cityIndex).getDefendingArmy()
					.getUnits().get(i).getLevel();
			if (parentView.getGame().getPlayer().getControlledCities().get(cityIndex).getDefendingArmy().getUnits()
					.get(i) instanceof Archer)
				u[i] = "Archer - " + level;
			else if (parentView.getGame().getPlayer().getControlledCities().get(cityIndex).getDefendingArmy().getUnits()
					.get(i) instanceof Cavalry)
				u[i] = "Cavalry - " + level;
			else
				u[i] = "Infantry - " + level;
		}
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(u);
		units.setModel(model);
	}

	private void updateEMBuildings(int cityIndex) {
		String[] e = new String[parentView.getGame().getPlayer().getControlledCities()
		                        .get(cityIndex)
				.getEconomicalBuildings().size()];
		for (int i = 0; i < e.length; i++) {
			if (parentView.getGame().getPlayer().getControlledCities()
					.get(cityIndex).getEconomicalBuildings()
					.get(i) instanceof Farm)
				e[i] = "Farm";
			else
				e[i] = "Market";
		}
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(e);
		eBuildings.setModel(model);

		String[] m = new String[parentView.getGame().getPlayer().getControlledCities().get(cityIndex)
				.getMilitaryBuildings().size()];
		for (int i = 0; i < m.length; i++) {
			if (parentView.getGame().getPlayer().getControlledCities().get(cityIndex).getMilitaryBuildings()
					.get(i) instanceof ArcheryRange)
				m[i] = "ArcheryRange";
			else if (parentView.getGame().getPlayer().getControlledCities().get(cityIndex).getMilitaryBuildings()
					.get(i) instanceof Barracks)
				m[i] = "Barracks";
			else
				m[i] = "Stable";
		}
		mBuildings.removeAllItems();
		for (int i = 0; i < m.length; i++) {
			mBuildings.addItem(m[i]);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == newBuilding) {
			String[] types = { "ArcheryRange", "Barracks", "Stable", "Farm", "Market" };
			JComboBox<String> typesCombo = new JComboBox<String>(types);

			JOptionPane.showMessageDialog(null, typesCombo, "Type Selection", 
					JOptionPane.QUESTION_MESSAGE);

			try {
				parentView.getGame().getPlayer().build
					((String) typesCombo.getSelectedItem(),
						(String) cities.getSelectedItem());
				parentView.updateAllGameElements();
			} catch (NotEnoughGoldException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == upgradeEBuilding) {
			int cIndex = cities.getSelectedIndex();
			int bIndex = eBuildings.getSelectedIndex();
			Building b = parentView.getGame().getPlayer().getControlledCities()
					.get(cIndex).getEconomicalBuildings().get(bIndex);
			try {
				parentView.getGame().getPlayer().upgradeBuilding(b);
				parentView.updateAllGameElements();
			} catch (NotEnoughGoldException | BuildingInCoolDownException | MaxLevelException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == upgradeMBuilding) {
			int cIndex = cities.getSelectedIndex();
			int bIndex = mBuildings.getSelectedIndex();
			Building b = parentView.getGame().getPlayer().getControlledCities().get(cIndex).getMilitaryBuildings()
					.get(bIndex);
			try {
				parentView.getGame().getPlayer().upgradeBuilding(b);
				parentView.updateAllGameElements();
			} catch (NotEnoughGoldException | BuildingInCoolDownException | MaxLevelException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == cities || e.getSource() == eBuildings 
				|| e.getSource() == mBuildings) {
			parentView.updateCityBuildingInfo();
		} else if (e.getSource() == units) {
			int cityIndex = cities.getSelectedIndex();
			int unitIndex = units.getSelectedIndex();
			Unit unit = parentView.getGame().getPlayer().getControlledCities().get(cityIndex).getDefendingArmy()
					.getUnits().get(unitIndex);
			parentView.updateUnitInfo(unit);
		} else if (e.getSource() == initArmy) {
			int cityIndex = cities.getSelectedIndex();
			City city = parentView.getGame().getPlayer().getControlledCities().get(cityIndex);

			int unitIndex = units.getSelectedIndex();
			Unit unit = parentView.getGame().getPlayer().getControlledCities().get(cityIndex).getDefendingArmy()
					.getUnits().get(unitIndex);

			parentView.getGame().getPlayer().initiateArmy(city, unit);
			parentView.updateAllGameElements();
		} else if (e.getSource() == recruitUnit) {
			String[] types = { "Archer", "Cavalry", "Infantry" };
			JComboBox<String> typesCombo = new JComboBox<String>(types);

			JOptionPane.showMessageDialog(null, typesCombo, "Type Selection", JOptionPane.QUESTION_MESSAGE);

			try {
				parentView.getGame().getPlayer().recruitUnit((String) typesCombo.getSelectedItem(),
						(String) cities.getSelectedItem());
				parentView.updateAllGameElements();
			} catch (BuildingInCoolDownException | MaxRecruitedException | NotEnoughGoldException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public JComboBox<String> getCities() {
		return cities;
	}

	public JComboBox<String> geteBuildings() {
		return eBuildings;
	}

	public JComboBox<String> getmBuildings() {
		return mBuildings;
	}

}
