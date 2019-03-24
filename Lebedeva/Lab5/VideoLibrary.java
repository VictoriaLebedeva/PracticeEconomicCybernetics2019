public class VideoLibrary {

    private final String[] parameterNames = { "Name of the film", "Year", "Director", "Genre" };
    private String genre;
    private String[] parameters;

    public VideoLibrary(String description) {
        parameters = description.split("  ");
        genre = parameters[parameters.length - 1];
          }

    public String[] getParameters() {
        return parameters;
    }

    public String[] getParameterNames() {
        return parameterNames;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return  parameters[0];
    }

    @Override
    public boolean equals(Object o) {
        VideoLibrary obj = (VideoLibrary) o;
        if (!genre.equals(obj.genre)) {
            return false;
        }
        if (!parameters[0].equals(obj.parameters[0])) {
            return false;
        }
        return true;
    }
}