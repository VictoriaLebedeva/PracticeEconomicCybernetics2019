import javafx.beans.property.SimpleStringProperty;

public class Film {
    private SimpleStringProperty film;
    private SimpleStringProperty country;
    private SimpleStringProperty year;
    private SimpleStringProperty genre;
    Film(String film, String country, String year, String genre) throws Exception {
        if (film == null || country == null || year == null || genre == null )
            throw new Exception("Not all fields are filled");
        this.film = new SimpleStringProperty(film);
        this.country = new SimpleStringProperty(country);
        this.year = new SimpleStringProperty(year);
        this.genre = new SimpleStringProperty(genre);
    }

    SimpleStringProperty getFieldBySQL(String sqlName) {
        SimpleStringProperty data;
        switch (sqlName) {
            case "Film":
                data = this.film;
                break;
            case "Country":
                data = this.country;
                break;
            case "Year":
                data = this.year; break;
            case "Genre":
                data = this.genre; break;
            default:
                data = null;
        }
        return data;
    }
    public String getFilm() {
        return film.get();
    }

    public String getCountry() {
        return country.get();
    }

    public String getYear() {
        return year.get();
    }

    public String getGenre() {
        return genre.get();
    }
}