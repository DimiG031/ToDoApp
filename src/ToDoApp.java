import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ToDoApp extends JFrame {
    private JTabbedPane tabbedPane;
    private int tabCounter = 0;
    private static final int MAX_TABS = 10;
    private HashMap<Integer, ArrayList<JCheckBox>> tabTasks = new HashMap<>();
    private JComboBox<String> comboBox;
    private JProgressBar progressBar;
    private JTextArea taskDescription;

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

        JButton btnNewTab = new JButton("New Tab");
        btnNewTab.setBounds(10, 10, 100, 30);
        btnNewTab.addActionListener(e -> {
            if (tabCounter < MAX_TABS) {
                addNewTab();
            } else {
                JOptionPane.showMessageDialog(ToDoApp.this, "Maximum number of tabs reached!");
            }
        });

        comboBox = new JComboBox<>(new String[]{"Done", "Undone", "All"});
        comboBox.setBounds(10, 60, 100, 30);
        comboBox.setSelectedIndex(2);
        comboBox.addActionListener(e -> filterTasks(comboBox.getSelectedItem().toString()));

        taskDescription = new JTextArea();
        taskDescription.setBounds(120, 10, 500, 80);

        JButton addButton = new JButton("Add Task");
        addButton.setBounds(520, 90, 100, 30);
        addButton.addActionListener(e -> {
            String description = taskDescription.getText().trim();
            if (!description.isEmpty()) {
                addTask(description);
                taskDescription.setText("");
            }
        });

        progressBar = new JProgressBar();
        progressBar.setBounds(10, 600, 610, 30);
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
        JScrollPane scrollPane = new JScrollPane(newTabContent);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        tabTasks.put(tabCounter, new ArrayList<>());

        tabbedPane.addTab("Tab " + tabCounter, scrollPane);
        tabCounter++;
    }

    private void addTask(String description) {
        int selectedIndex = tabbedPane.getSelectedIndex();
        if (selectedIndex >= 0 && tabTasks.containsKey(selectedIndex)) {
            JPanel currentTab = (JPanel) ((JScrollPane) tabbedPane.getComponentAt(selectedIndex)).getViewport().getView();
            JCheckBox checkBox = new JCheckBox(description);
            checkBox.addItemListener(e -> updateProgressBar());
            tabTasks.get(selectedIndex).add(checkBox);
            currentTab.add(checkBox);
            currentTab.revalidate();
            currentTab.repaint();
            updateProgressBar();
        }
    }

    private void filterTasks(String filter) {
        int selectedIndex = tabbedPane.getSelectedIndex();
        if (selectedIndex >= 0 && tabTasks.containsKey(selectedIndex)) {
            for (JCheckBox checkBox : tabTasks.get(selectedIndex)) {
                boolean isSelected = checkBox.isSelected();
                checkBox.setVisible(
                        filter.equals("All") ||
                                (filter.equals("Done") && isSelected) ||
                                (filter.equals("Undone") && !isSelected)
                );
            }
            updateProgressBar();
        }
    }

    private void updateProgressBar() {
        int selectedIndex = tabbedPane.getSelectedIndex();
        if (selectedIndex >= 0 && tabTasks.containsKey(selectedIndex)) {
            ArrayList<JCheckBox> tasks = tabTasks.get(selectedIndex);
            int totalTasks = tasks.size();
            int checkedTasks = (int) tasks.stream().filter(JCheckBox::isSelected).count();
            int percentage = totalTasks == 0 ? 0 : (checkedTasks * 100) / totalTasks;
            progressBar.setValue(percentage);
            progressBar.setString("Progress: " + percentage + "%");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ToDoApp::new);
    }
}
