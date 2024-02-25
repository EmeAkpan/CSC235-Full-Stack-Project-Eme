package com.example.demo3;

import com.example.demo3.Person;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

/**
 * Controller class for the Hello.fxml file.
 */
public class HelloController {

    @FXML
    private Button clearButton;


    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;


    @FXML
    private TableView<Person> tableView;

    @FXML
    private TableColumn<Person, Integer> idColumn; // Added ID column

    @FXML
    private TableColumn<Person, String> firstNameColumn;

    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private TableColumn<Person, String> departmentColumn;

    @FXML
    private TableColumn<Person, String> majorColumn;

    @FXML
    private TableColumn<Person, String> emailNameColumn;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField departmentField;

    @FXML
    private TextField majorField;

    @FXML
    private TextField emailField;

    @FXML
    private AnchorPane paneID;

    @FXML
    private MenuItem close;

    @FXML
    private ImageView imageView;

    private Stage stage;

    private int idCounter = 1; // Counter for generating unique IDs

    /**
     * Sets the background color to red.
     */
    public void setBackgroundColorToRed() {
        paneID.setBackground(Background.fill(Color.RED));
    }

    /**
     * Sets the background color to blue.
     */
    public void setBackgroundColorToBlue() {
        paneID.setBackground(Background.fill(Color.BLUE));
    }

    /**
     * Sets the background color to brown.
     */
    public void setBackgroundColorToBrown() {
        paneID.setBackground(Background.fill(Color.rgb(150, 112, 63)));
    }

    /**
     * Initializes the controller after its root element has been completely processed.
     */
    public void initialize() {
        // Initialize TableView and columns
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject()); // Set ID column
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        departmentColumn.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
        majorColumn.setCellValueFactory(cellData -> cellData.getValue().majorProperty());
        emailNameColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        deleteButton.setOnAction(e -> clearSelectedRow());
        clearButton.setOnAction(e -> clearTable());
        editButton.setOnAction(e -> handleEditButton());
        // Call edit method to enable editing
        edit();

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Populate text fields with the data of the selected row
                firstNameField.setText(newSelection.getFirstName());
                lastNameField.setText(newSelection.getLastName());
                departmentField.setText(newSelection.getDepartment());
                majorField.setText(newSelection.getMajor());
                emailField.setText(newSelection.getEmail());
            }
        });
    }

    /**
     * Handles the addition of a new Person object to the TableView.
     */
    @FXML
    public void handleAddButton() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String department = departmentField.getText();
        String major = majorField.getText();
        String email = emailField.getText();

        // Create a new Person object with auto-incremented ID
        Person person = new Person(idCounter++, firstName, lastName, department, major, email);

        // Add the person to the TableView
        tableView.getItems().add(person);

        // Call auto-increment method
        autoIncrementId();

        // Clear the text fields after adding
        firstNameField.clear();
        lastNameField.clear();
        departmentField.clear();
        majorField.clear();
        emailField.clear();
    }

    /**
     * Clears the selected row from the TableView.
     */
    public void clearSelectedRow() {
        Person selectedPerson = tableView.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            // Get the index of the selected row
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();

            // Remove the selected person from the table
            tableView.getItems().remove(selectedPerson);

            // Call auto-increment method
            autoIncrementId();
        }
    }

    /**
     * Automatically increments the ID counter and updates the IDs in the TableView.
     */
    public void autoIncrementId() {
        // Reset idCounter to 1
        idCounter = 1;

        // Update IDs for each row in the table
        for (Person person : tableView.getItems()) {
            person.setId(idCounter++);
        }
    }


    /**
     * Clears all rows from the TableView.
     */
    public void clearTable() {
        tableView.getItems().clear();
        idCounter = 1; // Reset the ID counter when clearing the table
    }

    /**
     * Closes the application.
     * @param actionEvent The event triggering the method.
     */
    @FXML
    private void closeStage(javafx.event.ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    /**
     * Displays a help message.
     */
    @FXML
    public void helpMenuItem() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText("Need further assistance?");
        alert.setContentText("To get further assistance using this application, visit www.farmingdale.edu");
        alert.showAndWait();
    }

    /**
     * Enables editing functionality for TableView.
     */
    public void edit() {
        // Set columns to be editable with TextFieldTableCell
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        departmentColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        majorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Set cell value factories
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        departmentColumn.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
        majorColumn.setCellValueFactory(cellData -> cellData.getValue().majorProperty());
        emailNameColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        // Handle editing commit for first name
        firstNameColumn.setOnEditCommit(event -> {
            Person person = event.getRowValue();
            person.setFirstName(event.getNewValue());
        });

        // Handle editing commit for last name
        lastNameColumn.setOnEditCommit(event -> {
            Person person = event.getRowValue();
            person.setLastName(event.getNewValue());
        });

        // Handle editing commit for department
        departmentColumn.setOnEditCommit(event -> {
            Person person = event.getRowValue();
            person.setDepartment(event.getNewValue());
        });

        // Handle editing commit for major
        majorColumn.setOnEditCommit(event -> {
            Person person = event.getRowValue();
            person.setMajor(event.getNewValue());
        });

        // Handle editing commit for email
        emailNameColumn.setOnEditCommit(event -> {
            Person person = event.getRowValue();
            person.setEmail(event.getNewValue());
        });
    }

    /**
     * Handles the edit button action.
     */
    @FXML
    private void handleEditButton() {
        // Get the selected row
        Person selectedRow = tableView.getSelectionModel().getSelectedItem();

        if (selectedRow != null) {
            // Update the selected row with the edited values from the text fields
            selectedRow.setFirstName(firstNameField.getText());
            selectedRow.setLastName(lastNameField.getText());
            selectedRow.setDepartment(departmentField.getText());
            selectedRow.setMajor(majorField.getText());
            selectedRow.setEmail(emailField.getText());

            // Refresh the TableView to reflect the changes
            tableView.refresh();
            firstNameField.clear();
            lastNameField.clear();
            departmentField.clear();
            majorField.clear();
            emailField.clear();
        }
    }
}
