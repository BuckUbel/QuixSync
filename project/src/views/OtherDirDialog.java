package views;

import controller.LanguageController;
import views.defaultViews.QuixDialog;

import javax.swing.*;

public class OtherDirDialog extends QuixDialog {

    public JPanel rootPanel;
    private JButton okButton;
    private JLabel dialogText;

    public OtherDirDialog(String title) {
        super(title);
    }

    public void createGUI() {
        String s = LanguageController.getLang().OTHERDIALOG_TEXT;
        super.createGUI(this.rootPanel, this.okButton, this.dialogText, s);
        this.finish();
    }
}
