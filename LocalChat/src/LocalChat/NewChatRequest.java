package LocalChat;

public class NewChatRequest extends LocalChatRequest {
  private String userName;
  public NewChatRequest(String userName) {
    this.userName = userName;
  }
}
