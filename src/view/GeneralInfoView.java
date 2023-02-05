package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

public class GeneralInfoView extends JLabel implements ActionListener {

	private MainView parentView;
	
	private JLabel playerName;
	private JLabel gold;
	private JLabel food;
	private JLabel turn;
	private JButton endTurn;

	public GeneralInfoView(MainView parentView) {
		this.parentView = parentView;

		this.setLayout(new GridLayout(5, 1));
		this.setBounds(20, 20, 210, 230);
		this.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black), "General Info"));

		playerName = new JLabel();
		this.add(playerName);

		gold = new JLabel();
		this.add(gold);

		food = new JLabel();
		this.add(food);

		turn = new JLabel();
		this.add(turn);

		endTurn = new JButton("End Turn");
		endTurn.addActionListener(this);
		this.add(endTurn);

		updateLabels();
	}

	public void updateLabels() {
		playerName.setText("Player Name : " + parentView.getGame().getPlayer().getName());
		gold.setText("Gold : " + parentView.getGame().getPlayer().getTreasury());
		food.setText("Food : " + parentView.getGame().getPlayer().getFood());
		turn.setText("Turn : " + parentView.getGame().getCurrentTurnCount());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == endTurn) {
			parentView.getGame().endTurn();
			parentView.updateAllGameElements();
		}

	}

}
