package controller.Tasks;

public class CompareTaskProps {

    String sourceIndexPath;
    String targetIndexPath;
    String comparePath;
    boolean isHardSync;
    boolean slowMode;

    public CompareTaskProps(String sourceIndexPath, String targetIndexPath, String comparePath, boolean isHardSync, boolean slowMode) {
        this.sourceIndexPath = sourceIndexPath;
        this.targetIndexPath = targetIndexPath;
        this.comparePath = comparePath;
        this.isHardSync = isHardSync;
        this.slowMode = slowMode;
    }

}
