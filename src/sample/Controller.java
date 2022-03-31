package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField textFieldID;

    @FXML
    private TextField textFieldTittle;

    @FXML
    private TextField textFieldAuthor;

    @FXML
    private TextField textFieldYear;

    @FXML
    private TextField textFieldPages;

    @FXML
    private TableView<Book> tableViewBooks;

    @FXML
    private TableColumn<Book, Integer> columnID;

    @FXML
    private TableColumn<Book, String> columnTitle;

    @FXML
    private TableColumn<Book, String> columnAuthor;

    @FXML
    private TableColumn<Book, Integer> columnYear;

    @FXML
    private TableColumn<Book, Integer> columnPages;

    @FXML
    private Button buttonInsert;

    @FXML
    private Button buttonDelete;

    @FXML
    private Button buttonUpdate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showBooks();
    }

    public Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");
            return conn;
        } catch (Exception ex) {
            System.out.println("Error : " + ex.getMessage());
            return null;
        }
    }

    public ObservableList<Book> getBookList() {
        ObservableList<Book> bookList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM books";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Book book;
            while (rs.next()) {
                book = new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getInt("year"), rs.getInt("pages"));
                bookList.add(book);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return bookList;
    }

    public void showBooks() {
        ObservableList<Book> list = getBookList();

        columnID.setCellValueFactory(new PropertyValueFactory<Book, Integer>("id"));
        columnTitle.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        columnAuthor.setCellValueFactory((new PropertyValueFactory<Book, String>("author")));
        columnYear.setCellValueFactory(new PropertyValueFactory<Book, Integer>("year"));
        columnPages.setCellValueFactory(new PropertyValueFactory<Book, Integer>("pages"));

        tableViewBooks.setItems(list);
    }

    @FXML
    private void actionDelete(ActionEvent event) {
        String query = "DELETE FROM books WHERE id = " + textFieldID.getText()  + ";";
        executeQuery(query);
        showBooks();
    }

    @FXML
    private void actionInsert(ActionEvent event) {
        String query = "INSERT INTO books VALUES (" + textFieldID.getText() + ", '" + textFieldTittle.getText() + "', '" + textFieldAuthor.getText() + "', " + textFieldYear.getText() + ", " + textFieldPages.getText() + ");";
        executeQuery(query);
        showBooks();
    }

    @FXML
    private void actionUpdate(ActionEvent event) {
        String query = "UPDATE books SET title = '" + textFieldTittle.getText() + "', author = '" + textFieldAuthor.getText() + "', year = " + textFieldYear.getText() + ", pages = " + textFieldPages.getText() + " WHERE id = " + textFieldID.getText()  + ";";
        executeQuery(query);
        showBooks();
    }

    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void actionMouseClick(MouseEvent mouseEvent) {
        Book book = tableViewBooks.getSelectionModel().getSelectedItem();

        textFieldID.setText("" + book.getId());
        textFieldTittle.setText(book.getTitle());
        textFieldAuthor.setText(book.getAuthor());
        textFieldYear.setText("" + book.getYear());
        textFieldPages.setText("" + book.getPages());
    }
}
