package LocalChat;

public class ChatRoomCreateResponse extends LocalChatResponse {
  public final int chatID;

  public ChatRoomCreateResponse(int chatID) {
    this.chatID = chatID;
  }
}
