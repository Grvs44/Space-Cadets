package LocalChat;

public class MessageRequest extends LocalChatRequest {
  public final int userID;
  public final int chatID;
  public final String message;

  public MessageRequest(int userID, int chatID, String message) {
    this.userID = userID;
    this.chatID = chatID;
    this.message = message;
  }
}
