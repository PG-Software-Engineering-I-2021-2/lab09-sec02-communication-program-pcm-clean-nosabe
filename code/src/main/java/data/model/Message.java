package data.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Message {

    private String content;
    private String sender;
    private MessageType type;
    private String name;

    public enum MessageType {
        CHAT, LEAVE, JOIN
    }

}
