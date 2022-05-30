package utility;

import model.Task;
import parsers.TaskParser;
import persistence.Jsonifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import java.io.PrintWriter;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;


// File input/output operations
public class JsonFileIO {
    public static final File jsonDataFile = new File("./resources/json/tasks.json");
    public static TaskParser taskparser = new TaskParser();
    
    // EFFECTS: attempts to read jsonDataFile and parse it
    //           returns a list of tasks from the content of jsonDataFile
    public static List<Task> read() {
        String txt = "";
        try {
            Scanner input = new Scanner(jsonDataFile);
            input.useDelimiter("");
            while (input.hasNext()) {
                txt = txt + input.next();
            }

        } catch (IOException e) {
            System.out.println("File IO error in read method");
        }
        //System.out.println(txt);
        //System.out.println(taskparser.parse(txt).toString());
        return taskparser.parse(txt);
    }
    
    // EFFECTS: saves the tasks to jsonDataFile
    public static void write(List<Task> tasks) {
        try {
            JSONArray jsonifiedtasks = Jsonifier.taskListToJson(tasks);
            PrintWriter output = new PrintWriter(jsonDataFile);

            output.println(jsonifiedtasks.toString());
            output.close();

        } catch (IOException e) {
            System.out.println("File IO error in write method");
        }
    }
}
