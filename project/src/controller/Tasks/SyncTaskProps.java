package controller.Tasks;

public class SyncTaskProps extends TaskProps{
    String compareFilePath;

    public SyncTaskProps(){}

    public SyncTaskProps(String compareFilePath) {
        this.compareFilePath = compareFilePath;
    }
}
