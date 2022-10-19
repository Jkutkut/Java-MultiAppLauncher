package dam.psp.mal.controller;

import dam.psp.mal.view.ViewWindow;
import jkutkut.InvalidDataException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Controller implements ActionListener {
    private static final String VALID_WEBSITE = "^https?://(www2?\\.)?([a-zA-Z\\d]+\\.)+[a-zA-Z\\d]+/?$";
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
                    openApp("terminator");
                else if (btn == this.viewWindow.getBtnVScode())
                    openApp("code");
                else if (btn == this.viewWindow.getBtnSettings())
                    openApp("gnome-control-center");
                else if (btn == this.viewWindow.getBtnBrowser())
                    browser();
                else if (btn == this.viewWindow.getBtnCmd())
                    executeCmd();
            }
            catch (InvalidDataException ex) {
                this.viewWindow.alertUser("Datos inválidos", ex.getMessage());
            }
            catch (IOException ex) {
                this.viewWindow.alertUser("Error", ex.getMessage());
            }
        }
        else if (e.getSource() instanceof JTextField) {
            JTextField txtf = (JTextField) e.getSource();
            if (txtf == this.viewWindow.getTxtfBrowser()) {
                try {
                    browser();
                }
                catch (InvalidDataException ex) {
                    this.viewWindow.alertUser("Datos inválidos", ex.getMessage());
                }
                catch (IOException ex) {
                    this.viewWindow.alertUser("Error", ex.getMessage());
                }
            }
        }
        else if (e.getSource() instanceof JCheckBox) {
            JCheckBox chkb = (JCheckBox) e.getSource();
            if (chkb == this.viewWindow.getChkbCmd())
                this.viewWindow.updateDirectoryPane();
        }
    }

    private void openApp(String app) throws IOException {
        Process t = new ProcessBuilder(app).start();
    }

    private void executeCmd() throws IOException {
        String cmd = this.viewWindow.getTxtfCmd().getText().trim();
        if (cmd.isEmpty())
            throw new InvalidDataException("Add a command to execute.");
        Process t = new ProcessBuilder("terminator", "-x", cmd + "; zsh").start();
    }

    private void browser() throws IOException, InvalidDataException {
        String url = this.viewWindow.getTxtfBrowser().getText();
        if (url == null || url.trim().isEmpty())
            throw new InvalidDataException("URL inválida");

        if (!url.matches(VALID_WEBSITE))
            throw new InvalidDataException("URL inválida: La url no tiene sentido");
        if (!pingWebsite(url))
            throw new InvalidDataException("URL inválida: No se ha podido conectar con el dominio");
        new ProcessBuilder("open", url).start();
        this.viewWindow.addBrowserHistory(url);
    }

    public static boolean pingWebsite(String url_s) {
        try {
            URL url = new URL(url_s);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("HEAD");
            int responseCode = huc.getResponseCode();

            return responseCode != 404;
        }
        catch (Exception e) {
            return false;
        }
    }

    public void directory() {
        String dir = viewWindow.getTxtfCmd().getText();
        // TODO validate
        this.viewWindow.addDirectory(dir);
    }
}
