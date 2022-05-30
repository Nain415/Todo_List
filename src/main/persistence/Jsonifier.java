package persistence;


import model.DueDate;
import model.Priority;
import model.Tag;
import model.Task;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

// Converts model elements to JSON objects
public class Jsonifier {

    // EFFECTS: returns JSON representation of tag
    public static JSONObject tagToJson(Tag tag) {
        JSONObject jsontag = new JSONObject();

        jsontag.put("name", tag.getName());

        return jsontag;
    }

    // EFFECTS: returns JSON representation of priority
    public static JSONObject priorityToJson(Priority priority) {
        JSONObject jsonprio = new JSONObject();

        jsonprio.put("important", priority.isImportant());
        jsonprio.put("urgent", priority.isUrgent());

        return jsonprio;
    }

    // EFFECTS: returns JSON respresentation of dueDate
    public static JSONObject dueDateToJson(DueDate dueDate) {
        JSONObject jsonduedate = new JSONObject();

        jsonduedate.put("year",dueDate.getDate().getYear() + 1900);
        jsonduedate.put("month",dueDate.getDate().getMonth());
        jsonduedate.put("day",dueDate.getDate().getDate()); //for some reason, this is 4 days behind
        jsonduedate.put("hour",dueDate.getDate().getHours());
        jsonduedate.put("minute",dueDate.getDate().getMinutes());

        return jsonduedate;
    }

    // EFFECTS: returns JSON representation of task
    public static JSONObject taskToJson(Task task) {
        JSONObject jsontask = new JSONObject();
        JSONArray tagarray = new JSONArray();

        for (Tag i : task.getTags()) {
            tagarray.put(tagToJson(i));
        }

        jsontask.put("description", task.getDescription());
        jsontask.put("tags",tagarray);
        if (task.getDueDate() == null) {
            jsontask.put("due-date", JSONObject.NULL);
        } else {
            jsontask.put("due-date", dueDateToJson(task.getDueDate()));
        }
        jsontask.put("priority",priorityToJson(task.getPriority()));
        jsontask.put("status",task.getStatus().toString().replaceAll(" ", "_"));

        return jsontask;
    }

    // EFFECTS: returns JSON array representing list of tasks
    public static JSONArray taskListToJson(List<Task> tasks) {
        JSONArray tasklist = new JSONArray();

        for (Task i : tasks) {
            tasklist.put(taskToJson(i));
        }
        return tasklist;
    }
}
