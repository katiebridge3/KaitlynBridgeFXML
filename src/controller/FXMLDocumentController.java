/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;



/**
 *
 * @author kzb43
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
    //copied from SceneBuilder
    @FXML
    private Button buttonCreateCourse;

    @FXML
    private Button buttonReadCourse;

    @FXML
    private Button buttonUpdateCourse;

    @FXML
    private Button buttonDeleteCourse;
    
    @FXML
    private Button buttonReadByNameAndID;
    
    @FXML
    private Button buttonReadBetweenCredits;
    
    @FXML
    private TextField enterName;
    
     @FXML
    private TableView<model.Coursemodel> courseTable;
    @FXML
    private TableColumn<model.Coursemodel, Integer> courseId;
    @FXML
    private TableColumn<model.Coursemodel, String> courseName;
    @FXML
    private TableColumn<model.Coursemodel, Integer> courseSection;
    
    @FXML
    private TableColumn<model.Coursemodel, Integer> courseCredits;

    
    //Quiz #4
    
    
     // code adapted from source
    private ObservableList<model.Coursemodel> courseData;

    // add the proper data to the observable list to be rendered in the table
    public void setTableData(List<model.Coursemodel> courseList) {

        // initialize the studentData variable
        courseData = FXCollections.observableArrayList();

        // add the student objects to an observable list object for use with the GUI table
        courseList.forEach(c -> {
            courseData.add(c);
        });

        // set the the table items to the data in studentData; refresh the table
        courseTable.setItems(courseData);
        courseTable.refresh();
    }

    
    //code adapted from source
    @FXML
    void searchByName(ActionEvent event) {
        System.out.println("clicked");
        
        //get name from text box
        String name = enterName.getText();
        
        List<model.Coursemodel> courses = readByName(name);

        if (courses == null || courses.isEmpty()) {

            // show an alert to inform user 
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog Box");
            alert.setHeaderText("This is header section to write heading");
            alert.setContentText("No course");
            alert.showAndWait();
        } else {

            // setting table data
            setTableData(courses);
        }
        
    }
    
    //code modified from source
    @FXML
    void searchByNameAdvancedAction(ActionEvent event) {
        System.out.println("clicked");

        //getting the name from text box        
        String name = enterName.getText();

        //call a db read operaiton, readByName
        List<model.Coursemodel> courses = readByNameAdvanced(name);

        
        //alert
        if (courses == null || courses.isEmpty()) {

            // show an alert to inform user 
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog Box");
            alert.setHeaderText("This is header section to write heading");
            alert.setContentText("No course");
            alert.showAndWait();
        } else {
            //set table data
            setTableData(courses);
        }
        
    }
    
    @FXML
    //code modified from source code
    void createCourse(ActionEvent event) {
        
        Scanner input = new Scanner(System.in);
        
        // read input from cmd line
        System.out.println("Enter Course ID:");
        int id = input.nextInt();
        
        System.out.println("Enter Course Name:");
        String name = input.next();
        
        
        
        System.out.println("Enter Course Credits:");
        int credits = input.nextInt();
        
        System.out.println("Enter Course Section Number:");
        int section = input.nextInt();
        
        // create an instance of course
        model.Coursemodel course = new model.Coursemodel();
        
        // set properties
        course.setCourseid(id);
        course.setCoursename(name);
        course.setCoursesection(section);
        course.setCredits(credits);
        
        // save to db      
        create(course);

    }

    @FXML
    //code modified from source code
    void deleteCourse(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
         // read input from command line
        System.out.println("Enter Course ID:");
        int id = input.nextInt();
        
        model.Coursemodel c = readById(id);
        System.out.println("we are deleting this course: "+ c.toString());
        delete(c);
    }

    @FXML
    //code modified from source code
    void readCourse(ActionEvent event) {
        readAll();
    }

    //modified from source
    @FXML
    void readByNameId(ActionEvent event) {
        // name and id
        
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        
        System.out.println("Enter Course Name:");
        String name = input.next();
        
        System.out.println("Enter Course ID:");
        int id = input.nextInt();
        
        // create a student instance      
        List<model.Coursemodel> course =  readByNameAndID(name, id);

    }
    
    @FXML 
    void readBetweenCredits (ActionEvent event){
        Scanner input = new Scanner(System.in);
        
        // read input from cmd line
        System.out.println("Enter Lower Bound Credits:");
        int lcredits = input.nextInt();
        
        System.out.println("Enter Hiigher Bound Credits:");
        int hcredits = input.nextInt();
        
        List<model.Coursemodel> course = readBtwnCredits(lcredits, hcredits);
        
    }
    
    
    
    @FXML
    //code modified from source code
    void updateCourse(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        // read input from cmd line
        System.out.println("Enter Course ID:");
        int id = input.nextInt();
        
        System.out.println("Enter Course Name:");
        String name = input.next();
        
        System.out.println("Enter Course Section:");
        int section = input.nextInt();
        
        System.out.println("Enter Course Credits:");
        int credits = input.nextInt();
        
        // create an instance of course
        model.Coursemodel course = new model.Coursemodel();
        
        // set properties
        course.setCourseid(id);
        course.setCoursename(name);
        course.setCoursesection(section);
        course.setCredits(credits);

        
        // save to db     
        update(course);
    }
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    //code used from source
    EntityManager manager;
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        //code used from source
        manager = (EntityManager) Persistence.createEntityManagerFactory("KaitlynBridgeFXMLPU").createEntityManager();
        
        courseId.setCellValueFactory(new PropertyValueFactory<>("courseid"));

        courseName.setCellValueFactory(new PropertyValueFactory<>("coursename"));
        
        
        courseSection.setCellValueFactory(new PropertyValueFactory<>("coursesection"));

        
        courseCredits.setCellValueFactory(new PropertyValueFactory<>("credits"));

        //eanble row selection
        // (SelectionMode.MULTIPLE);
        courseTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        
    } 
    
    //code used and modified from source code
    public void create(model.Coursemodel course) {
        try {
            
            manager.getTransaction().begin();
            
            
            if (course.getCourseid() != null) {
                
                // create course
                manager.persist(course);
                
                // end transaction
                manager.getTransaction().commit();
                
                System.out.println(course.toString() + " is created");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    //code used and modified from source code
     public List<model.Coursemodel> readAll(){
        Query query = manager.createNamedQuery("Coursemodel.findAll");
        List<model.Coursemodel> course = query.getResultList();

        for (model.Coursemodel c : course) {
            System.out.println(c.getCourseid() + " " + c.getCoursename() + " " + c.getCoursesection() + " " + c.getCredits());
        }
        
        return course;
    }
     
    //code used and modified from source code
     
     public void update(model.Coursemodel model) {
        try {

            model.Coursemodel existingCourse = manager.find(model.Coursemodel.class, model.getCourseid());

            if (existingCourse != null) {
                manager.getTransaction().begin();
                
                // update values
                existingCourse.setCoursename(model.getCoursename());
                existingCourse.setCredits(model.getCredits());
                existingCourse.setCoursesection(model.getCoursesection());
                
                // end
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
     
     //code modified and used from source
     //we need the readbyId method so that when we delete a course, we can
     //type in the primary key (the id) and then delete all the information
     //about the course given the id
     
     public model.Coursemodel readById(int id){
        Query query = manager.createNamedQuery("Coursemodel.findByCourseid");
        
        // setting query param
        query.setParameter("courseid", id);
        
        // execute 
        model.Coursemodel course = (model.Coursemodel) query.getSingleResult();
        if (course != null) {
            System.out.println(course.getCourseid() + " " + course.getCoursename() + " " + course.getCoursesection() + " " + course.getCredits());
        }
        
        //return desired course
        return course;
    }        
     
     //code used and modified from source code
     public List<model.Coursemodel> readByNameAndID(String name, int id){
        Query query = manager.createNamedQuery("Coursemodel.findByCourseidAndCoursename");
        
      
        
        // setting query parameter
        query.setParameter("courseid", id);
        query.setParameter("coursename", name);
        
        
        // execute query
        List<model.Coursemodel> course =  query.getResultList();
        for (model.Coursemodel c: course) {
            System.out.println(c.getCourseid() + " " + c.getCoursename() + " " + c.getCoursesection() + " " + c.getCredits());
        }
        
        return course;
    }
     
     public List<model.Coursemodel> readBtwnCredits(int lcredits, int hcredits){
        Query query = manager.createNamedQuery("Coursemodel.findByCourseBetweenCredits");
        
        // setting query parameter
        query.setParameter("startcredits", lcredits);
        query.setParameter("endcredits", hcredits);
        
        
        // execute query
        List<model.Coursemodel> course =  query.getResultList();
        for (model.Coursemodel c: course) {
            if(lcredits <= c.getCredits() && c.getCredits()<= hcredits){
                System.out.println(c.getCourseid() + " " + c.getCoursename() + " " + c.getCoursesection());
            }
        }
        
        return course;
    }
     
     //used and modified from source code
      public List<model.Coursemodel> readByName(String name) {
        Query query = manager.createNamedQuery("Coursemodel.findByCoursename");

        // setting query parameter
        query.setParameter("coursename", name);

        // execute query
        List<model.Coursemodel> courses = query.getResultList();
        for (model.Coursemodel c : courses) {
                System.out.println(c.getCourseid() + " " + c.getCredits() + " " + c.getCoursesection());
        }

        return courses;
    }
      
      //modified from source
      public List<model.Coursemodel> readByNameAdvanced(String name) {
        Query query = manager.createNamedQuery("Coursemodel.findByNameAdvanced");

        // setting query parameter
        query.setParameter("coursename", name);

        // execute query
        List<model.Coursemodel> courses = query.getResultList();
        for (model.Coursemodel c : courses) {
            System.out.println(c.getCourseid() + " " + c.getCredits() + " " + c.getCoursesection());
        }

        return courses;
    }
     
     //code used and modified from source code
    public void delete(model.Coursemodel course) {
        try {
            model.Coursemodel existingCourse = manager.find(model.Coursemodel.class, course.getCourseid());

            
            if (existingCourse != null) {
                
                // begin 
                manager.getTransaction().begin();
                
                //remove course
                manager.remove(existingCourse);
                
                // end 
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
