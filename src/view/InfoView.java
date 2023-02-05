package view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class InfoView extends JLabel {
	
	private MainView parentView;
	
	private JTextArea cityInfo;
	private JTextArea buildInfo;
	private JTextArea armyInfo;
	private JTextArea unitInfo;

	public InfoView(MainView parentView) {
		this.parentView = parentView;
		
		this.setLayout(new GridLayout(1, 4));
		this.setBounds(20, 520, 1310, 200);
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Info"));

		cityInfo = new JTextArea();
		cityInfo.setEditable(false);
		JScrollPane scroll1 = new JScrollPane(cityInfo, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "City Info"));
		this.add(scroll1);

		buildInfo = new JTextArea();
		buildInfo.setEditable(false);
		JScrollPane scroll2 = new JScrollPane(buildInfo, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll2.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Building Info"));
		this.add(scroll2);

		armyInfo = new JTextArea();
		armyInfo.setEditable(false);
		JScrollPane scroll3 = new JScrollPane(armyInfo, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Army Info"));
		this.add(scroll3);

		unitInfo = new JTextArea();
		unitInfo.setEditable(false);
		JScrollPane scroll4 = new JScrollPane(unitInfo, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Unit Info"));
		this.add(scroll4);
	}

	public JTextArea getCityInfo() {
		return cityInfo;
	}

	public JTextArea getBuildInfo() {
		return buildInfo;
	}

	public JTextArea getArmyInfo() {
		return armyInfo;
	}

	public JTextArea getUnitInfo() {
		return unitInfo;
	}

}
