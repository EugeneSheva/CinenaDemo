package com.example.cinenademo.cinema.controller;

import com.example.cinenademo.cinema.model.Action;
import com.example.cinenademo.cinema.model.Film;
import com.example.cinenademo.cinema.model.User;
import com.example.cinenademo.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    public String findAll(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("user",users);
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
//
//    @PostMapping("/admin-film-card-update-save")
//    public String updateFilm(@RequestParam ("idfilm") Long idfilm, @RequestParam ("name") String name, @RequestParam ("description") String description,@RequestParam("mainpicture") MultipartFile file,
//                             @RequestParam("picture1") MultipartFile[] files,
//                             @RequestParam("video") String video,  @RequestParam("type") String type, @RequestParam("url") String url,
//                             @RequestParam("title") String title, @RequestParam("keywords") String keywords, @RequestParam("descript") String descript) {
//        Film film = new Film();
//        film.setIdfilm(idfilm);
//        film.setName(name);
//        film.setDescription(description);
//        if (file != null) {
//            uploadDir = new File(uploadPath);
//            if (!uploadDir.exists()) {
//                uploadDir.mkdirs();
//            }
//            String uuid = UUID.randomUUID().toString();
//            String FileNameUuid = uuid + "-" + file.getOriginalFilename();
//            try {
//                file.transferTo(new File(uploadDir + "/" + FileNameUuid));
//                System.out.println(uploadDir + "/" + FileNameUuid);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            film.setMainpicture("/img/"+FileNameUuid);
//
//
//        }
//        if (files.length>0) {
//            System.out.println(files.length);
//            for (MultipartFile file1 : files) {
//                if (file1.getSize() > 0) {
//
//
//                    uploadDir = new File(uploadPath);
//                    System.out.println(uploadDir);
//                    if (!uploadDir.exists()) {
//                        uploadDir.mkdirs();
//                    }
//                    String uuid = UUID.randomUUID().toString();
//                    String FileNameUuid = uuid + "-" + file1.getOriginalFilename();
//                    try {
//                        file1.transferTo(new File(uploadDir + "/" + FileNameUuid));
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                    if (film.getPicture1()==null) {
//                        film.setPicture1("/img/"+FileNameUuid);
//                    }else if (film.getPicture2()==null) {
//                        film.setPicture2("/img/"+FileNameUuid);
//                    }else if (film.getPicture3()==null) {
//                        film.setPicture3("/img/"+FileNameUuid);
//                    }else if (film.getPicture4()==null) {
//                        film.setPicture4("/img/"+FileNameUuid);
//                    }else if (film.getPicture5()==null) {
//                        film.setPicture5("/img/"+FileNameUuid);
//                    }
//                }
//            }
//        }
//        film.setVideo(video);
//        film.setType(type);
//        film.setUrl(url);
//        film.setTitle(title);
//        film.setKeywords(keywords);
//        film.setDescript(descript);
//        filmService.saveFilm(film);
//
//        return "redirect:/admin-film";
//    }

}
