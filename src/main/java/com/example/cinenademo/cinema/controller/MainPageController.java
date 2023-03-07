package com.example.cinenademo.cinema.controller;

import com.example.cinenademo.cinema.model.*;
import com.example.cinenademo.cinema.model.banners.ActionNewsBanner;
import com.example.cinenademo.cinema.model.banners.BackgroundBanner;
import com.example.cinenademo.cinema.model.banners.MainTopBanner;
import com.example.cinenademo.cinema.model.pages.Page;
import com.example.cinenademo.cinema.model.pages.Pagecontacts;
import com.example.cinenademo.cinema.model.pages.Pagemain;
import com.example.cinenademo.cinema.repository.FilmRepository;
import com.example.cinenademo.cinema.repository.banners.ActionNewsBannerRepository;
import com.example.cinenademo.cinema.repository.banners.BackgroundBannerRepository;
import com.example.cinenademo.cinema.repository.banners.MainTopBannerRepository;
import com.example.cinenademo.cinema.service.ActionService;
import com.example.cinenademo.cinema.service.CinemaService;
import com.example.cinenademo.cinema.service.HallService;
import com.example.cinenademo.cinema.service.NewsService;
import com.example.cinenademo.cinema.service.banners.ActionNewsBannerService;
import com.example.cinenademo.cinema.service.banners.MainTopBannerService;
import com.example.cinenademo.cinema.service.pages.PageService;
import com.example.cinenademo.cinema.service.pages.PagecontactsService;
import com.example.cinenademo.cinema.service.pages.PagemainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainPageController {
    private final MainTopBannerService mainTopBannerService;
    private final ActionNewsBannerService actionNewsBannerService;
    private final MainTopBannerRepository mainTopBannerRepository;
    private final ActionNewsBannerRepository actionNewsBannerRepository;
    private final BackgroundBannerRepository backgroundBannerRepository;
    private final FilmRepository filmRepository;
    private final CinemaService cinemaService;
    private final ActionService actionService;
    private final PagemainService pagemainService;
    private final HallService hallService;
    private final NewsService newsService;
    private final PageService pageService;
    private final PagecontactsService pagecontactsService;

    @GetMapping("/user-main")
    public String findAll(Model model) {
        BackgroundBanner backgroundBanner = backgroundBannerRepository.getById(1L);
        List<Film> films = filmRepository.findAll();
        List<Film> filmsNow = new ArrayList<>();
        List<Film> filmsSoon = new ArrayList<>();
        List<MainTopBanner> mainTopBanners = mainTopBannerRepository.findAll();
        MainTopBanner mainTopBanner = mainTopBanners.get((int) (Math.random() * mainTopBanners.size()));
        List<ActionNewsBanner> actionNewsBanners = actionNewsBannerRepository.findAll();
        ActionNewsBanner actionNewsBanner = actionNewsBanners.get((int) (Math.random() * mainTopBanners.size()));
        for (Film film : films) {
            System.out.println(film.getName());
            System.out.println(film.getStatus());
            if (film.getStatus().equalsIgnoreCase("now")) {
                filmsNow.add(film);
            } else {
                filmsSoon.add(film);
            }
        }
        Pagemain pagemain = pagemainService.findById(1L);
        model.addAttribute("pagemain", pagemain);
        model.addAttribute("films", films);
        model.addAttribute("filmsNow", filmsNow);
        model.addAttribute("filmsSoon", filmsSoon);
        model.addAttribute("mainTopBanner", mainTopBanner);
        model.addAttribute("actionNewsBanner", actionNewsBanner);
        model.addAttribute("backgroundBanner", backgroundBanner);
        return "User/user-main";
    }

    @GetMapping("/user-poster")
    public String findAllPoster(Model model) {
        List<Film> films = filmRepository.findAll();
        List<Film> filmsNow = new ArrayList<>();
        for (Film film : films) {
            if (film.getStatus().equalsIgnoreCase("now")) {
                filmsNow.add(film);
            }
        }
        BackgroundBanner backgroundBanner = backgroundBannerRepository.getById(1L);
        Pagemain pagemain = pagemainService.findById(1L);
        model.addAttribute("pagemain", pagemain);
        model.addAttribute("backgroundBanner", backgroundBanner);
        model.addAttribute("filmsNow", filmsNow);
        return "User/user-poster";
    }

    @GetMapping("/user-poster-soon")
    public String findAllPosterSoon(Model model) {
        List<Film> films = filmRepository.findAll();
        List<Film> filmsSoon = new ArrayList<>();
        for (Film film : films) {
            if (film.getStatus().equalsIgnoreCase("soon")) {
                filmsSoon.add(film);
            }
        }
        BackgroundBanner backgroundBanner = backgroundBannerRepository.getById(1L);
        Pagemain pagemain = pagemainService.findById(1L);
        model.addAttribute("pagemain", pagemain);
        model.addAttribute("backgroundBanner", backgroundBanner);
        model.addAttribute("filmsSoon", filmsSoon);
        return "User/user-poster-soon";
    }

    @GetMapping("/user-cinema")
    public String findAllCinema(Model model) {
        List<Film> films = filmRepository.findAll();
        List<Cinema> cinemas = cinemaService.findAll();
        BackgroundBanner backgroundBanner = backgroundBannerRepository.getById(1L);
        Pagemain pagemain = pagemainService.findById(1L);
        model.addAttribute("pagemain", pagemain);
        model.addAttribute("backgroundBanner", backgroundBanner);
        model.addAttribute("cinemas", cinemas);
        model.addAttribute("films", films);
        return "User/user-cinema";
    }

    @GetMapping("/user-action")
    public String findAllAction(Model model) {
        List<Film> films = filmRepository.findAll();
        List<Action> actions = actionService.findAll();
        BackgroundBanner backgroundBanner = backgroundBannerRepository.getById(1L);
        Pagemain pagemain = pagemainService.findById(1L);
        model.addAttribute("pagemain", pagemain);
        model.addAttribute("backgroundBanner", backgroundBanner);
        model.addAttribute("actions", actions);
        model.addAttribute("films", films);
        return "User/user-actions";
    }

    @GetMapping("/user-film-card/{id}")
    public String filmCard(Model model, @PathVariable("id") Long id) {
        Film film = filmRepository.getById(id);
        BackgroundBanner backgroundBanner = backgroundBannerRepository.getById(1L);
        Pagemain pagemain = pagemainService.findById(1L);
        model.addAttribute("backgroundBanner", backgroundBanner);
        model.addAttribute("film", film);
        model.addAttribute("pagemain", pagemain);
        return "User/user-film-card";
    }

    @GetMapping("/user-cinema-card/{id}")
    public String cinemaCard(Model model, @PathVariable("id") Long id) {
        Cinema cinema = cinemaService.findById(id);
        List<Hall> halls = cinema.getHalls();
        BackgroundBanner backgroundBanner = backgroundBannerRepository.getById(1L);
        Pagemain pagemain = pagemainService.findById(1L);
        model.addAttribute("pagemain", pagemain);
        model.addAttribute("halls", halls);
        model.addAttribute("backgroundBanner", backgroundBanner);
        model.addAttribute("cinema", cinema);
        model.addAttribute("pagemain", pagemain);
        return "User/user-cinema-card";
    }

    @GetMapping("/user-hall-card/{id}")
    public String hallCard(Model model, @PathVariable("id") Long id) {
        Hall hall = hallService.findById(id);
        BackgroundBanner backgroundBanner = backgroundBannerRepository.getById(1L);
        Pagemain pagemain = pagemainService.findById(1L);
        model.addAttribute("pagemain", pagemain);
        model.addAttribute("hall", hall);
        model.addAttribute("backgroundBanner", backgroundBanner);
        model.addAttribute("pagemain", pagemain);
        return "User/user-hall-card";
    }

    @GetMapping("/user-timetable")
    public String findTimeTable(Model model) {
        BackgroundBanner backgroundBanner = backgroundBannerRepository.getById(1L);
        Pagemain pagemain = pagemainService.findById(1L);
        model.addAttribute("pagemain", pagemain);
        model.addAttribute("backgroundBanner", backgroundBanner);
        return "User/user-timetable";
    }

    @GetMapping("/user-actions-card/{id}")
    public String findAction(Model model, @PathVariable("id") Long id) {
        Action action = actionService.findById(id);
        BackgroundBanner backgroundBanner = backgroundBannerRepository.getById(1L);
        Pagemain pagemain = pagemainService.findById(1L);
        model.addAttribute("action", action);
        model.addAttribute("pagemain", pagemain);
        model.addAttribute("backgroundBanner", backgroundBanner);
        return "User/user-actions-card";
    }

    @GetMapping("/user-news")
    public String findNews(Model model) {
        BackgroundBanner backgroundBanner = backgroundBannerRepository.getById(1L);
        Pagemain pagemain = pagemainService.findById(1L);
        List<News> news = newsService.findAll();
        model.addAttribute("pagemain", pagemain);
        model.addAttribute("news", news);
        model.addAttribute("backgroundBanner", backgroundBanner);
        return "User/user-news";
    }

    @GetMapping("/user-cafe")
    public String findCafe(Model model) {
        BackgroundBanner backgroundBanner = backgroundBannerRepository.getById(1L);
        Pagemain pagemain = pagemainService.findById(1L);
        Page page = pageService.findById(2L);
        model.addAttribute("pagemain", pagemain);
        model.addAttribute("page", page);
        model.addAttribute("backgroundBanner", backgroundBanner);
        return "User/user-cafe";
    }

    @GetMapping("/user-advertising")
    public String findAdvertising(Model model) {
        BackgroundBanner backgroundBanner = backgroundBannerRepository.getById(1L);
        Pagemain pagemain = pagemainService.findById(1L);
        Page page = pageService.findById(4L);
        model.addAttribute("pagemain", pagemain);
        model.addAttribute("page", page);
        model.addAttribute("backgroundBanner", backgroundBanner);
        return "User/user-advertising";
    }

    @GetMapping("/user-contacts")
    public String findContacts(Model model) {
        BackgroundBanner backgroundBanner = backgroundBannerRepository.getById(1L);
        Pagemain pagemain = pagemainService.findById(1L);
        List<Pagecontacts> contactList = pagecontactsService.findAll();
        model.addAttribute("contactList", contactList);
        model.addAttribute("pagemain", pagemain);
        model.addAttribute("backgroundBanner", backgroundBanner);
        return "User/user-contacts";
    }
}
