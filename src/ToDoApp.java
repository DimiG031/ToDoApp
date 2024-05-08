import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ToDoApp extends JFrame {
    private JTabbedPane tabbedPane;
    private int tabCounter = 0;
    private static final int MAX_TABS = 10;
    private ArrayList<String> tasks = new ArrayList<>();
    private JComboBox<String> comboBox;
    private JProgressBar progressBar;
    private JLabel lastSelectedLabel;

    public ToDoApp() {
        super("To Do App");

        tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(10, 130, 610, 450);
        tabbedPane.setBackground(Color.WHITE);
        addNewTab();

        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                filterTasks(comboBox.getSelectedItem().toString());
            }
        });

        JButton btnNewTab = new JButton("New tab");
        btnNewTab.setBounds(10, 10, 100, 30);
        btnNewTab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tabCounter < MAX_TABS) {
                    addNewTab();
                } else {
                    JOptionPane.showMessageDialog(ToDoApp.this,
                            "Maximum number of tabs reached!");
                }
            }
        });
        comboBox = new JComboBox<>(new String[]{"Done", "Undone", "All"});
        comboBox.setBounds(10,60,100,30);
        comboBox.setSelectedIndex(2);
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterTasks(comboBox.getSelectedItem().toString());
            }
        });

        JTextArea taskDescription = new JTextArea();
        taskDescription.setBounds(120, 10, 500, 80);

        JButton addButton = new JButton("Add Task");
        addButton.setBounds(520, 90, 100, 30);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String description = taskDescription.getText();
                if (!description.isEmpty()) {
                    addTask(description);
                    taskDescription.setText("");
                }
            }
        });

        progressBar = new JProgressBar();
        progressBar.setBounds(10,600,610,30);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        setLayout(null);
        setSize(650, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(btnNewTab);
        add(tabbedPane);
        add(comboBox);
        add(taskDescription);
        add(addButton);
        add(progressBar);

        setVisible(true);
    }

    private void addNewTab() {
        JPanel newTabContent = new JPanel();
        newTabContent.setLayout(new BoxLayout(newTabContent, BoxLayout.Y_AXIS));

        JButton closeButton = new JButton("X");
        closeButton.setMargin(new Insets(0, 0, 0, 0));
        closeButton.setBorder(null);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = tabbedPane.indexOfComponent(newTabContent);
                if (index != -1) {
                    tabbedPane.remove(index);
                    tabCounter--;
                }
            }
        });

        JPanel tabTitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        JLabel tabLabel = new JLabel("Tab  ");
        tabTitlePanel.add(tabLabel);
        tabTitlePanel.add(closeButton);

        tabbedPane.addTab("Tab " + tabCounter, null, newTabContent, null);
        tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, tabTitlePanel);

        tabLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    lastSelectedLabel = tabLabel;
                    tabbedPane.setSelectedIndex(tabbedPane.indexOfTabComponent(tabTitlePanel));
                } else if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    String newName = JOptionPane.showInputDialog("Enter new tab name:", tabbedPane.getTitleAt(tabbedPane.indexOfTabComponent(tabTitlePanel)));
                    if (newName != null && !newName.isEmpty()) {
                        tabbedPane.setTitleAt(tabbedPane.indexOfTabComponent(tabTitlePanel), newName);
                        tabLabel.setText(newName + "  ");
                    }
                }
            }
        });
    }

    private void addTask(String description) {
        int selectedIndex = tabbedPane.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < tabbedPane.getTabCount()) {
            JPanel currentTab = (JPanel) tabbedPane.getComponentAt(selectedIndex);
            Component[] components = currentTab.getComponents();
            int yPosition = components.length * 30 + 10;
            JCheckBox checkBox = new JCheckBox(description);
            checkBox.setBounds(10, yPosition, 200, 30);
            checkBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    filterTasks(comboBox.getSelectedItem().toString());
                }
            });
            currentTab.add(checkBox);
            currentTab.revalidate();
            currentTab.repaint();
            tasks.add(description);
            filterTasks(comboBox.getSelectedItem().toString());
        }
    }

    private void filterTasks(String filter) {
        int selectedIndex = tabbedPane.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < tabbedPane.getTabCount()) {
            JPanel currentTab = (JPanel) tabbedPane.getComponentAt(selectedIndex);
            Component[] components = currentTab.getComponents();
            int totalTasks = 0;
            int checkedTasks = 0;

            for (Component component : components) {
                if (component instanceof JCheckBox) {
                    JCheckBox checkBox = (JCheckBox) component;
                    boolean isSelected = checkBox.isSelected();

                    if (filter.equals("Done")) {
                        checkBox.setVisible(isSelected);
                    } else if (filter.equals("Undone")) {
                        checkBox.setVisible(!isSelected);
                    } else { // "All"
                        checkBox.setVisible(true);
                        totalTasks++;
                        if (isSelected) {
                            checkedTasks++;
                        }
                    }
                }
            }
            currentTab.revalidate();
            currentTab.repaint();

            if (filter.equals("All")) {
                updateProgressBar(totalTasks, checkedTasks);
            }
        }
    }

    private void updateProgressBar(int totalTasks, int checkedTasks) {
        int percentage = totalTasks == 0 ? 0 : (checkedTasks * 100) / totalTasks;
        progressBar.setValue(percentage);
        progressBar.setString("Progress: " + percentage + "%");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public
            void run() {
                new ToDoApp();
            }
        });
    }
}
