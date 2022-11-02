package LocalChat;

public class JoinResponse extends LocalChatResponse {
  private boolean success;
  private int userID;

  public JoinResponse(boolean success, int userID) {
    this.success = success;
    this.userID = userID;
  }

  public int getUserID() {
    return userID;
  }

  public boolean isSuccess() {
    return success;
  }
}
