import java.util.ArrayList;
import java.util.List;

public class City {
    private List<Hotel> hotels;
    private String url;
    private String name;
    public City(String name, String url)
    {
        hotels = new ArrayList<>();
        this.name = name;
        this.url = url;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }
}
