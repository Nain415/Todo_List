package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;

import java.util.*;

// Represents a Project, a collection of zero or more Tasks
// Class Invariant: no duplicated task; order of tasks is preserved
public class Project extends Todo implements Iterable<Todo> {
    //private String description;
    private List<Todo> tasks;

    // MODIFIES: this
    // EFFECTS: constructs a project with the given description
    //     the constructed project shall have no tasks.
    //  throws EmptyStringException if description is null or empty
    public Project(String description) {
        super(description);
        if (description == null || description.length() == 0) {
            throw new EmptyStringException("Cannot construct a project with no description");
        }
        this.description = description;
        tasks = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: task is added to this project (if it was not already part of it)
    //   throws NullArgumentException when task is null
    public void add(Todo task) {
        if (!contains(task) && !task.equals(this)) {
            tasks.add(task);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes task from this project
    //   throws NullArgumentException when task is null
    public void remove(Todo task) {
        if (contains(task)) {
            tasks.remove(task);
        }
    }

//    // EFFECTS: returns the description of this project
//    public String getDescription() {
//        return description;
//    }


    @Deprecated
    public List<Task> getTasks() {
        throw new UnsupportedOperationException();
    }

    // EFFECTS: returns an integer between 0 and 100 which represents
//     the percentage of completion (rounded down to the nearest integer).
//     the value returned is the average of the percentage of completion of
//     all the tasks and sub-projects in this project.
    public int getProgress() {
        if (tasks.isEmpty()) {
            return 0;
        }
        int sum = 0;
        int count = 0;
        for (Todo i : tasks) {
            sum = sum + i.getProgress();
            count = count + 1;
        }
        return (int) Math.floor(sum / count);
    }


//    // EFFECTS: returns the number of completed tasks in this project
//    private int getNumberOfCompletedTasks() {
//        int done = 0;
//        for (Task t : tasks) {
//            if (t.getStatus() == Status.DONE) {
//                done++;
//            }
//        }
//        return done;
//    }

    // EFFECTS: returns the number of tasks (and sub-projects) in this project
    public int getNumberOfTasks() {
        return tasks.size();
    }

    // EFFECTS: returns true if every task (and sub-project) in this project is completed, and false otherwise
//     If this project has no tasks (or sub-projects), return false.
    public boolean isCompleted() {
        return getNumberOfTasks() != 0 && getProgress() == 100;
    }

    // EFFECTS: returns true if this project contains the task
    //   throws NullArgumentException when task is null
    public boolean contains(Todo task) {
        if (task == null) {
            throw new NullArgumentException("Illegal argument: task is null");
        }
        return tasks.contains(task);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        Project project = (Project) o;
        return Objects.equals(description, project.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    //phase 4 stuff

    @Override
    //EFFECTS: Returns the sum-eta of all tasks in project and sub-projects.
    public int getEstimatedTimeToComplete() {
        int sum = 0;
        for (Todo i : tasks) {
            sum = sum + i.getEstimatedTimeToComplete();
        }
        return sum;
    }

    @Override
    public Iterator<Todo> iterator() {
        return new PriorityIterator();
    }

    private class PriorityIterator implements Iterator<Todo> {

        Iterator todoIterator = tasks.iterator();
        boolean notdone = true;
        int index = 0;
        int pr = 1;
        int next;

        @Override
        //EFFECTS: Returns true if a next Todo exists with priority <= previous Todo, else false.
        //MODIFIES: index and next variables; index of found Todo is assigned.
        public boolean hasNext() {
            if (!tasks.isEmpty()) {
                next = nextbehaviour(pr, index);
                while (pr < 5) {
                    if (next > -1) {
                        index = next;
                        return true;
                    }
                    if (next == -1) {
                        index = 0;
                        pr++;
                        next = nextbehaviour(pr, index);
                    }
                }
            }
            return false;
        }


        @Override
        //EFFECTS: Returns Todo found in hasNext, and increments index to continue the search
        public Todo next() {
            index++;
            return tasks.get(index - 1);
        }


        //EFFECTS: Returns -1 if no Todo with given prio is found, else returns position of found Todo.
        public int nextbehaviour(int prio,int index) {
            while (index < tasks.size()) {
                Todo todo = tasks.get(index);
                if ((checklevel(todo)) == prio) {
                    return index;
                }
                index++;
            }
            return -1;
        }


        //EFFECTS: Returns an integer representing the given Todo's priority.
        public int checklevel(Todo todo) {
            Priority prio = todo.getPriority();
            if (prio.isImportant() && prio.isUrgent()) {
                return 1;
            }

            if (prio.isUrgent()) {
                return 3;
            }

            if (prio.isImportant()) {
                return 2;
            }

            return 4;
        }
    }
}