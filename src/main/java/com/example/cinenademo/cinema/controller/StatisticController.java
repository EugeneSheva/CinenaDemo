package com.example.cinenademo.cinema.controller;

import com.example.cinenademo.cinema.model.Action;
import com.example.cinenademo.cinema.model.Film;
import com.example.cinenademo.cinema.model.User;
import com.example.cinenademo.cinema.service.FilmService;
import com.example.cinenademo.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.util.List;

@Controller
public class StatisticController {
        private final UserService userService;
        private final FilmService filmService;
        public StatisticController(FilmService filmService, UserService userService) {
                this.filmService = filmService;
                this.userService = userService;        }



        @Value("${upload.path}")
        private String uploadPath;
        private File uploadDir;



        @GetMapping("/admin-statistics")
        public String findAll(Model model) {
//                return "Admin/chartjs";
                List<User> users = userService.findAll();
                List<Film> films = filmService.findAll();
                //кол-во пользователей
                Integer userssQual = users.size();
                //кол-во фильмов
                Integer filmsQual = films.size();
                // статистика по городам
                Integer Kiev=0;
                Integer Odessa=0;
                Integer Kharkiv=0;
                for (User user : users) {
                       if (user.getCity().equalsIgnoreCase("Киев")) {
                         Kiev++;
                       } else if (user.getCity().equalsIgnoreCase("Одесса")) {
                          Odessa++;
                       } else if (user.getCity().equalsIgnoreCase("Харьков")) {
                               Kharkiv++;
                       }

                }

                model.addAttribute("usersQual", userssQual);
                model.addAttribute("filmQual", filmsQual);
                model.addAttribute("Kiev", Kiev);
                model.addAttribute("Odessa", Odessa);
                model.addAttribute("Kharkiv", Kharkiv);
            return "Admin/admin-statistics-list";
        }







}
