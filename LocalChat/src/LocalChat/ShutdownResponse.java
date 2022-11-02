package LocalChat;

public class ShutdownResponse extends LocalChatResponse {
  private boolean success;

  public ShutdownResponse(boolean success){
    this.success = success;
  }

  public boolean isSuccess() {
    return success;
  }
}
