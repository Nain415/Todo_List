package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TestProject {

    Project project;
    Task task;
    Task task2;
    Task task3;
    Project project2;

    @BeforeEach
    public void setup() {
        project = new Project("test project");
        project2 = new Project("A new project");
        task = new Task("test task");
        task2 = new Task("second test task");
        task3 = new Task("third test task");
        task.setStatus(Status.DONE);
        task2.setStatus(Status.DONE);
        task3.setStatus(Status.TODO);
        task.addTag("Hello");
        task.addTag("Earthlings");
    }

    //    Phase 2 NullArgumentException tests.  Valid arguments are tested elsewhere.
    @Test
    public void testnulladd() {
        try {
            project.add(null);
            fail("Attempted addition of null Task to project did not throw NullArgumentException");
        } catch(NullArgumentException nae) {

        }
    }

    @Test
    public void testnullremove() {
        try {
            project.remove(null);
            fail("Attempted removal of null Task to project did not throw NullArgumentException");
        } catch(NullArgumentException nae) {

        }
    }

    @Test
    public void testnullcontains() {
        try {
            project.contains(null);
            fail("Null argument for contains() method did not throw NullArgumentException");
        } catch(NullArgumentException nae) {

        }
    }



    //    Phase 2 empty-string tests
    @Test
    public void testProjectconstructorvaliddescription() {
        project.getDescription().equals("test project");
    }

    @Test
    public void testProjectconstructornull() {
        try {
            Project nullproject = new Project(null);
            fail("Null description was not caught.");
        } catch (EmptyStringException ese) {
            System.out.println(ese.getMessage());
        }
    }

    @Test
    public void testProjectconstructoremptystring() {
        try {
            Project emptyproject = new Project("");
            fail("Empty description was not caught.");
        } catch (EmptyStringException ese) {
            System.out.println(ese.getMessage());
        }
    }


//    Phase 1 tests

    @Test
    public void add1Test() {
        assertFalse(project.contains(task));
        assertEquals(project.getNumberOfTasks(), 0);

        project.add(task);

        assertTrue(project.contains(task));
        assertEquals(1, project.getNumberOfTasks());

    }
    @Test
    public void addmultipleTest() {
        project.add(task);
        project.add(task2);
        assertEquals(project.getNumberOfTasks(), 2);
        assertTrue(project.contains(task));
        assertTrue(project.contains(task2));
    }

    @Test
    public void addDuplicateTest() {
        project.add(task);
        project.add(task);
        assertEquals(project.getNumberOfTasks(), 1);
        assertTrue(project.contains(task));
    }

    @Test
    public void removeempty() {
        assertEquals(project.getNumberOfTasks(),0);
        project.remove(task2);
        assertEquals(project.getNumberOfTasks(),0);
    }

    @Test
    public void removenotcontained() {
        project.add(task);
        assertEquals(project.getNumberOfTasks(),1);
        project.remove(task2);
        assertEquals(project.getNumberOfTasks(),1);
        assertTrue(project.contains(task));
    }

    @Test
    public void removeTagonly() {
        project.add(task);
        assertEquals(project.getNumberOfTasks(),1);
        project.remove(task);
        assertEquals(project.getNumberOfTasks(),0);
    }

    @Test
    public void removeTagmany() {
        project.add(task);
        project.add(task2);
        assertEquals(project.getNumberOfTasks(),2);
        project.remove(task);
        assertEquals(project.getNumberOfTasks(),1);
        project.contains(task2);
    }

    @Test
    public void getProgressTest0() {
        assertEquals(0, project.getProgress());
    }

    @Test
    public void getProgressTest1done() {
        task.setProgress(100);
        project.add(task);
        assertEquals(100, project.getProgress());
    }

    @Test
    public void getProgressTestundone() {
        project.add(task3);
        assertEquals(0, project.getProgress());
    }

    @Test
    public void getProgressTestmixed() {
        task.setProgress(5);
        task2.setProgress(66);
        task3.setProgress(54);
        project.add(task);
        project.add(task2);
        project.add(task3);
        assertEquals(41, project.getProgress());
    }

    @Test
    public void isCompletedemptytest() {
        assertFalse(project.isCompleted());
    }

    @Test
    public void isCompletednottest() {
        project.add(task3);
        assertFalse(project.isCompleted());
    }

    @Test
    public void isCompletetest() {
        task.setProgress(100);
        project.add(task);
        assertTrue(project.isCompleted());
    }

    @Test
    public void isComplete2test() {
        task.setProgress(100);
        task2.setProgress(100);
        project.add(task);
        project.add(task2);
        assertTrue(project.isCompleted());
    }

    @Test
    public void inCompletemixedtest() {
        task.setProgress(55);
        project.add(task);
        project.add(task2);
        project.add(task3);
        assertFalse(project.isCompleted());
    }

    @Test
    public void containsTestempty() {
        assertFalse(project.contains(task));
    }

    @Test
    public void containsTestone() {
        project.add(task);
        assertTrue(project.contains(task));
        assertFalse(project.contains(task2));
    }

    @Test
    public void containsTagTestmany() {
        project.add(task);
        project.add(task2);
        project.add(task3);
        assertTrue(project.contains(task3));
        assertTrue(project.contains(task));
        assertTrue(project.contains(task2));
    }
    //Phase 4 tests
    @Test
    public void AddSelfToProject() {
        project.add(project);
        assertEquals(0,project.getNumberOfTasks());
    }

    @Test
    public void AddProjecttoProject() {
        project.add(project2);
        assertEquals(1,project.getNumberOfTasks());
    }

    @Test
    public void getProgressnotaskand1emptytask() {
        assertEquals(0,project.getProgress());
        project.add(task);
        assertEquals(0,project.getProgress());
    }

    @Test
    public void getProgressmultipletask() {
        task.setProgress(100);
        task2.setProgress(50);
        project2.add(task);
        project2.add(task2);
        assertEquals(75,project2.getProgress());
    }

    @Test
    public void getProgresszerosubproject() {
        project.add(project2);
        assertEquals(1,project.getNumberOfTasks());
        assertEquals(0,project.getProgress());
    }

    @Test
    public void getProgresssubproject() {
        task.setProgress(100);
        task2.setProgress(50);
        project2.add(task);
        project2.add(task2);
        task3.setProgress(28);
        project.add(task3);
        project.add(project2);
        assertEquals(51,project.getProgress());
    }

    @Test
    public void getProgressonlysubproject() {
        task.setProgress(23);
        project2.add(task);
        project.add(project2);
        assertEquals(23/1,project.getProgress());
    }

    @Test
    public void getETAnotasks() {
        assertEquals(0,project.getEstimatedTimeToComplete());
    }

    @Test
    public void getETAonetask() {
        task.setEstimatedTimeToComplete(5);
        project.add(task);
        assertEquals(5,project.getEstimatedTimeToComplete());
    }

    @Test
    public void getETAtwotask() {
        task.setEstimatedTimeToComplete(3);
        task2.setEstimatedTimeToComplete(10);
        project.add(task);
        project.add(task2);
        assertEquals(13,project.getEstimatedTimeToComplete());
    }

    @Test
    public void getETAsubproject() {
        task.setEstimatedTimeToComplete(10);
        project2.add(task);

        project.add(project2);
        assertEquals(10,project.getEstimatedTimeToComplete());
    }

    @Test
    public void getETAsubprojectsandtasks() {
        task.setEstimatedTimeToComplete(6);
        task2.setEstimatedTimeToComplete(2);
        project2.add(task);
        project2.add(task2);

        task3.setEstimatedTimeToComplete(20);

        project.add(task3);
        project.add(project2);

        assertEquals(28,project.getEstimatedTimeToComplete());
    }

    @Test
    public void Iteratortest() {
        task.setPriority(new Priority(2));
        task2.setPriority(new Priority(2));
        task3.setPriority(new Priority(3));
        Task task4 = new Task("helloooooo");
        Task task5 = new Task("hellooodooo");
        task4.setPriority(new Priority(1));

        project.add(task2);
        project.add(task);
        project.add(task5);
        project.add(task4);
        project.add(task3);

        for (Todo i : project) {
            System.out.println(i.getPriority().toString());
        }
    }


}