package controller.Tasks;

public class SyncTaskProps extends TaskProps {
    public String compareFilePath;

    public SyncTaskProps() {
        this.compareFilePath = "";
    }

    public SyncTaskProps(String compareFilePath) {
        this.compareFilePath = compareFilePath;
    }
}
