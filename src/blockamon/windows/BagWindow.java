package blockamon.windows;

import blockamon.io.IPrinter;
import blockamon.io.PrintManager;
import blockamon.items.Item;
import blockamon.objects.Player;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * User: Jack's Computer
 * Date: 11/9/2014
 * Time: 3:25 PM
 */
public class BagWindow extends JFrame {

    private IPrinter _printer = PrintManager.getPrinter(this);
    private JPanel _panel;
    private Player _player;
    private ArrayList<JTextArea> _itemTexts;

    public BagWindow(Player player) {
        _player = player;
        this.setSize(600, 400);
        this.setResizable(false);
        setUpWindow();
    }

    private void setUpWindow() {
        _panel = new JPanel();
        super.add(_panel);
    }

    private void updateItemList() {
        _panel.removeAll();
        _panel.setLayout(new GridLayout());
        _itemTexts = new ArrayList<JTextArea>();
        Item[] items = _player.getItems();
        _printer.out("Displaying window with item count: " + items.length);

        this.setSize(this.getWidth(), (items.length * 100) + 100);

        if(items.length == 0) {
            JLabel label = new JLabel();
            label.setText("No Items");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            label.setSize(_panel.getSize());
            _panel.add(label);
        }
        _panel.setSize(this.getSize());
        for(int i = 0; i < items.length; i++) {
            Item item = items[i];
            JTextArea textArea = new JTextArea();
            textArea.setText(item.getWellFormattedString());
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setBorder(new LineBorder(Color.BLACK, 1));
            _itemTexts.add(textArea);
            _panel.add(textArea);
        }
    }

    public void update(Graphics g) {
        updateItemList();
        super.update(g);
    }
}
