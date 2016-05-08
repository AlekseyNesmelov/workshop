package workshop.client.head;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTextArea;
import workshop.client.manager.IController;
import workshop.client.manager.ManagerGUI;

public class HeadGUI extends ManagerGUI {
    private static final String TITLE = "Head client";
    private static final String SHOW_LOG_TITLE = "Show log";

    private final JButton mShowLogButton;
    private final JTextArea mLogArea;
    private final ClickListener mClickListener = new ClickListener();
    public HeadGUI(IController controller) {
        super(controller);
        mShowLogButton = new JButton(SHOW_LOG_TITLE);
        mShowLogButton.setBounds(50, 250, 200, 30);
        mShowLogButton.addActionListener(mClickListener);
        
        mLogArea = new JTextArea();
        mLogArea.setBounds(50, 285, 400, 150);
    }
    private class ClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == mShowLogButton) {
            }
        }
    }
    
    @Override
    public void show() {
        super.show();
        mFrame.setTitle(TITLE);
        showLogScreeen();
    }
    
    public void showLogScreeen() {
        mFrame.add(mShowLogButton);
        mFrame.add(mLogArea);
    }
}
