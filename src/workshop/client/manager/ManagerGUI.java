package workshop.client.manager;

import workshop.common.SheduleTableRenderer;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import workshop.client.common.IGUI;

public class ManagerGUI implements IGUI {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    private static final String TITLE = "Manager client";
    private static final String EDIT_ORDER = "Edit order";
    private static final String REFRESH = "Refresh";
    
    private final JFrame mFrame;
    private final JButton mRefreshButton;
    private final JButton mEditButton;
    private final JTable mSheduleTable;
    private final JScrollPane mScrollPane;
    private final JLabel mInfoLabel;

    private final ClickListener mClickListener;

    private final IController mController;

    public ManagerGUI(IController controller) {
        mController = controller;

        mFrame = new JFrame(TITLE);
        mFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mFrame.setSize(WIDTH, HEIGHT);
        mFrame.setLayout(null);

        mClickListener = new ClickListener();

        mEditButton = new JButton(EDIT_ORDER);
        mEditButton.setBounds(50, 200, 150, 50);
        mEditButton.addActionListener(mClickListener);

        mRefreshButton = new JButton(REFRESH);
        mRefreshButton.setBounds(250, 200, 100, 50);
        mRefreshButton.addActionListener(mClickListener);

        mSheduleTable = new JTable();
        mSheduleTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        mSheduleTable.setBounds(50, 50, 400, 150);

        mInfoLabel = new JLabel("");
        mInfoLabel.setBounds(50, 300, 400, 100);

        mSheduleTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = mSheduleTable.rowAtPoint(evt.getPoint());
                int col = mSheduleTable.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 0) {
                    String[] info = mController.getInfo(mSheduleTable.getModel().getValueAt(row, col) +
                            "-" + mSheduleTable.getColumnName(col)).split(";");
                    if(info.length > 1) {
                        info[0] = "<html>Client: " + info[0];
                        info[1] = "<br>Description: " + info[1];
                        info[2] = "<br>Status: " + info[2];
                        info[3] = "<br>Status description: " + info[3];
                        info[4] = "<br>Phone: " + info[4] + "</html>";
                    }
                    String infoResult = Arrays.toString(info);
                    infoResult = infoResult.substring(1, infoResult.length() - 1);
                    mInfoLabel.setText(infoResult);
                    mFrame.repaint();
                }
            }
        });

        mScrollPane = new JScrollPane(mSheduleTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mScrollPane.setBounds(50, 50, 400, 150);
    }

    @Override
    public void show() {
        mFrame.setVisible(true);
        showStartScreen();
    }

    public void showStartScreen() {
        clear();
        mFrame.add(mRefreshButton);
        mFrame.add(mEditButton);
        mFrame.add(mScrollPane);
        mFrame.add(mInfoLabel);
        fillTable();
        mFrame.repaint();
    }
    
    public void clear() {
        mFrame.remove(mSheduleTable);
        mFrame.remove(mScrollPane);
        mFrame.repaint();
    }

    public void clearTable() {
        DefaultTableModel tableModel = (DefaultTableModel) mSheduleTable.getModel();
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);
    }

    public void fillTable() {
        clearTable();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = Calendar.getInstance();
        String currentDate = dateFormat.format(calendar.getTime());
        Object[] columns = new Object[30];
        for (int i = 0; i < 30; i++) {
            columns[i] = (String) currentDate;
            calendar.add(Calendar.DATE, 1);
            currentDate = dateFormat.format(calendar.getTime());
        }
        TableModel tableModel = new DefaultTableModel(columns, 0);
        mSheduleTable.setModel(tableModel);
        DefaultTableModel model = (DefaultTableModel) mSheduleTable.getModel();
        int hour = 10;
        for (int i = 0; i < 12; i++) {
            Object[] row = new Object[30];
            for (int j = 0; j < 30; j++) {
                row[j] = hour + ":00";
            }
            hour++;
            model.addRow(row);
        }
        mSheduleTable.setDefaultEditor(Object.class, null);

        String[] shedule = mController.getShedule();
        if (shedule == null) {
            JOptionPane.showMessageDialog(mFrame,
                    "Can't get shedule!");
        } else {
            int size = shedule.length;
            Point[] filledCells = new Point[size];
            for (int i = 0; i < size; i++) {
                String[] splited = shedule[i].split("-");
                if (splited.length == 2) {
                    String time = splited[0];
                    String day = splited[1];
                    int colIndex = getColumnIndex(mSheduleTable, day);
                    if (colIndex != -1) {
                        int rowIndex = getRowIndex(time);
                        if (rowIndex != -1) {
                            filledCells[i] = new Point(rowIndex, colIndex);
                        }
                    }
                }
            }
            setTableRenderer(size, filledCells);
        }
    }
    
    private void setTableRenderer(int size, Point[] filledCells) {
        mSheduleTable.setDefaultRenderer(mSheduleTable.getColumnClass(0), new SheduleTableRenderer(size, filledCells));
    }

    private int getColumnIndex(JTable table, String header) {
        for (int i = 0; i < table.getColumnCount(); i++) {
            if (table.getColumnName(i).equals(header)) {
                return i;
            }
        }
        return -1;
    }

    private int getRowIndex(String header) {
        switch (header) {
            case "10:00": {
                return 0;
            }
            case "11:00": {
                return 1;
            }
            case "12:00": {
                return 2;
            }
            case "13:00": {
                return 3;
            }
            case "14:00": {
                return 4;
            }
            case "15:00": {
                return 5;
            }
            case "16:00": {
                return 6;
            }
            case "17:00": {
                return 7;
            }
            case "18:00": {
                return 8;
            }
            case "19:00": {
                return 9;
            }
            case "20:00": {
                return 10;
            }
            case "21:00": {
                return 11;
            }
            default: {
                return -1;
            }
        }
    }

    private class ClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == mEditButton) {
            }
            if (e.getSource() == mRefreshButton) {
                fillTable();
            }
        }
    }
}
