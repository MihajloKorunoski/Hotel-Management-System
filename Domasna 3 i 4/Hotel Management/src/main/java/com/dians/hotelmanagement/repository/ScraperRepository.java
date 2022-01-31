package com.dians.hotelmanagement.repository;

import com.dians.hotelmanagement.model.City;
import com.dians.hotelmanagement.model.Hotel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class ScraperRepository {
    private final CityRepository cityRepository;
    private final HotelRepository hotelRepository;
    private final static String websiteUrl = "https://macedonian-hotels.mk/";

    public ScraperRepository(CityRepository cityRepository, HotelRepository hotelRepository) {
        this.cityRepository = cityRepository;
        this.hotelRepository = hotelRepository;
    }

    private String filterText(String text) {
        return text.replace("'", "");
    }

    private List<String> scrapeHotelImages(Document document) {
        return document.select("#gallery1 li")
                .stream()
                .map(e -> e.select("a").attr("href"))
                .collect(Collectors.toList());
    }

    //Scrapes all hotels from the https://macedonian-hotels.mk/hotels/cityName
    //The hotels can be distributed on multiple pages on the website
    public List<Hotel> scrapeAllHotelsInCity() {
        List<Hotel> hotels = new ArrayList<>();
        this.cityRepository.findAll().forEach(city -> {
                    final String url = city.getWebsite();
                    try {
                        Document document = Jsoup.connect(url).get();
                        int numberOfPages = document.select(".pagination li").size() / 2 - 2;
                        numberOfPages = numberOfPages <= 0 ? 1 : numberOfPages;
                        //iterating all the pages
                        for (int i = 1; i <= numberOfPages; i++) {
                            Document specificCityOnPageDocument = Jsoup.connect(city.getWebsite() + "?page=" + i).get();
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
                                    IntStream.range(0, contactHeader.size())
                                            .forEach(index -> hotelContactInfo.put(contactHeader.get(index).text(), contactInfo.get(index).text()));

                                    Map<String, String> hotelFacilitiesInfo = new HashMap<>();
                                    Element facilities = specificHotelDocument.select(".accommodation .row").get(0);
                                    Elements facilitiesHeader = facilities.select("dl dt");
                                    Elements facilitiesInfo = facilities.select("dl dd");
                                    IntStream.range(0, facilitiesHeader.size())
                                            .forEach(index -> hotelFacilitiesInfo.put(facilitiesHeader.get(index).text(), facilitiesInfo.get(index).text().equals("-") ||
                                                    facilitiesInfo.get(index).text().isEmpty() ? null : filterText(facilitiesInfo.get(index).text())));

                                    Optional<DataNode> rawHtml = specificHotelDocument.getElementsByTag("script").stream()
                                            .flatMap(t -> t.dataNodes().stream())
                                            .filter(t -> t.getWholeData().contains("position"))
                                            .findFirst();
                                    int indexCoordinates = rawHtml.toString().indexOf("LatLng");
                                    String[] coordinates = rawHtml.toString()
                                            .substring(indexCoordinates + 7, indexCoordinates + 27)
                                            .split(",");
                                    double longitude = Double.parseDouble(coordinates[0]);
                                    double latitude = Double.parseDouble(coordinates[1]);
                                    Hotel dbHotel = this.hotelRepository.findHotelByCityNameAndName(city.getName(), hotelName);

                                    if (dbHotel == null) {
                                        Hotel saveHotel = new Hotel(hotelName, hotelDescription, hotelContactInfo.get("address"),
                                                hotelContactInfo.get("phone"), hotelContactInfo.get("fax"), hotelContactInfo.get("web"),
                                                hotelFacilitiesInfo.get("facilities"), hotelFacilitiesInfo.get("room facilities"),
                                                hotelFacilitiesInfo.get("checkin"), hotelFacilitiesInfo.get("checkout"),
                                                hotelFacilitiesInfo.get("pets"), distanceFromCenter, starsCount, hotelImages,
                                                longitude, latitude);
                                        saveHotel.setCity(city);
                                        hotels.add(saveHotel);
                                    }
                                } catch (IOException e) {
                                    System.out.println(e.getMessage());
                                }
                            });
                        }
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                });
        return hotels;
    }

    public List<City> scrapeAllCities() throws IOException {
        return Jsoup.connect(websiteUrl).get()
                .select(".panel-body .destinations ul li")
                .stream()
                .map(city -> city.select("a span").text())
                .map(city -> new City(city, websiteUrl + "hotels/" + city
                        .toLowerCase()
                        .replace(" ", "-")))
                .filter(city -> this.cityRepository.findByName(city.getName()) == null)
                .collect(Collectors.toList());
    }
}
