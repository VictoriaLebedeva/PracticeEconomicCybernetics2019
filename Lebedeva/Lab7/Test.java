import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;
import java.util.Optional;

public class Test extends Application {
    private static Connection connection;
    private static Driver driver;
    private static Statement statement;
    private static ResultSet resultSet;
    private TableView<Film> filmTable = new TableView<>();
    private TableView<Cast> castTable = new TableView<>();
    private static ObservableList<Film> filmData = FXCollections.observableArrayList();
    private static ObservableList<Cast> extraInfoData = FXCollections.observableArrayList();
    private final HBox addBox = new HBox(15);
    private int index = 1;

    public void start(Stage primaryStage) {
        Scene scene = new Scene(new Group());
        primaryStage.setTitle("Video Library");
        createFilmTable();
        createCastTable();
        createAddBox();
        VBox allBox = new VBox(10);
        allBox.setPadding(new Insets(10, 10, 10, 10));
        allBox.getChildren().addAll(filmTable, addBox, castTable);
        ((Group) scene.getRoot()).getChildren().addAll(allBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void createAddBox() {
        TextField nameField = new TextField();
        nameField.setMaxWidth(200);
        nameField.setPromptText("Film");
        TextField countryField = new TextField();
        countryField.setMaxWidth(300);
        countryField.setPromptText("Country");
        TextField yearField = new TextField();
        yearField.setMaxWidth(100);
        yearField.setPromptText("Year");
        TextField genreField = new TextField();
        genreField.setMaxWidth(200);
        genreField.setPromptText("Genre");
        Button addButton = new Button("ADD");
        addButton.setOnAction((ActionEvent e) -> {
            String film = nameField.getText();
            String country = countryField.getText();
            String year = yearField.getText();
            String genre = genreField.getText();
            try {
                Film newFilm = new Film(film, country, year, genre);
                String cast = CastDescriptionDialog(genre);
                Cast extraInfo = new Cast(film, ((Integer)(index++)).toString(), cast);
                addCastToDB(extraInfo);
                extraInfoData.add(extraInfo);
                filmData.add(newFilm);
                addFilmToDB(film, country, year, genre);
                nameField.clear();
                countryField.clear();
                yearField.clear();
                genreField.clear();
            } catch (Exception exception) {
                System.out.println(exception.toString());
            }
        });
        addBox.getChildren().addAll(nameField, countryField, yearField, genreField, addButton);
    }

    public void createFilmTable() {
        TableColumn<Film, String> nameColumn = new TableColumn<>("Film");
        setFilmColumnValues(nameColumn, 300, "Film");
        TableColumn<Film, String> countryColumn = new TableColumn<>("Country");
        setFilmColumnValues(countryColumn, 200, "Country");
        TableColumn<Film, String> yearColumn = new TableColumn<>("Year");
        setFilmColumnValues(yearColumn, 100,"Year");
        TableColumn<Film, String> typeColumn = new TableColumn<>("Genre");
        setFilmColumnValues(typeColumn, 200, "Genre");
        filmTable.setItems(filmData);
        filmTable.getColumns().addAll(nameColumn, countryColumn, yearColumn, typeColumn);
        filmTable.setMaxHeight(200);
    }

    public void createCastTable() {
        TableColumn<Cast, String> idColumn = new TableColumn<>("ID");
        TableColumn<Cast, String> filmColumn = new TableColumn<>("Film");
        TableColumn<Cast, String> castColumn = new TableColumn<>("Cast");
        setExtraInfoColumnValues(filmColumn, 200, "Film");
        setExtraInfoColumnValues(idColumn, 70, "ID");
        setExtraInfoColumnValues(castColumn, 530, "Cast");
        castTable.setItems(extraInfoData);
        castTable.getColumns().addAll(idColumn, filmColumn, castColumn);
        castTable.setMaxHeight(200);
    }

    public String CastDescriptionDialog(String film) {
        TextInputDialog dialog = new TextInputDialog("No description");
        dialog.setTitle("Add cast of the film");
        dialog.setHeaderText("Enter cast");
        Optional<String> result = dialog.showAndWait();
        return result.orElse("No description");
    }

    public void addCastToDB(Cast cast) {
        String sql = "INSERT INTO cast(film, cast) VALUES(?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, cast.getFilm());
            statement.setString(2, cast.getCast());
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setExtraInfoColumnValues(TableColumn<Cast, String> column, int width, String sqlName) {
        column.setPrefWidth(width);
        column.setCellValueFactory(param -> param.getValue().getFieldBySQL(sqlName));
    }

    public void setFilmColumnValues(TableColumn<Film, String> column, int width, String sqlName) {
        column.setPrefWidth(width);
        column.setCellValueFactory(param -> param.getValue().getFieldBySQL(sqlName));
    }

    public void addFilmToDB(String film, String country, String year, String genre) {
        String sql = "INSERT INTO recipes(film, country, year, genre) VALUES(?,?,?,?)";
        try  {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, film);
            statement.setString(2, country);
            statement.setString(3, year);
            statement.setString(4, genre);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/videolib", "root", "11111");
            statement = connection.createStatement();
            String selectSQL = "SELECT * FROM films";
            resultSet = statement.executeQuery(selectSQL);
            while (resultSet.next()) {
                Film recipe = new Film(resultSet.getString("Film"), resultSet.getString("Country"), resultSet.getString("Year"), resultSet.getString("Genre"));
                filmData.add(recipe);
            }
            selectSQL = "SELECT * FROM cast";
            resultSet = statement.executeQuery(selectSQL);
            while (resultSet.next()) {
                Cast cast = new Cast(resultSet.getString("Film"), resultSet.getString("ID"), resultSet.getString("Cast"));
                extraInfoData.add(cast);
            }
            statement.execute("CREATE TABLE IF NOT EXISTS 'films' " + "('film' TEXT REFERENCES cast(film)," + " 'country' TEXT, " + " 'year' TEXT," + " 'genre' TEXT DEFERRABLE INITIALLY DEFERRED);");
            statement.execute("CREATE TABLE IF NOT EXISTS 'cast' " + "('id' INTEGER PRIMARY KEY AUTOINCREMENT,  " + "'film' TEXT, " + "'cast' TEXT);");
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        launch();
        try {
            resultSet.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}