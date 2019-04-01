import javafx.beans.property.SimpleStringProperty;

public class Cast {
    private SimpleStringProperty ID;
    private SimpleStringProperty film;
    private SimpleStringProperty cast;

    Cast(String film, String id, String cast) {
        this.ID = new SimpleStringProperty(id);
        this.film = new SimpleStringProperty(film);
        this.cast = new SimpleStringProperty(cast);
    }

    SimpleStringProperty getFieldBySQL(String sqlName) {
        SimpleStringProperty data;
        switch (sqlName) {
            case "Film":
                data = this.film;
                break;
            case "ID":
                data = this.ID;
                break;
            case "Cast":
                data = this.cast;
                break;
            default:
                data = null;
        }
        return data;
    }
    public String getFilm() {
        return film.get();
    }

    public String getID() {
        return ID.get();
    }

    public String getCast() {
        return cast.get();
    }
}