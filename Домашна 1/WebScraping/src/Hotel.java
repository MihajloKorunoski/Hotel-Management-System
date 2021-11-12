import java.util.List;
import java.util.Map;

public class Hotel
{
    private final String hotelUrl;
    private final String hotelName;
    private final String hotelDescription;
    private Map<String,String> hotelContactInfo;
    private List<String> hotelImages;
    private Map<String,String> hotelFacilities;
    private Integer stars;
    private String centerDistance;
    public Hotel(String hotelUrl, String hotelName, String hotelDescription, Map<String, String> hotelContactInfo
            , List<String> hotelImages
            , Map<String, String> hotelFacilities, Integer stars, String centerDistance) {
        this.hotelUrl = hotelUrl;
        this.hotelName = hotelName;
        this.hotelDescription = hotelDescription;
        this.hotelContactInfo = hotelContactInfo;
        this.hotelImages = hotelImages;
        this.hotelFacilities = hotelFacilities;
        this.stars = stars;
        this.centerDistance = centerDistance;
    }

    public Integer getStars() {
        return stars;
    }

    public String getCenterDistance() {
        return centerDistance;
    }

    public Map<String, String> getHotelFacilities() {
        return hotelFacilities;
    }

    public List<String> getHotelImages() {
        return hotelImages;
    }

    public void setHotelContactInfo(Map<String, String> hotelContactInfo) {
        this.hotelContactInfo = hotelContactInfo;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getHotelDescription() {
        return hotelDescription;
    }

    public Map<String, String> getHotelContactInfo() {
        return hotelContactInfo;
    }
    @Override
    public String toString()
    {
        return String.format("%s\n%s\n%s\n\n",hotelName,hotelDescription,hotelContactInfo);
    }
}
