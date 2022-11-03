package LocalChatHost;

import java.util.ArrayList;
import java.util.Iterator;

public class ChatUser {
  public final String ipAddress;
  public final int port;
  public final String userName;
  private final ArrayList<ChatRoom> chats = new ArrayList<>();

  public ChatUser(String ipAddress, int port, String userName) {
    this.ipAddress = ipAddress;
    this.port = port;
    this.userName = userName;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public int getPort() {
    return port;
  }

  public Iterator<ChatRoom> getChatRooms() {
    return chats.iterator();
  }
}
