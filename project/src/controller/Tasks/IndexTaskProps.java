package controller.Tasks;

public class IndexTaskProps extends TaskProps {

    public String pathToIndex;
    public String indexFilePath;

    public IndexTaskProps() {
        this.pathToIndex = "";
        this.indexFilePath = "";
    }

    public IndexTaskProps(String pathToIndex, String indexFilePath) {
        this.pathToIndex = pathToIndex;
        this.indexFilePath = indexFilePath;
    }
}
