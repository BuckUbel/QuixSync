package views;

import controller.LanguageController;
import views.defaultViews.QuixDialog;

import javax.swing.*;

public class AnotherRunningProcessDialog extends QuixDialog {

    public JPanel rootPanel;
    private JButton okButton;
    private JLabel dialogText;

    public AnotherRunningProcessDialog(String title) {
        super(title);
    }

    public void createGUI() {
        String s = LanguageController.getLang().ANOTHER_RUNNING_PROCESS_DIALOG;
        super.createGUI(this.rootPanel, this.okButton, this.dialogText, s);
        this.finish();
    }
}
