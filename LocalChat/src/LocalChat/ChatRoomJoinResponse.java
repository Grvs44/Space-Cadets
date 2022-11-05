package LocalChat;

public class ChatRoomJoinResponse extends LocalChatResponse {
  public final String name;

  public ChatRoomJoinResponse(String name) {
    this.name = name;
  }
}
