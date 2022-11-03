package LocalChat;

public class UserJoinRequest extends LocalChatRequest {
  public final int port;
  public final String userName;

  public UserJoinRequest(int port, String userName) {
    this.port = port;
    this.userName = userName;
  }
}
