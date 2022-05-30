package model;

import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestTask {
    Task task1;
    Tag tag1;

    @BeforeEach
    public void setup() {
        task1 = new Task("A description. ## tomorrow; urgent; in progress");
        tag1 = new Tag("Ahoy");


    }

    //Phase 5 tests
    @Test
    public void ETAzero() {
        assertEquals(0,task1.getEstimatedTimeToComplete());
    }

    @Test
    public void Progresszero() {
        assertEquals(0,task1.getProgress());
    }


    //

    /////Add/remove tests

    //bidirectional containment test
    @Test
    public void addTagbidirecitonal() {
        task1.addTag(tag1);
        assertTrue(tag1.containsTask(task1));
    }

    @Test
    public void removeTagbidirectional() {
        task1.addTag(tag1);
        assertTrue(tag1.containsTask(task1));
        task1.removeTag(tag1);
        assertFalse(tag1.containsTask(task1));
        assertFalse(task1.containsTag(tag1));
    }


    @Test
    public void addNotYetContained() {
        assertFalse(task1.containsTag(tag1));
        task1.addTag(tag1);
        assertTrue(task1.containsTag(tag1));
    }

    @Test
    public void addTwo() {
        task1.addTag(tag1);
        task1.addTag(new Tag("Hey"));
        assertTrue(task1.containsTag(tag1));
        assertTrue(task1.containsTag("Hey"));
    }

    @Test
    public void addAlreadyContained() {
        task1.addTag(tag1);
        task1.addTag(tag1);
        assertEquals(task1.getTags().size(), 1);
        assertTrue(task1.containsTag("Ahoy"));
    }

    @Test
    public void addMultipleSuccess() {
        task1.addTag(tag1);
        task1.addTag(new Tag("Hello"));
        task1.addTag(new Tag("Friend"));
        assertEquals(task1.getTags().size(), 3);
        assertTrue(task1.containsTag("Friend"));
        assertTrue(task1.containsTag("Ahoy"));
        assertTrue(task1.containsTag("Hello"));
    }

    @Test
    public void addMultipleFail() {
        task1.addTag(tag1);
        task1.addTag("Hello");
        task1.addTag("Ahoy");
        assertEquals(task1.getTags().size(), 2);
        assertTrue(task1.containsTag("Ahoy"));
        assertTrue(task1.containsTag("Hello"));
    }

    @Test
    public void removeOneSuccessInside() {
        task1.addTag(tag1);
        task1.removeTag(tag1);
        assertTrue(task1.getTags().isEmpty());
    }

    @Test
    public void removeOneSuccessNotInside() {
        task1.removeTag(tag1);
        assertTrue(task1.getTags().isEmpty());
    }

    @Test
    public void removeOneSuccessMany() {
        task1.addTag(tag1);
        task1.addTag("Hello");
        task1.addTag("Friend");
        task1.removeTag("Hello");
        assertEquals(task1.getTags().size(), 2);
        assertTrue(task1.containsTag(tag1));
        assertTrue(task1.containsTag("Friend"));
    }

    @Test
    public void removeFailMany() {
        task1.addTag("Hello");
        task1.addTag("Friend");
        task1.removeTag(tag1);
        assertEquals(task1.getTags().size(), 2);
        assertTrue(task1.containsTag("Hello"));
        assertTrue(task1.containsTag("Friend"));
    }

    @Test
    public void removeFailOne() {
        task1.addTag(tag1);
        task1.removeTag("Hello");
        assertEquals(task1.getTags().size(), 1);
        assertTrue(task1.containsTag(tag1));
    }

    @Test
    public void removeSuccessSameNameReference() {
        task1.addTag(tag1);
        task1.removeTag("Ahoy");
        assertTrue(task1.getTags().isEmpty());
    }

    @Test
    public void addTagNullArgument() {
        Tag nullTag = null;
        try {
            task1.addTag(nullTag);
            fail("NullArgumentException not thrown.");
        } catch(NullArgumentException e) {

        }
    }

    @Test
    public void removeTagNullArgument() {
        Tag nullTag = null;
        try {
            task1.removeTag(nullTag);
            fail("NullArgumentException not thrown.");
        } catch(NullArgumentException e) {

        }
    }





    /////Equals tests

    @Test
    public void testequalssamedetails1() {
        Task task2a = new Task("A description. ## tomorrow; urgent; in progress; some tag");
        assertTrue(task1.equals(task2a));
    }

    @Test
    public void testequalssamedetails2() {
        Task task2b = new Task("A description. ## tomorrow; urgent; in progress");
        assertTrue(task1.equals(task2b));
    }

    @Test
    public void testequalsdifferentDescription() {
        Task task3 = new Task("Hello ## tomorrow; urgent; in progress");
        assertFalse(task1.equals(task3));
    }

    @Test
    public void testequalsdifferentDueDate() {
        Task task4 = new Task("A description. ## today; urgent; in progress");
        assertFalse(task1.equals(task4));
    }

    @Test
    public void testequalsdifferentPriority() {
        Task task5 = new Task("A description. ## tomorrow; urgent; todo");
        assertFalse(task1.equals(task5));
    }

    @Test
    public void testequalsdifferentStatus() {
        Task task6 = new Task("A description. ## tomorrow; important; in progress");
        assertFalse(task1.equals(task6));
    }

    @Test
    public void testequalsdifferentEverything() {
        Task task7 = new Task("Hey. ## today; important; up next");
        assertFalse(task1.equals(task7));
    }

    //phase 5 tests
    @Test
    public void setProgressboundserror() {
        try {
            task1.setProgress(-1);
            fail("No exception thrown");
        }
        catch(IllegalArgumentException e) {

        }
    }

    @Test
    public void setProgressboundserror2() {
        try {
            task1.setProgress(101);
            fail("No exception thrown");
        }
        catch(IllegalArgumentException e) {

        }
    }

    @Test
    public void setETAboundserror() {
        try {
            task1.setEstimatedTimeToComplete(-1);
            fail("No exception thrown");
        }
        catch(IllegalArgumentException e) {

        }
    }

    @Test
    public void setProgresstest() {
        task1.setProgress(50);
        assertEquals(50,task1.getProgress());
    }

    @Test
    public void setETAtest() {
        task1.setEstimatedTimeToComplete(5);
        assertEquals(5,task1.getEstimatedTimeToComplete());
    }



}

