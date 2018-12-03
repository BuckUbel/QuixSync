package controller.Tasks;

import controller.SettingsController;

public class CompareTaskProps extends TaskProps {

    public String sourceIndexPath;
    public String targetIndexPath;
    public String comparePath;
    public boolean isHardSync;
    public boolean slowMode;

    public CompareTaskProps() {
        this.sourceIndexPath = "";
        this.targetIndexPath = "";
        this.comparePath = "";
        this.isHardSync = SettingsController.getIsHardSync();
        this.slowMode = SettingsController.getIsSlowMode();
    }

    public CompareTaskProps(String sourceIndexPath, String targetIndexPath, String comparePath, boolean isHardSync, boolean slowMode) {
        this.sourceIndexPath = sourceIndexPath;
        this.targetIndexPath = targetIndexPath;
        this.comparePath = comparePath;
        this.isHardSync = isHardSync;
        this.slowMode = slowMode;
    }

}
