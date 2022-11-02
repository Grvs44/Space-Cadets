package LocalChat;

import java.io.IOException;
import java.io.InputStream;

public abstract class LocalChatResponse extends LocalChatRequestResponse {
  public static LocalChatResponse deserialize(InputStream byteStream) throws IOException, ClassNotFoundException {
    return (LocalChatResponse) LocalChatRequestResponse.deserialize(byteStream);
  }
}
