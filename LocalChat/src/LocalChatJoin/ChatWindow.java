package LocalChatJoin;

import LocalChat.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class ChatWindow extends JFrame implements ActionListener {
  private final JPanel panel = new JPanel();
  private final JTextArea messageBox = new JTextArea(5, 40);
  private final JTextArea sendBox = new JTextArea(5, 40);
  private final JScrollPane messageBoxScroll = new JScrollPane(messageBox);
  private final JScrollPane sendBoxScroll = new JScrollPane(sendBox);
  private final JButton sendButton = new JButton("Send");
  private final LocalChatJoin localChatJoin;
  public final int chatID;

  public ChatWindow(LocalChatJoin localChatJoin, int chatID, String name) {
    super(name + " - LocalChatJoin");
    this.localChatJoin = localChatJoin;
    this.chatID = chatID;
    setSize(500, 500);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    sendButton.addActionListener(this);
    panel.add(messageBoxScroll);
    panel.add(sendBoxScroll);
    panel.add(sendButton);
    add(panel);
    setVisible(true);
  }

  public void actionPerformed(ActionEvent event) {
    try (Socket socket = new Socket(localChatJoin.hostIP, localChatJoin.hostPort)) {
      new MessageRequest(chatID, this.sendBox.getText()).serialize(socket);
      LocalChatResponse response = LocalChatResponse.deserialize(socket);
      if (response instanceof MessageResponse) {
        this.sendBox.setText("");
        System.out.println("Message received");
      } else {
        System.out.println("Issue sending message");
      }
    } catch (ClassNotFoundException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
