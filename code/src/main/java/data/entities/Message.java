package data.entities;

public class Message {

    private String content;
    private String fromLogin;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFromLogin() {
        return fromLogin;
    }

    public void setFromLogin(String fromLogin) {
        this.fromLogin = fromLogin;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + content + '\'' +
                ", fromLogin='" + fromLogin + '\'' +
                '}';
    }

}
