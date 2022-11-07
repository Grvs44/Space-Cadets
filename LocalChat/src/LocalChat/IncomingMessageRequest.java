package LocalChat;

public class IncomingMessageRequest extends LocalChatRequest {
  public final String userName;
  public final String message;
  public final int chatID;

  public IncomingMessageRequest(String userName, String message, int chatID) {
    this.userName = userName;
    this.message = message;
    this.chatID = chatID;
  }
}
