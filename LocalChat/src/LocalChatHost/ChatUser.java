package LocalChatHost;

import java.util.ArrayList;

public class ChatUser {
  public final String ipAddress;
  public final int port;
  public final String userName;
  public final ArrayList<ChatRoom> chatRooms = new ArrayList<>();

  public ChatUser(String ipAddress, int port, String userName) {
    this.ipAddress = ipAddress;
    this.port = port;
    this.userName = userName;
  }
}
