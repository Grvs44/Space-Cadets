package LocalChat;

public class ChatRoomCreateRequest extends LocalChatRequest {
  public final String name;

  public ChatRoomCreateRequest(String name) {
    this.name = name;
  }
}
