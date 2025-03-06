package ro.tuc.ds2020.model;

public class ChatMessage {
    private String senderId; // User or Admin ID
    private String content;  // Message content
    private String recipientId; // Target user (for admin)
    public ChatMessage(String senderId, String content, String recipientId){
        this.senderId = senderId;
        this.content = content;
        this.recipientId = recipientId;


    }

    // Getters and Setters
    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getRecipientId() { return recipientId; }
    public void setRecipientId(String recipientId) { this.recipientId = recipientId; }
}
