public class Main {
  public static void main(String[] args) {
    if (args.length == 0) new InterpreterWindow(null);
    else {
      for(String arg : args){
        new InterpreterWindow(arg);
      }
    }
  }
}
