package LocalChatHost;

import java.util.ArrayList;
import java.util.Iterator;

public class ChatRoom {
  private ArrayList<ChatUser> users = new ArrayList<>();
  private String name;

  public ChatRoom(String name) {
    this.name = name;
  }

  public ArrayList<ChatUser> getUsers() {
    return users;
  }
}
