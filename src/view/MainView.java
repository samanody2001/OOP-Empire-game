package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import buildings.EconomicBuilding;
import buildings.MilitaryBuilding;
import engine.City;
import engine.Game;
import units.Army;
import units.Unit;

public class MainView extends JFrame {

	private Game game;

	private WelcomeView welcomeView;
	private GeneralInfoView generalInfoView;
	private CityView cityView;
	private ArmyView armyView;
	private InfoView infoView;

	public MainView() {
		welcomeView = new WelcomeView(this);
		this.getContentPane().add(welcomeView);
		
		this.setSize(1360, 780);
		this.setResizable(false);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setVisible(true);
		this.getContentPane().setLayout(null);
		
	}
	
	public static void main(String[] args) {
		new MainView();
	}

	public void changeToGameMode(String playerName, String cityName) {
		welcomeView.setVisible(false);

		try {
			game = new Game(playerName, cityName);
		} catch (IOException e) {
			JOptionPane.showConfirmDialog(this, "Error");
		}
		
		generalInfoView = new GeneralInfoView(this);
		this.getContentPane().add(generalInfoView);

		cityView = new CityView(this);
		this.getContentPane().add(cityView);

		infoView = new InfoView(this);
		this.getContentPane().add(infoView);

		armyView = new ArmyView(this);
		this.getContentPane().add(armyView);


		updateAllGameElements();

	}

	public void updateAllGameElements() {
		generalInfoView.updateLabels();
		
		cityView.updateComboBoxes();
		
		armyView.updateComboBoxes();
	}

	public void updateCityBuildingInfo() {
		int cityIndex = cityView.getCities().getSelectedIndex();
		if (cityIndex >= 0) {
			City c = game.getPlayer().getControlledCities().get(cityIndex);
			String cityInfo = "Name : " + c.getName() + "\n" + "Is Under Seige : " + c.isUnderSiege() + "\n"
					+ "Truns Under Seige : " + c.getTurnsUnderSiege() + "\n";
			if (c.getDefendingArmy() != null) {
				Army a = c.getDefendingArmy();
				cityInfo += "Defending Army : " + "\n" + "Current Location : " + a.getCurrentLocation() + "\n"
						+ "Target Location : " + a.getTarget() + "\n" + "Distance to Target : "
						+ a.getDistancetoTarget() + "\n" + "Food Needed : " + a.foodNeeded() + "\n"
						+ "Current Status : " + a.getCurrentStatus() + "\n";
			}
			infoView.getCityInfo().setText(cityInfo);

			int eBIndex = cityView.geteBuildings().getSelectedIndex();
			String emInfo = "";
			if (eBIndex >= 0) {
				EconomicBuilding e = c.getEconomicalBuildings().get(eBIndex);
				emInfo = "Economical Building : \n" + "Cost : " + e.getCost() + "\n" + "Level : " + e.getLevel() + "\n"
						+ "Upgrade Level : " + e.getUpgradeCost() + "\n" + "Cool Down : " + e.isCoolDown() + "\n"
						+ "........................................\n";
			}
			int mBIndex = cityView.getmBuildings().getSelectedIndex();
			if (mBIndex >= 0) {
				MilitaryBuilding e = c.getMilitaryBuildings().get(mBIndex);
				emInfo += "Military Building : \n" + "Cost : " + e.getCost() + "\n" + "Level : " + e.getLevel() + "\n"
						+ "Upgrade Level : " + e.getUpgradeCost() + "\n" + "Cool Down : " + e.isCoolDown() + "\n"
						+ "Recruitment Cost : " + e.getRecruitmentCost() + "\n" + "Max Recruit : " + e.getMaxRecruit();
			}
			infoView.getBuildInfo().setText(emInfo);
		}
	}

	public void updateUnitInfo(Unit u) {
		String uInfo = "Soldier Count : " + u.getCurrentSoldierCount() + "\n" + "Max. Soldier Count : "
				+ u.getMaxSoldierCount() + "\n" + "Level : " + u.getLevel() + "\n";
		infoView.getUnitInfo().setText(uInfo);
	}

	public void updateArmyInfo(Army a) {
		String armyInfo = "Army : " + "\n" + "Current Location : " + a.getCurrentLocation() + "\n"
				+ "Target Location : " + a.getTarget() + "\n" + "Distance to Target : " + a.getDistancetoTarget() + "\n"
				+ "Food Needed : " + a.foodNeeded() + "\n" + "Current Status : " + a.getCurrentStatus() + "\n";
		infoView.getArmyInfo().setText(armyInfo);
	}

	public Game getGame() {
		return game;
	}

}
