package blockamon.windows;

import blockamon.io.IPrinter;
import blockamon.io.PrintManager;
import blockamon.objects.Blockamon;
import blockamon.objects.Player;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * User: Jack's Computer
 * Date: 11/9/2014
 * Time: 2:50 PM
 */
public class BlockamonWindow extends JFrame {

    private IPrinter _printer = PrintManager.getPrinter(this);
    private JPanel _panel;
    private Player _player;
    private ArrayList<JTextArea> _texts;

    public BlockamonWindow(Player player) {
        _player = player;
        this.setSize(600, 400);
        this.setResizable(false);
        setUpWindow();
    }

    private void setUpWindow() {
        _panel = new JPanel();
        super.add(_panel);
    }

    private void updateBlockamonList() {
        _panel.removeAll();
        _panel.setLayout(new GridLayout());
        _texts = new ArrayList<JTextArea>();
        ArrayList<Blockamon> blockamans = _player.getBlockamon();
        _printer.out("Displaying window with blockamon count: " + blockamans.size());
        this.setSize(this.getWidth(), (blockamans.size() * 100) + 100);
        _panel.setSize(this.getSize());
        for(int i = 0; i < blockamans.size(); i++) {
            Blockamon blockamon = blockamans.get(i);
            JTextArea textArea = new JTextArea();
            textArea.setText(blockamon.toString());
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setBorder(new LineBorder(Color.BLACK, 1));
            _texts.add(textArea);
            _panel.add(textArea);
        }
    }

    public void update(Graphics g) {
        updateBlockamonList();
        super.update(g);
    }
}
