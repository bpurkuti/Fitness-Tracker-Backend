package dev.marker.endpoints;

public class Session {
    private String session;

    Session(String session) {
        this.session = session;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}