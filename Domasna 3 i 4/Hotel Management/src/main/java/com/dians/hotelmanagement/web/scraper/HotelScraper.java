package com.dians.hotelmanagement.web.scraper;

import com.dians.hotelmanagement.service.ScraperService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;


@Configuration
@EnableScheduling
public class HotelScraper {
    private final ScraperService scraperService;

    public HotelScraper(ScraperService scraperService) {

        this.scraperService = scraperService;
    }

    //Runs every 30 days and checks for new hotels
    @Scheduled(fixedDelay = 2592000)
    public void scrapeNewHotels() throws IOException {
        this.scraperService.populateDatabase();
    }
}
