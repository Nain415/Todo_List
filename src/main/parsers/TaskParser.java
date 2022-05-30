package parsers;

import model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskParser {


    // EFFECTS: iterates over every JSONObject in the JSONArray represented by the input
    // string and parses it as a task; each parsed task is added to the list of tasks.
    // Any task that cannot be parsed due to malformed JSON data is not added to the
    // list of tasks.
    // Note: input is a string representation of a JSONArray
    public List<Task> parse(String input) {
        List<Task> tasklist = new ArrayList<>();
        JSONArray taskArray = new JSONArray(input);
        JSONObject duedate;

        for (Object object : taskArray) {
            JSONObject taskJson = (JSONObject) object;
            JSONObject priority = taskJson.getJSONObject("priority");
            String status = taskJson.getString("status");
            String description = taskJson.getString("description");
            JSONArray tags = taskJson.getJSONArray("tags");
            if (taskJson.get("due-date") == JSONObject.NULL) {
                duedate = null;
            } else {
                duedate = taskJson.getJSONObject("due-date");
            }
            tasklist.add(buildtask(description,priority,status,tags,duedate));
        }

        return tasklist;
    }

    private Task buildtask(String description, JSONObject priority, String status, JSONArray tags, JSONObject duedate) {
        Task task = new Task(description);
        if (!(duedate == null)) {
            task.setDueDate(gendueDate(duedate));
        }
        addTags(tags,task);
        task.setPriority(genPriority(priority));
        task.setStatus(genStatus(status));

        return task;
    }

    public Priority genPriority(JSONObject p) {
        Priority prio = new Priority();
        prio.setImportant(p.getBoolean("important"));
        prio.setUrgent(p.getBoolean("urgent"));
        return prio;
    }

    public Status genStatus(String s) {
        if (s.equals("IN_PROGRESS")) {
            return Status.IN_PROGRESS;
        }

        if (s.equals("TODO")) {
            return Status.TODO;
        }

        if (s.equals("UP_NEXT")) {
            return Status.UP_NEXT;
        }

        return Status.DONE;
    }

    //MODIFIES: task
    public void addTags(JSONArray tags, Task task) {
        for (Object object : tags) {
            JSONObject tag = (JSONObject) object;
            task.addTag(tag.getString("name"));
        }
    }

    public DueDate gendueDate(JSONObject dd) {
        int year = dd.getInt("year");
        int month = dd.getInt("month");
        int day = dd.getInt("day");
        int hour = dd.getInt("hour");
        int minute = dd.getInt("minute");
        Date date = new Date(year - 1900,month,day,hour,minute);
        DueDate dueDate = new DueDate(date);

        return dueDate;
    }


}
