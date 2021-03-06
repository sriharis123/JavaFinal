package graphics;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import cards.Amplifier;
import cards.Amplifier.AmpEnum;
import main.Game;

/**
 * This is the panel where amplifiers are put into play, they do nothing until
 * placed here
 * 
 * @author Santiago Callegari(appearance), Luke
 */
public class AmpPanel extends JPanel implements ActionListener {

	private JButton[] buttons = new JButton[5];
	private JButton sac;
	private boolean sacActive = false;

	public AmpPanel() {
		setBackground(new Color(0, 0, 0, 0));
		/**
		 * creates and places the buttons
		 */
		buttons[0] = new JButton("Open");
		buttons[0].setActionCommand("0");
		buttons[0].addActionListener(this);
		buttons[1] = new JButton("Open");
		buttons[1].setActionCommand("1");
		buttons[1].addActionListener(this);
		buttons[2] = new JButton("Open");
		buttons[2].setActionCommand("2");
		buttons[2].addActionListener(this);
		buttons[3] = new JButton("Open");
		buttons[3].setActionCommand("3");
		buttons[3].addActionListener(this);
		buttons[4] = new JButton("Open");
		buttons[4].setActionCommand("4");
		buttons[4].addActionListener(this);
		sac = new JButton("Sacrifice");
		sac.setActionCommand("Sac");
		sac.addActionListener(this);

		add(buttons[0]);
		add(buttons[1]);
		add(buttons[2]);
		add(buttons[3]);
		add(buttons[4]);
		add(sac);
		setVisible(true);
	}

	/**
	 * The action
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String a = e.getActionCommand();
		if (a.equals("Sac")) {
			int x = 0;
			for (int i = 0; i < 5; i++) {
				if (Game.game.getAmpArray()[i] != null) {
					x++;
					Game.game.addToGraveyard(Game.game.getAmpArray()[i]);
				}
				if (x >= 2) {
					break;
				}
			}
		} else if (Game.game.getAmpAt(Integer.parseInt(a)) == null) {
			Game.game.addToPlayerActionQueue(new Amplifier(AmpEnum.NONE));
		} else {
			((JButton) e.getSource()).setText(Game.game.getAmpAt(Integer.parseInt(a)).getName());
			Game.game.addToPlayerActionQueue(Game.game.getAmpAt(Integer.parseInt(a)));
			Game.game.getGameMenu().getInfo().newDisplay(Game.game.getAmpAt(Integer.parseInt(a)));
		}
	}

	public void refresh() {
		for (int i = 0; i < 5; i++) {
			if (Game.game.getAmpArray()[i] == null) {
				buttons[i].setText("Open");
			} else {
				buttons[i].setText(Game.game.getAmpArray()[i].getName());
			}
		}
		if (Game.game.getAmpArray()[0] != null && Game.game.getAmpArray()[1] != null
				&& Game.game.getAmpArray()[2] != null && Game.game.getAmpArray()[3] != null
				&& Game.game.getAmpArray()[4] != null && Game.game.getAmpArray()[5] != null) {
			sac.setEnabled(true);
		} else {
			sac.setEnabled(false);
		}
	}
}
