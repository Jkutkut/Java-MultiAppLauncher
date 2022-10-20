package dam.psp.mal.main;

import com.formdev.flatlaf.FlatDarculaLaf;
import dam.psp.mal.controller.Controller;
import dam.psp.mal.controller.JListListener;
import dam.psp.mal.view.ViewWindow;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ViewWindow viewWindow = new ViewWindow();

                Controller controller = new Controller(viewWindow);
                JListListener jListListener = new JListListener(controller);
                viewWindow.setController(controller, jListListener);
                viewWindow.setVisible(true);
            }
        });
    }
}
