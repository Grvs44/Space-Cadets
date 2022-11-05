package LocalChat;

public class ChatRoomJoinRequest extends LocalChatRequest {
  public final int chatID;

  public ChatRoomJoinRequest(int chatID) {
    this.chatID = chatID;
  }
}
