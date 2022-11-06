package LocalChat;

import java.io.*;
import java.net.Socket;

public abstract class LocalChatRequestResponse implements java.io.Serializable {

  public void serialize(Socket socket) throws IOException {
    OutputStream outputStream = socket.getOutputStream();
    ObjectOutputStream objectStream = new ObjectOutputStream(outputStream);
    objectStream.writeObject(this);
    objectStream.close();
    outputStream.close();
  }

  public static LocalChatRequestResponse deserialize(Socket socket) throws IOException, ClassNotFoundException {
    InputStream inputStream = socket.getInputStream();
    ObjectInputStream objectStream = new ObjectInputStream(inputStream);
    LocalChatRequestResponse request = (LocalChatRequestResponse) objectStream.readObject();
    objectStream.close();
    inputStream.close();
    return request;
  }
}
