package LocalChatJoin;

import LocalChat.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class ChatWindow extends JFrame implements ActionListener {
  private final JTextArea messageBox = new JTextArea(20, 40);
  private final JTextArea sendBox = new JTextArea(5, 40);
  private final LocalChatJoin localChatJoin;
  public final int chatID;

  public ChatWindow(LocalChatJoin localChatJoin, int chatID, String name) {
    super(name + " - LocalChatJoin");
    this.localChatJoin = localChatJoin;
    this.chatID = chatID;
    setSize(500, 500);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    this.addWindowListener(new ChatWindowListener(localChatJoin, chatID));
    JButton sendButton = new JButton("Send");
    sendButton.addActionListener(this);
    messageBox.setEditable(false);
    JScrollPane messageBoxScroll = new JScrollPane(messageBox);
    JPanel panel = new JPanel();
    panel.add(messageBoxScroll);
    JScrollPane sendBoxScroll = new JScrollPane(sendBox);
    panel.add(sendBoxScroll);
    panel.add(sendButton);
    add(panel);
    setVisible(true);
  }

  public void actionPerformed(ActionEvent event) {
    try (Socket socket = new Socket(localChatJoin.hostIP, localChatJoin.hostPort)) {
      String message = sendBox.getText();
      new MessageRequest(chatID, message).serialize(socket);
      LocalChatResponse response = LocalChatResponse.deserialize(socket);
      if (response instanceof MessageResponse) {
        sendBox.setText("");
        messageBox.setText(messageBox.getText() + "\n\n[" + localChatJoin.userName + "]\n" + message);
      } else {
        JOptionPane.showMessageDialog(this, "Issue sending message", "LocalChatJoin", JOptionPane.ERROR_MESSAGE);
      }
    } catch (ClassNotFoundException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
