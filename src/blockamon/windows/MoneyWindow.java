package blockamon.windows;

import blockamon.objects.Player;

import javax.swing.*;
import java.awt.*;

/**
 * User: Jack's Computer
 * Date: 11/9/2014
 * Time: 2:34 PM
 */
public class MoneyWindow extends JFrame {
    private JLabel _moneyLabel;
    private JPanel _moneyPanel;
    private Player _player;

    public MoneyWindow(Player player) {
        _player = player;
        this.setSize(200, 100);
        createMoneyPanel();
    }

    private void createMoneyPanel() {
        _moneyPanel = new JPanel();
        _moneyPanel.setSize(super.getSize());
        _moneyLabel = new JLabel();
        _moneyLabel.setSize(_moneyPanel.getSize());
        updateMoneyLabel();
        _moneyPanel.add(_moneyLabel);
        super.add(_moneyPanel);
    }

    private void updateMoneyLabel() {
        _moneyLabel.setText("Your Money: " + _player.getMoney());
    }

    public void update(Graphics g) {
        updateMoneyLabel();
        super.update(g);
    }
}
