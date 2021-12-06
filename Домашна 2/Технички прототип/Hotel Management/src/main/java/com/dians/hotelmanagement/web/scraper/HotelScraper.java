package com.dians.hotelmanagement.web.scraper;

import com.dians.hotelmanagement.model.City;
import com.dians.hotelmanagement.model.Hotel;
import com.dians.hotelmanagement.repository.CityRepository;
import com.dians.hotelmanagement.repository.HotelRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Configuration
@EnableScheduling
public class HotelScraper {
    private final static String websiteUrl = "https://macedonian-hotels.mk/";
    private static final String jdbcURL = "jdbc:postgresql://localhost:5500/postgres?currentSchema=dians";
    private static final String username = "postgres";
    private static final String password = "1q2w3e4r5t";
    private List<City> citiesInMacedonia = new ArrayList<>();

    private final CityRepository cityRepository;
    private final HotelRepository hotelRepository;
    private final EntityManager entityManager;

    public HotelScraper(CityRepository cityRepository, HotelRepository hotelRepository, EntityManager entityManager) {
        this.cityRepository = cityRepository;
        this.hotelRepository = hotelRepository;
        this.entityManager = entityManager;
    }

    @Scheduled(fixedDelay = 20000000)
    public void scrapeNewHotels() throws IOException {
        //delegira kon service
        //service kon repository
//        Document document = (Document) Jsoup.connect(websiteUrl).get();
//        populateDatabaseWithCities(document.select(".panel-body .destinations ul li"));
//        populateDatabaseWithHotels();
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

    private void populateDatabaseWithHotels() {
        this.cityRepository.findAll().stream().forEach(city -> {
            final String url = city.getWebsite();
            try {
                Document document = Jsoup.connect(url).get();
                int numberOfPages = document.select(".pagination li").size() / 2 - 2;
                numberOfPages = numberOfPages <= 0 ? 1 : numberOfPages;
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

                            Optional<Hotel> dbHotel = this.hotelRepository.findAllByName(hotelName)
                                    .stream()
                                    .filter(h -> h.getCity().getId().equals(city.getId()))
                                    .findFirst();
                            if(dbHotel.isEmpty())
                            {
                                Hotel saveHotel = new Hotel(hotelName, hotelDescription, hotelContactInfo.get("address"),
                                        hotelContactInfo.get("phone"), hotelContactInfo.get("fax"), hotelContactInfo.get("web"),
                                        hotelFacilitiesInfo.get("facilities"), hotelFacilitiesInfo.get("room facilities"),
                                        hotelFacilitiesInfo.get("checkin"), hotelFacilitiesInfo.get("checkout"),
                                        hotelFacilitiesInfo.get("pets"), distanceFromCenter, starsCount, hotelImages);
                                saveHotel.setCity(city);
                                this.hotelRepository.save(saveHotel);
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
    }

    private void populateDatabaseWithCities(Elements body) {
        citiesInMacedonia = body.stream()
                .map(city -> city.select("a span").text())
                .map(city -> new City(city, websiteUrl + "hotels/" + city
                        .toLowerCase()
                        .replace(" ", "-")))
                .collect(Collectors.toList());

        citiesInMacedonia.stream()
                .filter(city -> this.cityRepository.findByName(city.getName()) == null)
                .forEach(city -> this.cityRepository.save(new City(city.getName(), city.getWebsite())));
    }
}
