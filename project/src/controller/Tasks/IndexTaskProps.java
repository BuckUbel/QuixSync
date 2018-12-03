package controller.Tasks;

public class IndexTaskProps extends TaskProps{

    String pathToIndex;
    String indexFilePath;

    public IndexTaskProps(){}

    public IndexTaskProps(String pathToIndex, String indexFilePath) {
        this.pathToIndex = pathToIndex;
        this.indexFilePath = indexFilePath;
    }
}
