package Main;

public class Breakdown {
    private final String title;
    private boolean isAccepted;

    public Breakdown(String title, boolean isAccepted) {
        this.title = title;
        this.isAccepted = isAccepted;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }
}
