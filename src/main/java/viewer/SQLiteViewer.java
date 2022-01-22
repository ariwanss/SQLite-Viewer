package viewer;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class SQLiteViewer extends JFrame {

    private Database database;

    private JFileChooser fileChooser;
    private JPanel panel;

    private JPanel openFilePanel;
    private JTextField fileNameTextField;
    private JButton openFileButton;

    private JPanel comboBoxPanel;
    private JLabel selectTableLabel;
    private JComboBox<String> tablesComboBox;

    private JPanel queryAreaPanel;
    private JLabel queryLabel;
    private JTextArea queryTextArea;

    private JPanel executePanel;
    private JButton executeQueryButton;

    private JPanel resultPanel;
    private JScrollPane scrollPane;
    private JTable table;

    public SQLiteViewer() {
        fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
        fileChooser.setName("FileChooser");
        add(fileChooser);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        //setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("SQLite Viewer");

        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        add(panel);

        /*JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout());
        panel.add(northPanel, BorderLayout.NORTH);*/

        initComponents();

        setVisible(true);
    }

    public void initComponents() {

        openFilePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        fileNameTextField = new JTextField();
        fileNameTextField.setName("FileNameTextField");
        fileNameTextField.setPreferredSize(new Dimension(575, 30));
        fileNameTextField.setEditable(false);
        openFilePanel.add(fileNameTextField);

        openFileButton = new JButton();
        openFileButton.setText("Open");
        openFileButton.setName("OpenFileButton");
        openFileButton.setPreferredSize(new Dimension(75, 30));
        openFileButton.addActionListener(actionEvent -> {
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filepath = selectedFile.getAbsolutePath();
                fileNameTextField.setText(filepath);
                tablesComboBox.removeAllItems();
                database = new Database(filepath);
                List<String> tables = database.listTables();
                tables.forEach(tablesComboBox::addItem);
                String selectedTable = (String) tablesComboBox.getSelectedItem();
                queryTextArea.setText("SELECT * FROM " + selectedTable + ";");
            }
        });
        openFilePanel.add(openFileButton);

        panel.add(openFilePanel);

        comboBoxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        selectTableLabel = new JLabel();
        selectTableLabel.setText("Select table:");
        comboBoxPanel.add(selectTableLabel);

        tablesComboBox = new JComboBox<>();
        tablesComboBox.setName("TablesComboBox");
        tablesComboBox.setPreferredSize(new Dimension(575, 30));
        tablesComboBox.addActionListener(actionEvent -> {
            String selectedTable = (String) tablesComboBox.getSelectedItem();
            queryTextArea.setText("SELECT * FROM " + selectedTable + ";");
        });
        comboBoxPanel.add(tablesComboBox);

        panel.add(comboBoxPanel);

        queryAreaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        queryLabel = new JLabel();
        queryLabel.setText("Query:");
        queryAreaPanel.add(queryLabel);

        queryTextArea = new JTextArea();
        queryTextArea.setName("QueryTextArea");
        queryTextArea.setBackground(Color.WHITE);
        queryTextArea.setPreferredSize(new Dimension(575, 100));
        queryAreaPanel.add(queryTextArea);

        panel.add(queryAreaPanel);

        executePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        executeQueryButton = new JButton();
        executeQueryButton.setName("ExecuteQueryButton");
        executeQueryButton.setText("Execute");
        executeQueryButton.addActionListener(actionEvent -> {
            //database.setTable(new Table());
            try {
                String query = queryTextArea.getText();
                DefaultTableModel tableModel = database.executeQuery(query);
                //table = new JTable(tableModel);
                table.setModel(tableModel);
                //scrollPane.setColumnHeader(new JViewport());
                scrollPane.setViewportView(table);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(new Frame(), e.getMessage());
            }

        });
        executePanel.add(executeQueryButton);

        panel.add(executePanel);

        resultPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        TableModel tableModel = new Table();
        table = new JTable(tableModel);
        table.setName("Table");

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(655, 350));
        resultPanel.add(scrollPane);

        panel.add(resultPanel);
    }
}
