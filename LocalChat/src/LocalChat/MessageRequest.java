package LocalChat;

public class MessageRequest extends LocalChatRequest {
  public final int chatID;
  public final String message;

  public MessageRequest(int chatID, String message) {
    this.chatID = chatID;
    this.message = message;
  }
}
