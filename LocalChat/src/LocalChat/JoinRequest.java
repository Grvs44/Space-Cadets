package LocalChat;

public class JoinRequest extends LocalChatRequest {
  private int chatID;
  private String userName;

  public JoinRequest(String userName, int chatID) {
    this.chatID = chatID;
    this.userName = userName;
  }

  public int getChatID() {
    return chatID;
  }

  public String getUserName() {
    return userName;
  }
}
