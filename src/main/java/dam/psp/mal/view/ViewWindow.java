package dam.psp.mal.view;

import dam.psp.mal.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;

public class ViewWindow extends JFrame implements WindowListener {

    private static final String TITLE = "MultiAppLauncher";
    private static final String HISTORY_FILE = "res/browserHistory";
    DefaultListModel<String> historyModel;

    private JPanel jpMenu;
    private JPanel jpBotonera;
    private JButton btnTerminator;
    private JButton btnVScode;
    private JButton btnSettings;
    private JPanel jpBrowser;
    private JTextField txtfBrowser;
    private JButton btnBrowser;
    private JList listBrowser;
    private JPanel jpDirectory;
    private JCheckBox chkbDirectory;
    private JList lstDirectory;
    private JTextField txtfDirectory;
    private JButton btnDirectory;

    public ViewWindow() {
        setTitle(TITLE);
        setIconImage(new ImageIcon("res/img/logo.png").getImage());
        setContentPane(jpMenu);
        pack();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(800, 500);
        setMinimumSize(new Dimension(400, 400)); // TODO adjust
        addWindowListener(this);

        initComponents();
    }

    private void initComponents() {
        // Fill list with browser history
        historyModel = new DefaultListModel<>();
        loadBrowserHistory();
        listBrowser.setModel(historyModel);
        // TODO add hint to txtfs
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
        chkbDirectory.addActionListener(controller);
    }

    // *************** TOOLS ***************
    private static void saveModelInFile(DefaultListModel<String> model, String filename) {
        filename = filename + ".bat";
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
            for (int i = 0; i < model.size(); i++) {
                oos.writeObject(model.get(i));
            }
            oos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadModelFromFile(DefaultListModel<String> listModel, String filename) throws FileNotFoundException {
        filename = filename + ".bat";
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
            while (true) {
                listModel.addElement((String) ois.readObject());
            }
        }
        catch (FileNotFoundException e) {
            System.out.println(filename + " not found");
            throw new FileNotFoundException(e.getMessage());
        }
        catch (EOFException e) {
            System.out.println("Fully loaded " + filename);
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void alertUser(String title, String msg) {
        JOptionPane.showMessageDialog(this, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean addElementToModel(DefaultListModel<String> listModel, String element) {
        for (int i = 0; i < listModel.size(); i++) {
            if (listModel.get(i).equals(element)) {
                listModel.addElement(element);
                return true;
            }
        }
        return false;
    }

    // *************** Controller tools ***************
    public void addBrowserHistory(String url) {
        if (addElementToModel(historyModel, url))
            saveModelInFile(historyModel, HISTORY_FILE);
    }

    private void loadBrowserHistory() {
        try {
            loadModelFromFile(historyModel, HISTORY_FILE);
        }
        catch (FileNotFoundException e) {
            System.out.println("Importing default browser history");
            historyModel.addElement("https://www.youtube.com");
            historyModel.addElement("https://www.instagram.com");
            historyModel.addElement("https://www.twitter.com");
            historyModel.addElement("https://www.amazon.com");
            historyModel.addElement("https://www.wikipedia.org");
        }
    }

    public void updateDirectoryPane() {
        jpDirectory.setVisible(chkbDirectory.isSelected());
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

    public JCheckBox getChkbDirectory() {
        return chkbDirectory;
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

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
