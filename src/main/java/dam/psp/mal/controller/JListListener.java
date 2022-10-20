package dam.psp.mal.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class JListListener implements KeyListener {
    Controller controller;

    public JListListener(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DELETE)
            controller.actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, "delete"));
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
