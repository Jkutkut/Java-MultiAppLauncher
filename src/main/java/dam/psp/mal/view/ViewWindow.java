package dam.psp.mal.view;

import dam.psp.mal.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ViewWindow extends JFrame implements WindowListener {

    private static final String TITLE = "MultiAppLauncher";
    DefaultListModel<String> listModel;

    private JPanel jpMenu;
    private JPanel jpBotonera;
    private JButton btnTerminator;
    private JButton btnVScode;
    private JButton btnSettings;
    private JPanel jpBrowser;
    private JTextField txtfBrowser;
    private JButton btnBrowser;
    private JList listBrowser;

    public ViewWindow() {
        setTitle(TITLE);
        setIconImage(new ImageIcon("res/img/logo.png").getImage());
        setContentPane(jpMenu);
        pack();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(800, 500);
        setMinimumSize(new Dimension(400, 400));
        addWindowListener(this);

        initComponents();
    }

    private void initComponents() {
        // Fill list with browser history
        listModel = new DefaultListModel<>();
        loadBrowserHistory();
        listBrowser.setModel(listModel);

    }

    public void setController(Controller controller) {
        btnTerminator.addActionListener(controller);
        btnVScode.addActionListener(controller);
        btnSettings.addActionListener(controller);
        btnBrowser.addActionListener(controller);
        listBrowser.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                txtfBrowser.setText((String) listBrowser.getSelectedValue());
            }
        });
        txtfBrowser.addActionListener(controller);
    }

    public void addBrowserHistory(String url) {
        for (int i = 0; i < listModel.size(); i++) {
            if (listModel.get(i).equals(url))
                return;
        }
        listModel.addElement(url);
        saveBrowserHistory();
    }

    private void saveBrowserHistory() {
        // TODO
    }

    private void loadBrowserHistory() {
        // TODO
        listModel.addElement("https://www.youtube.com");
        listModel.addElement("https://www.instagram.com");
        listModel.addElement("https://www.twitter.com");
        listModel.addElement("https://www.amazon.com");
        listModel.addElement("https://www.wikipedia.org");
    }

    public void alertUser(String title, String msg) {
        JOptionPane.showMessageDialog(this, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    // GETTERS

    public JButton getBtnTerminator() {
        return btnTerminator;
    }

    public JButton getBtnVScode() {
        return btnVScode;
    }

    public JButton getBtnSettings() {
        return btnSettings;
    }

    public JButton getBtnBrowser() {
        return btnBrowser;
    }

    public JTextField getTxtfBrowser() {
        return txtfBrowser;
    }

    public JList getListBrowser() {
        return listBrowser;
    }

    // WINDOW LISTENER

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
