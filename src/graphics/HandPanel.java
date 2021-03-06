package graphics;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import cards.Card;
import main.Game;

/**
 * 
 * 
 * @author Santiago Callegari, Luke
 *
 */
public class HandPanel extends JPanel {
	private JButton[] buttons = new JButton[6];

	/**
	 * 
	 * makes buttons that when pushed remove the card(if any) from the hand
	 * array and the use it if a keystroke was pressed otherwise it send info
	 * the infopanel
	 */
	public HandPanel() {
		setBackground(new Color(0, 0, 0, 0));
		setLayout(null);

		buttons[0] = new JButton("Open Slot");
		buttons[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Game.game.getHand().get(0) != null) {
					Card c = Game.game.getHand().get(0);
					Game.game.addToPlayerActionQueue(Game.game.getHand().get(0));
					Game.game.getGameMenu().getInfo().newDisplay(Game.game.getHand().get(0));
				}
			}
		});
		buttons[0].setBounds(82, 11, 122, 177);
		add(buttons[0]);

		buttons[1] = new JButton("Open Slot");
		buttons[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Game.game.getHand().get(1) != null) {
					Game.game.addToPlayerActionQueue(Game.game.getHand().get(1));
					Game.game.getGameMenu().getInfo().newDisplay(Game.game.getHand().get(1));
				}
			}
		});
		buttons[1].setBounds(214, 11, 122, 177);
		add(buttons[1]);

		buttons[2] = new JButton("Open Slot");
		buttons[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Game.game.getHand().get(2) != null) {
					Game.game.addToPlayerActionQueue(Game.game.getHand().get(2));
					Game.game.getGameMenu().getInfo().newDisplay(Game.game.getHand().get(2));
				}
			}
		});
		buttons[2].setBounds(346, 11, 122, 177);
		add(buttons[2]);

		buttons[3] = new JButton("Open Slot");
		buttons[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Game.game.getHand().get(3) != null) {
					Game.game.getGameMenu().getInfo().newDisplay(Game.game.getHand().get(3));
					Game.game.addToPlayerActionQueue(Game.game.getHand().get(3));
				}
			}
		});
		buttons[3].setBounds(478, 11, 122, 177);
		add(buttons[3]);

		buttons[4] = new JButton("Open Slot");
		buttons[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Game.game.getHand().get(4) != null) {
					Game.game.addToPlayerActionQueue(Game.game.getHand().get(4));
					Game.game.getGameMenu().getInfo().newDisplay(Game.game.getHand().get(4));
				}
			}
		});
		buttons[4].setBounds(610, 11, 122, 177);
		add(buttons[4]);

		buttons[5] = new JButton("Open Slot");
		buttons[5].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Game.game.getHand().get(5) != null) {
					Game.game.addToPlayerActionQueue(Game.game.getHand().get(5));
					Game.game.getGameMenu().getInfo().newDisplay(Game.game.getHand().get(5));
				}
			}
		});
		buttons[5].setBounds(742, 11, 122, 177);
		add(buttons[5]);
	}

	/**
	 * makes sure cards shown are always accurate
	 */
	public void refresh() {
		for (int i = 0; i < 5; i++) {
			try {
				buttons[i].setText(Game.game.getHand().get(i).getFormattedName());
				Game.game.getHand().get(i).updateDescription();
			} catch (Exception e) {
				buttons[i].setText("Open Slot");
			}
		}
	}

}
