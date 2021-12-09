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
    @Scheduled(fixedDelay = 20000000)
    public void scrapeNewHotels() throws IOException {
        this.scraperService.populateDatabase();
    }
}
