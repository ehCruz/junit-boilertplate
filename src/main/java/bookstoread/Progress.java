package bookstoread;

public class Progress {

    private Integer completed;
    private Integer toRead;
    private Integer inProgress;

    public Progress(Integer completed, Integer toRead, Integer inProgress) {
        this.completed = completed;
        this.toRead = toRead;
        this.inProgress = inProgress;
    }

    public Integer getCompleted() {
        return completed;
    }

    public Integer getToRead() {
        return toRead;
    }

    public Integer getInProgress() {
        return inProgress;
    }
}
