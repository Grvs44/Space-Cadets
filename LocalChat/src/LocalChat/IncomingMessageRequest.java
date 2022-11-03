package LocalChat;

public class IncomingMessageRequest extends LocalChatRequest {
  public final String userName;
  public final String message;

  public IncomingMessageRequest(String userName, String message) {
    this.userName = userName;
    this.message = message;
  }
}
