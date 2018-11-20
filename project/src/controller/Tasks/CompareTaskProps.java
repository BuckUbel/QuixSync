package controller.Tasks;

public class CompareTaskProps {

    public String sourceIndexPath;
    public String targetIndexPath;
    public String comparePath;
    public boolean isHardSync;
    public boolean slowMode;

    public CompareTaskProps(String sourceIndexPath, String targetIndexPath, String comparePath, boolean isHardSync, boolean slowMode) {
        this.sourceIndexPath = sourceIndexPath;
        this.targetIndexPath = targetIndexPath;
        this.comparePath = comparePath;
        this.isHardSync = isHardSync;
        this.slowMode = slowMode;
    }

}
