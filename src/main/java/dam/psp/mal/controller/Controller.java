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
                    openApp("terminator");
                else if (btn == this.viewWindow.getBtnVScode())
                    openApp("code");
                else if (btn == this.viewWindow.getBtnSettings())
                    openApp("gnome-control-center");
                else if (btn == this.viewWindow.getBtnBrowser())
                    browser();
            }
            catch (Exception ex) {
                ex.printStackTrace();
                // TODO Alert user
            }
        }
    }

    private void openApp(String app) throws IOException {
        Process t = new ProcessBuilder(app).start();
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
