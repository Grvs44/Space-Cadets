package LocalChatHost;

import java.util.ArrayList;

public class ChatRoom {
  public final ArrayList<ChatUser> users = new ArrayList<>();
  public final String name;

  public ChatRoom(String name) {
    this.name = name;
  }
}
