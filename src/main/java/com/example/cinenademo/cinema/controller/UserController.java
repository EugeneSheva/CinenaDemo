package com.example.cinenademo.cinema.controller;

import com.example.cinenademo.cinema.model.Action;
import com.example.cinenademo.cinema.model.Film;
import com.example.cinenademo.cinema.model.User;
import com.example.cinenademo.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Value("${upload.path}")
    private String uploadPath;
    private File uploadDir;

    @GetMapping("/admin-users")
    public String findAll(Model model, @PageableDefault(sort = {"idusers"}, direction = Sort.Direction.ASC, size = 10) Pageable pageable) {
        Page<User>users = userService.findAll(pageable);
        model.addAttribute("user", users);
        return "Admin/admin-users-list";
    }


    @GetMapping("/admin-users-card-form")
    public String createUsersForm(Model model) {
        model.addAttribute("users", new User());
        return "Admin/admin-users-card";
    }

    @PostMapping("/admin-users-card-add")
    public String createFilm(@ModelAttribute("users") User user) {
        userService.saveUser(user);
        return "redirect:/admin-users";
    }


    //
    @GetMapping("users-delete/{id}")
    public String deleteFilm(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin-users";
    }

    //
    @GetMapping("/admin-users-card-update/{id}")
    public String updateFilmForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("users", user);
        return "Admin/admin-users-update-card";
    }

    @PostMapping("/admin-users-card-update-save")
    public String updateFilm(@ModelAttribute("users") User user) {
        userService.saveUser(user);
        return "redirect:/admin-users";
    }

}
