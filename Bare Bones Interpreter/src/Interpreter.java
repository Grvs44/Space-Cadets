import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interpreter {
    private ArrayList<String> code;
    private int lineNo = 0;
    private HashMap<String, String> variables;
    private Class<Construct>[] constructs = {
            Clear.class
    };
    public Interpreter(String path) throws IOException {
        code = new ArrayList<>();
        variables = new HashMap<>();
        FileReader codeFile = new FileReader(path);
        BufferedReader codeBuffer = new BufferedReader(codeFile);
        String line;
        while ((line = codeBuffer.readLine()) != null) {
            code.add(line);
        }
        codeBuffer.close();
        codeFile.close();
    }

    private void logVariables() {
        String output = String.format(
                "## Variables on line %s `%i`\n" +
                "|Variable|Value|\n" +
                "|:------:|:---:|",
                lineNo + 1, code.get(lineNo).trim()
        );
        for(String var : variables.keySet()){
            output += String.format("|%s|%i|\\n", var, variables.get(var));
        }
        System.out.println(output);
    }

    public void run() {
        int codeSize = code.size();
        boolean result = true;
        while(result && lineNo < codeSize){
            result = step();
        }
    }

    public boolean step() {
        if(lineNo >= code.size()) return false;
        String line = code.get(lineNo);
        if(line.length() == 0){
            lineNo++;
            return step();
        }
        for(Class<Construct> construct : constructs){
            if(construct.)
        }
        Pattern pattern = Pattern.compile("w3schools");
        Matcher matcher = pattern.matcher(line);
        boolean matchFound = matcher.find();
        if(matchFound) {
            System.out.println("Match found");
        } else {
            System.out.println("Match not found");
        }
        return true;
    }
}
