package LocalChat;

import java.io.*;

public abstract class LocalChatRequestResponse implements java.io.Serializable {
  public byte[] serialize() throws IOException {
    ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
    ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
    objectStream.writeObject(this);
    byte[] buffer = byteStream.toByteArray();
    objectStream.close();
    byteStream.close();
    return buffer;
  }

  public void serialize(OutputStream outputStream) throws IOException {
    new ObjectOutputStream(outputStream).writeObject(this);
  }

  public static LocalChatRequestResponse deserialize(InputStream inputStream) throws IOException, ClassNotFoundException {
    ObjectInputStream objectStream = new ObjectInputStream(inputStream);
    LocalChatRequestResponse request = (LocalChatRequestResponse) objectStream.readObject();
    objectStream.close();
    inputStream.close();
    return request;
  }
}
