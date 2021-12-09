import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WebScraper {
    private final static String websiteUrl = "https://macedonian-hotels.mk/";
    private List<City> citiesInMacedonia = new ArrayList<>();
    private Map<String, List<Hotel>> listHotelsInCity = new HashMap<>();
    private static final String jdbcURL = "jdbc:postgresql://localhost:5500/postgres?currentSchema=hotel";
    private static final String username = "postgres";
    private static final String password = "1q2w3e4r5t";

    private List<City> getCitiesInMacedonia() throws IOException {
        Document document = Jsoup.connect(websiteUrl).get();
        Elements body = document.select(".panel-body .destinations ul li");
        return body.stream()
                .map(city -> city.select("a span").text())
                .map(city -> new City(city, websiteUrl+"hotels/"+city
                        .toLowerCase()
                        .replace(" ","-")))
                .collect(Collectors.toList());
    }
    private String filterText(String text)
    {
        return text.replace("'","");
    }
    private List<String> scrapeHotelImages(Document document)
    {
        return document.select("#gallery1 li")
                .stream()
                .map(e -> e.select("a").attr("href"))
                .collect(Collectors.toList());
    }
    private Map<String,List<Hotel>> getHotelsInCities()
    {
        return citiesInMacedonia.stream().peek(city ->{
            final String url = city.getUrl();
            try {
                Document document = Jsoup.connect(url).get();
                int numberOfPages = document.select(".pagination li").size()/2-2;
                numberOfPages = numberOfPages<=0 ? 1 : numberOfPages;
                for(int i=1;i<=numberOfPages;i++){
                    Document specificCityOnPageDocument = Jsoup.connect(city.getUrl()+"?page="+ i).get();
                    Elements specificCityBody = specificCityOnPageDocument.select(".media-list li");
                    specificCityBody.forEach(hotel -> {
                        final String specificHotelWebsiteUrl = hotel.select("a").attr("href");
                        try {
                            Document specificHotelDocument = Jsoup.connect(specificHotelWebsiteUrl).get();
                            Map<String, String> hotelContactInfo = new HashMap<>();
                            final String hotelName = filterText(specificHotelDocument.select(".container h2 .heading").text());
                            final String hotelDescription = filterText(specificHotelDocument.select(".description span").text());
                            final Integer starsCount = specificHotelDocument.select(".star").text().length();
                            final String distanceFromCenter = specificHotelDocument.select(".blockquote-reverse p").text();
                            List<String> hotelImages = scrapeHotelImages(specificHotelDocument);
                            Elements contactHeader = specificHotelDocument.select("[itemprop=\"address\"] dl dt");
                            Elements contactInfo = specificHotelDocument.select("[itemprop=\"address\"] dl dd");
                            IntStream.range(0,contactHeader.size())
                                    .forEach(index -> hotelContactInfo.put(contactHeader.get(index).text(),contactInfo.get(index).text()));

                            Map<String, String> hotelFacilitiesInfo = new HashMap<>();
                            Element facilities = specificHotelDocument.select(".accommodation .row").get(0);
                            Elements facilitiesHeader = facilities.select("dl dt");
                            Elements facilitiesInfo = facilities.select("dl dd");
                            IntStream.range(0,facilitiesHeader.size())
                                            .forEach(index -> hotelFacilitiesInfo.put(facilitiesHeader.get(index).text(), facilitiesInfo.get(index).text().equals("-") ||
                                                    facilitiesInfo.get(index).text().isEmpty() ? null : filterText(facilitiesInfo.get(index).text())));

                            city.getHotels().add(new Hotel(specificHotelWebsiteUrl,hotelName, hotelDescription, hotelContactInfo
                                    , hotelImages, hotelFacilitiesInfo, starsCount, distanceFromCenter));
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    });
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }).collect(Collectors.toMap(City::getName, City::getHotels));
    }
    private void populateDatabaseWithCity(Connection connection, City c) throws SQLException {
        Statement stmt = connection.createStatement();
        final String createSql = "select * from city where name='"+c.getName()+"'";
        ResultSet result = stmt.executeQuery(createSql);
        if(!result.next())
        {
            String addCity = "insert into city (name) values ('"+c.getName()+"')";
            stmt.executeUpdate(addCity);
        }
    }
    private void populateDatabaseWithHotels(Connection connection, City c) throws SQLException {
        Statement stmt = connection.createStatement();

        final String createSql = "select * from city where name='"+c.getName()+"'";
        ResultSet result = stmt.executeQuery(createSql);
        result.next();
        final int id_city = result.getInt("id_city");

        c.getHotels().forEach(h -> {
            String createSqlHotel = "select * from hotel where name='"+h.getHotelName()+"'";
            try {
                ResultSet hotelResult = stmt.executeQuery(createSqlHotel);
                if(!hotelResult.next())
                {
                    String addHotel = "insert into hotel (id_city, name, description, address, phone, fax, website," +
                            "facilities, room_facilities, check_in, check_out, pets, stars, center_distance)" +
                            "values ("+ id_city +", " +
                            "'"+h.getHotelName()+"', "+
                            "'"+h.getHotelDescription()+"', "+
                            "'"+h.getHotelContactInfo().get("address")+"', "+
                            "'"+h.getHotelContactInfo().get("phone")+"', "+
                            "'"+h.getHotelContactInfo().get("fax")+"', "+
                            "'"+h.getHotelContactInfo().get("web")+"', "+
                            "'"+h.getHotelFacilities().get("facilities")+"', "+
                            "'"+h.getHotelFacilities().get("room facilities")+"', "+
                            "'"+h.getHotelFacilities().get("checkin")+"', "+
                            "'"+h.getHotelFacilities().get("checkout")+"', "+
                            "'"+h.getHotelFacilities().get("pets")+"', "+
                            "'"+h.getStars()+"', "+
                            "'"+h.getCenterDistance()+"')";

                    stmt.executeUpdate(addHotel);

                    createSqlHotel = "select * from hotel where name='"+h.getHotelName()+"'";
                    hotelResult = stmt.executeQuery(createSqlHotel);
                    if(hotelResult.next())
                    {
                        final int id_hotel = hotelResult.getInt("id_hotel");
                        h.getHotelImages().forEach(image -> {
                            String addImage = "insert into image (id_hotel, url) values("+id_hotel+", '"+image+"')";
                            try {
                                stmt.executeUpdate(addImage);
                            } catch (SQLException e) {
                                System.out.println(e.getMessage());
                            }
                        });
                    }

                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        });
    }
    private void populateDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection(jdbcURL,username,password);
        citiesInMacedonia.forEach(c -> {
            try {
                populateDatabaseWithCity(connection,c);
                populateDatabaseWithHotels(connection,c);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        });
        connection.close();
    }
    //TEST
    private void showHotelsInCity() throws SQLException {
        Connection connection = DriverManager.getConnection(jdbcURL,username,password);
        Statement stmt = connection.createStatement();

        final String createSql = "select * from hotel where id_city=8";
        ResultSet result = stmt.executeQuery(createSql);
        while(result.next())
        {
            System.out.println(result.getString("name"));
            System.out.println(result.getString("description"));
            System.out.println(result.getString("address"));
            System.out.println(result.getString("fax"));
            System.out.println(result.getString("phone"));
            System.out.println(result.getString("website"));
        }
    }
    public static void main(String[] args) throws IOException, SQLException {
        WebScraper webScraper = new WebScraper();
        webScraper.citiesInMacedonia = webScraper.getCitiesInMacedonia();
//        List<City> test = new ArrayList<>();
//        test.add(webScraper.citiesInMacedonia.get(8));
//        test.add(webScraper.citiesInMacedonia.get(7));
//        test.add(webScraper.citiesInMacedonia.get(18));
//        test.add(webScraper.citiesInMacedonia.get(20));
//        webScraper.citiesInMacedonia = test;
        webScraper.listHotelsInCity = webScraper.getHotelsInCities();
        //webScraper.showHotelsInCity();
        webScraper.populateDatabase();
    }
}