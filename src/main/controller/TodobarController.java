package controller;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRippler;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import model.Task;
import ui.ListView;
import ui.PomoTodoApp;
import ui.EditTask;
import utility.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

// Controller class for Todobar UI
public class TodobarController implements Initializable {
    private static final String todoOptionsPopUpFXML = "resources/fxml/TodoOptionsPopUp.fxml";
    private static final String todoActionsPopUpFXML = "resources/fxml/TodoActionsPopUp.fxml";
    private File optionsPopUpfxmlFile = new File(todoOptionsPopUpFXML);
    private File actionsPopUpfxmlFile = new File(todoActionsPopUpFXML);
    
    @FXML
    private Label descriptionLabel;
    @FXML
    private JFXHamburger todoActionsPopUpBurger;
    @FXML
    private StackPane todoActionsPopUpContainer;
    @FXML
    private JFXRippler todoOptionsPopUpRippler;
    @FXML
    private StackPane todoOptionsPopUpBurger;

    private JFXPopup todoOptionsPopUp;
    private JFXPopup todoActionsPopUp;
    
    private Task task;
    
    // REQUIRES: task != null
    // MODIFIES: this
    // EFFECTS: sets the task in this Todobar
    //          updates the Todobar UI label to task's description
    public void setTask(Task task) {
        this.task = task;
        descriptionLabel.setText(task.getDescription());
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTodoOptionsPopUp();
        loadtodoOptionsPopUpActionListener();
        loadActionsPopUp();
        loadtodoActionsPopUpActionListener();

    }

    // EFFECTS: load options pop up (??)
    private void loadTodoOptionsPopUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(optionsPopUpfxmlFile.toURI().toURL());
            fxmlLoader.setController(new OptionsPopUpController());
            todoOptionsPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void loadActionsPopUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(actionsPopUpfxmlFile.toURI().toURL());
            fxmlLoader.setController(new ActionsPopUpController());
            todoActionsPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    // EFFECTS: show view selector pop up when its icon is clicked
    private void loadtodoOptionsPopUpActionListener() {
        todoOptionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                todoOptionsPopUp.show(todoOptionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.LEFT,
                        12,
                        15);
            }
        });
    }

    // EFFECTS: show options pop up when its icon is clicked
    private void loadtodoActionsPopUpActionListener() {
        todoActionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                todoActionsPopUp.show(todoActionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.RIGHT,
                        -12,
                        15);
            }
        });
    }




    class OptionsPopUpController {
        @FXML
        private JFXListView<?> optionPopUpList;

        @FXML
        private void submit() {
            int selectedIndex = optionPopUpList.getSelectionModel().getSelectedIndex();
            switch (selectedIndex) {
                case 0:
                    Logger.log("OptionsPopUpController", "Task editing begun.");
                    PomoTodoApp.setScene(new EditTask(task));

                    break;
                case 1:
                    for (int i = 0; i < 2; i++) { //I don't know why, but I gotta call remove at least twice.
                        PomoTodoApp.getTasks().remove(task);
                    }
                    PomoTodoApp.setScene(new ListView(PomoTodoApp.getTasks())); //imitating AddButton class
                    Logger.log("OptionsPopUpController", "Task deleted, and list updated.");
                    break;
                default:
                    Logger.log("OptionsPopUpController", "No action is implemented for the selected option");
            }
            todoOptionsPopUp.hide(); //IDK
        }
    }

    class ActionsPopUpController {
        @FXML
        private JFXListView<?> actionPopUpList;

        @FXML
        private void submit() {
            int selectedIndex = actionPopUpList.getSelectionModel().getSelectedIndex();
            switch (selectedIndex) {
                case 0:
                    Logger.log("ActionsPopUpController", "Functionality not yet implemented.");
                    break;
                default:
                    Logger.log("ActionsPopUpController", "No action is implemented for the selected option");
            }
            todoActionsPopUp.hide();
        }
    }

}
