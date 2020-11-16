package insta;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class User {
    private String name;
    private String email;
    private String bio;
    private String avatarUrl;

    public User(String name, String email) {
        this(name, email, null, null);
    }

    public User(String name, String email, String bio, String avatarUrl) {
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}

