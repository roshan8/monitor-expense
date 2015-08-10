package roshan_shetty.edugap.model;

/**
 * Created by roshan on 4/22/2015.
 */
public class postModel {
    int id;
    String postTitle;
    String postMessage;
    String postedTime;

    // Constructor
    public postModel() {
    }

    public postModel(String msgTitle, String msgContent, String msgPostedOn) {
        //this.id = id;
        this.postMessage = msgContent;
        this.postedTime = msgPostedOn;
        this.postTitle = msgTitle;

    }

    // setters
    public void setId(int id1) {
        this.id = id1;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public void setPostMessage(String postMessage1) {
        this.postMessage = postMessage1;
    }

    public void setPostedTime(String postedTitle) {
        this.postedTime = postedTitle;
    }



    // getters
    public long getId() {
        return this.id;
    }

    public String getPostMessage() {
        return this.postMessage;
    }



    public String getPostedTime() {
        return this.postedTime;
    }



    public String getPostTitle() {
        return this.postTitle;
    }


}
