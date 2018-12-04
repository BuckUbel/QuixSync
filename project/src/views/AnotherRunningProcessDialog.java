package views;

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
        String s = "<html>Ein anderer Prozess läuft derzeit. <br> Warten Sie bis dieser abgeschlossen ist. </html>";
        super.createGUI(this.rootPanel,this.okButton,this.dialogText, s);
        this.finish();
    }
}
