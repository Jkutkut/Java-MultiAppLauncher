package dam.psp.mal.controller;

import dam.psp.mal.view.ViewWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Controller implements ActionListener {
    private ViewWindow viewWindow;

    public Controller(ViewWindow viewWindow) {
        this.viewWindow = viewWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton btn = (JButton) e.getSource();

            try {
                if (btn == this.viewWindow.getBtnTerminator())
                    terminator();
                else if (btn == this.viewWindow.getBtnVScode())
                    vscode();
                else if (btn == this.viewWindow.getBtnSettings())
                    nautilus();
                else if (btn == this.viewWindow.getBtnBrowser())
                    browser();
            }
            catch (Exception ex) {
                ex.printStackTrace(); // Alert user
            }
        }
        // TODO sobreescribir textf con seleccion de listBrowser
    }

    private void terminator() throws IOException {
        Process t = new ProcessBuilder("terminator").start();
    }

    private void vscode() throws IOException {
        Process t = new ProcessBuilder("code").start();
    }

    private void nautilus() throws IOException {
        Process t = new ProcessBuilder("gnome-control-center").start();
    }

    private void browser() throws IOException {
        String url = this.viewWindow.getTxtfBrowser().getText();
        Process t = new ProcessBuilder("open", url).start();

        this.viewWindow.addBrowserHistory(url);
    }
}
