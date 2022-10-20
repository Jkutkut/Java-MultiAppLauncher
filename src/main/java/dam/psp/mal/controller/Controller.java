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
    private static final boolean SHELL = true;
    private static final boolean NO_SHELL = false;
    private static final String VALID_WEBSITE = "^https?://(www2?\\.)?[-a-zA-Z\\d@:%._+~#=]{1,256}\\.[a-zA-Z\\d()]{1,6}\\b([-a-zA-Z\\d()@:%_+.~#?&/=]*)$";
    private final ViewWindow viewWindow;

    public Controller(ViewWindow viewWindow) {
        this.viewWindow = viewWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton btn) {
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
                    executeCmd(NO_SHELL);
                else if (btn == this.viewWindow.getBtnCmdShell())
                    executeCmd(SHELL);
            }
            catch (InvalidDataException ex) {
                this.viewWindow.alertUser("Datos inválidos", ex.getMessage());
            }
            catch (IOException ex) {
                this.viewWindow.alertUser("Error", ex.getMessage());
            }
        }
        else if (e.getSource() instanceof JTextField txtf) {
            try {
                if (txtf == this.viewWindow.getTxtfBrowser())
                    browser();
                else if (txtf == this.viewWindow.getTxtfCmd())
                    executeCmd(SHELL);
            }
            catch (InvalidDataException ex) {
                this.viewWindow.alertUser("Datos inválidos", ex.getMessage());
            }
            catch (IOException ex) {
                this.viewWindow.alertUser("Error", ex.getMessage());
            }
        }
        else if (e.getSource() instanceof JCheckBox chkb) {
            if (chkb == this.viewWindow.getChkbCmd())
                this.viewWindow.updateDirectoryPane();
        }
        else if (e.getSource() instanceof JList lst) {
            if (e.getActionCommand().equals("delete")) {
                this.viewWindow.deleteSelected(lst);
            }
        }
    }

    private void openApp(String app) throws IOException {
        new ProcessBuilder(app).start();
    }

    private void executeCmd(boolean shell) throws IOException {
        String cmd = this.viewWindow.getTxtfCmd().getText();
        if (cmd.trim().isEmpty())
            throw new InvalidDataException("Add a command to execute.");
        if (shell)
            new ProcessBuilder("terminator", "-x", cmd + "; zsh").start();
        else
            new ProcessBuilder("terminator", "-x", cmd).start();
        this.viewWindow.addCmd(cmd);
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
}
