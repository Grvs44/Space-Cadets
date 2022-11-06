package LocalChat;

public class ChatRoomLeaveRequest extends LocalChatRequest {
  public final int chatID;

  public ChatRoomLeaveRequest(int chatID) {
    this.chatID = chatID;
  }
}
