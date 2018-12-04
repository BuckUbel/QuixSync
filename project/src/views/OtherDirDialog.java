package views;

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
        String s = "<html>Der eingegebene Pfad war kein existierendes Verzeichnis <br> Bitte wählen Sie ein anderes Verzeichnis.</html>";
        super.createGUI(this.rootPanel,this.okButton,this.dialogText, s);
        this.finish();
    }
}
