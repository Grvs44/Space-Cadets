import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        for(String arg : args){
            try{
                Interpreter interpreter = new Interpreter(arg);
            } catch (IOException e) {
                System.out.println(arg + ": file not found");
            }
        }
    }
}
