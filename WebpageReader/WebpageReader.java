import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;
public class WebpageReader {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter ID: ");
        String userId = input.nextLine();
        URL url = new URL("https://www.ecs.soton.ac.uk/people/" + userId);
        input.close();
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        boolean emailFound = false;
        boolean nameFound = false;
        String line;
        int i = 0;
        while(i<1000 && !(emailFound && nameFound)) {
            line = reader.readLine();
            if (!emailFound && line.indexOf("og:email") != -1) {
                System.out.println("Email: " + line.substring(35, 50));
                emailFound = true;
            } else if (!nameFound && line.indexOf("og:title") != -1) {
                System.out.println("Name: " + line.substring(35, 55));
                nameFound = true;
            }
            i++;
        }
        reader.close();
    }
}