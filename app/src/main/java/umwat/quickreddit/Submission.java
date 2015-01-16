package umwat.quickreddit;

/**
 * Created on 1/13/15.
 */
public class Submission {
    private int score;
    private String title;
    private boolean isSelf;
    private String subreddit;
    private boolean isGilded;
    private int gilds;
    private String selfText;
    private boolean visited;
    private String author;
    private boolean over18;
    private String name;
    private String url;
    private boolean stickied;
    private int numComments;
    private String thumbnailUrl;
    private boolean hidden;
    private String commentsUrl;
    private String domain;
    private long created;

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }


    public void setScore(int score) {
        this.score = score;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSelf(boolean isSelf) {
        this.isSelf = isSelf;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public void setGilded(boolean isGilded) {
        this.isGilded = isGilded;
    }

    public void setGilds(int gilds) {
        this.gilds = gilds;
    }

    public void setSelfText(String selfText) {
        this.selfText = selfText;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setOver18(boolean over18) {
        this.over18 = over18;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setStickied(boolean stickied) {
        this.stickied = stickied;
    }

    public void setNumComments(int numComments) {
        this.numComments = numComments;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public void setCommentsUrl(String commentsUrl) {
        this.commentsUrl = commentsUrl;
    }

    public int getScore() {
        return score;
    }

    public boolean isHidden() {
        return hidden;
    }

    public String getTitle() {
        return title;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public boolean isGilded() {
        return isGilded;
    }

    public String getSelfText() {
        return selfText;
    }


    public String getAuthor() {
        return author;
    }

    public boolean isOver18() {
        return over18;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public boolean isStickied() {
        return stickied;
    }

    public int getNumComments() {
        return numComments;
    }

    public int getGilds() {
        return gilds;
    }

    public boolean isVisited() {
        return visited;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getCommentsUrl() {
        return commentsUrl;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
