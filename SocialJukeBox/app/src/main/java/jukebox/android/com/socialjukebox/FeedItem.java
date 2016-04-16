package jukebox.android.com.socialjukebox;

/**
 * Created by f1vyer1 on 4/16/16.
 */
public class FeedItem {
    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    private String songName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;


}