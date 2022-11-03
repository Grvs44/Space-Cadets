package LocalChat;

public class ChatRoomJoinRequest extends LocalChatRequest {
  public final int chatID;
  public final String userName;

  public ChatRoomJoinRequest(String userName, int chatID) {
    this.chatID = chatID;
    this.userName = userName;
  }
}
