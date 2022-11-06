package LocalChat;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public abstract class LocalChatResponse extends LocalChatRequestResponse {
  public static LocalChatResponse deserialize(Socket socket) throws IOException, ClassNotFoundException {
    return (LocalChatResponse) LocalChatRequestResponse.deserialize(socket);
  }
}
