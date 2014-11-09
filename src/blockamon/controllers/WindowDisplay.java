package blockamon.controllers;

import blockamon.io.IPrinter;
import blockamon.io.PrintManager;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Jack's Computer
 * Date: 11/9/2014
 * Time: 2:23 PM
 */
public class WindowDisplay {

    private IPrinter _printer = PrintManager.getPrinter(this);
    private Map<WindowType, JFrame> _windows;

    public WindowDisplay() {
        _windows = new HashMap<WindowType, JFrame>();
    }

    public void registerWindow(WindowType windowType, JFrame window) {
        if(_windows.containsKey(windowType)) {
            JFrame frame = (JFrame) _windows.get(windowType);
            frame.dispose();
        }
        _windows.put(windowType, window);
    }

    public void displayWindow(WindowType windowType) {
        if(!_windows.containsKey(windowType)) {
            _printer.err("No window found for " + windowType.toString());
            return;
        }
        JFrame window = _windows.get(windowType);
        window.update(window.getGraphics());
        window.setVisible(true);
    }

    public void hideAllWindows() {
        for(WindowType windowType : WindowType.values()) {
            JFrame window = _windows.get(windowType);
            if(window != null) {
                window.setVisible(false);
            }
        }
    }

    public enum WindowType {
        BlockamonWindow,
        Money
    }
}
