package LocalChat;

public class MessageRequest extends LocalChatRequest {
  private final int userID;
  private final String message;

  public MessageRequest(int userID, String message) {
    this.userID = userID;
    this.message = message;
  }

  public int getUserID() {
    return userID;
  }

  public String getMessage() {
    return message;
  }
}
