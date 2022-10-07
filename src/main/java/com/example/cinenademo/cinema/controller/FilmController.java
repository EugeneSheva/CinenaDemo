package com.example.cinenademo.cinema.controller;
import com.example.cinenademo.cinema.model.Cinema;
import com.example.cinenademo.cinema.model.Film;
import com.example.cinenademo.cinema.model.News;
import com.example.cinenademo.cinema.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class FilmController {

    private final FilmService filmService;
 @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @Value("${upload.path}")
    private String uploadPath;
    private File uploadDir;

    @GetMapping("/admin-film")
    public String findAll(Model model){
        List<Film> films = filmService.findAll();
        model.addAttribute("films",films);
        return "Admin/admin-film-list";
    }


    @GetMapping("/admin-film-card-form")
    public String createFilmForm(Model model) {
        model.addAttribute("film", new Film());
        return "Admin/admin-film-card";
    }
//    @PostMapping("/admin-film-card-add")
//    public String createFilm(@ModelAttribute("film") Film film) {
//        filmService.saveFilm(film);
//        return "redirect:/admin-film";
//    }





    @PostMapping("/admin-film-card-add")
    public String createFilm(@RequestParam ("name") String name, @RequestParam ("description") String description,@RequestParam("mainpicture") MultipartFile file,
                             @RequestParam("picture1") MultipartFile[] files,
                             @RequestParam("video") String video,  @RequestParam("type") String type, @RequestParam("url") String url,
                             @RequestParam("title") String title, @RequestParam("keywords") String keywords, @RequestParam("descript") String descript){
        Film film = new Film();
        film.setName(name);
        film.setDescription(description);
        if (file.getSize() > 0) {
                    uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }
                    String uuid = UUID.randomUUID().toString();
                    String FileNameUuid = uuid + "-" + file.getOriginalFilename();
                    try {
                        file.transferTo(new File(uploadDir + "/" + FileNameUuid));
                        System.out.println(uploadDir + "/" + FileNameUuid);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    film.setMainpicture("/img/"+FileNameUuid);


        }
        if (files.length>0) {
            System.out.println(files.length);
            for (MultipartFile file1 : files) {
                if (file1.getSize() > 0) {


                    uploadDir = new File(uploadPath);
                    System.out.println(uploadDir);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }
                    String uuid = UUID.randomUUID().toString();
                    String FileNameUuid = uuid + "-" + file1.getOriginalFilename();
                    try {
                        file1.transferTo(new File(uploadDir + "/" + FileNameUuid));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if (film.getPicture1()==null) {
                        film.setPicture1("/img/"+FileNameUuid);
                    }else if (film.getPicture2()==null) {
                        film.setPicture2("/img/"+FileNameUuid);
                    }else if (film.getPicture3()==null) {
                        film.setPicture3("/img/"+FileNameUuid);
                    }else if (film.getPicture4()==null) {
                        film.setPicture4("/img/"+FileNameUuid);
                    }else if (film.getPicture5()==null) {
                        film.setPicture5("/img/"+FileNameUuid);
                    }
                }
            }
        }
        film.setVideo(video);
        film.setType(type);
        film.setUrl(url);
        film.setTitle(title);
        film.setKeywords(keywords);
        film.setDescript(descript);
        filmService.saveFilm(film);

        return "redirect:/admin-film";
    }

    @GetMapping("film-delete/{id}")
    public String deleteFilm(@PathVariable("id") Long id) {
        filmService.deleteById(id);
        return "redirect:/admin-film";
    }

    @GetMapping("/admin-film-card-update/{id}")
    public String updateFilmForm(@PathVariable("id") Long id, Model model) {
        Film film = filmService.findById(id);
        model.addAttribute("film", film);
        return "Admin/admin-film-update-card";
    }

    @PostMapping("/admin-film-card-update-save")
    public String updateFilm(@RequestParam ("idfilm") Long idfilm, @RequestParam ("name") String name, @RequestParam ("description") String description,@RequestParam("mainpicture") MultipartFile file,
                             @RequestParam("picture1") MultipartFile[] files,
                             @RequestParam("video") String video,  @RequestParam("type") String type, @RequestParam("url") String url,
                             @RequestParam("title") String title, @RequestParam("keywords") String keywords, @RequestParam("descript") String descript) {
        Film film = new Film();
        film.setIdfilm(idfilm);
        film.setName(name);
        film.setDescription(description);
        if (file.getSize() > 0) {
                    uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }
                    String uuid = UUID.randomUUID().toString();
                    String FileNameUuid = uuid + "-" + file.getOriginalFilename();
                    try {
                        file.transferTo(new File(uploadDir + "/" + FileNameUuid));
                        System.out.println(uploadDir + "/" + FileNameUuid);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    film.setMainpicture("/img/"+FileNameUuid);


        }
        if (files.length>0) {
            System.out.println(files.length);
            for (MultipartFile file1 : files) {
                if (file1.getSize() > 0) {


                    uploadDir = new File(uploadPath);
                    System.out.println(uploadDir);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }
                    String uuid = UUID.randomUUID().toString();
                    String FileNameUuid = uuid + "-" + file1.getOriginalFilename();
                    try {
                        file1.transferTo(new File(uploadDir + "/" + FileNameUuid));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if (film.getPicture1()==null) {
                        film.setPicture1("/img/"+FileNameUuid);
                    }else if (film.getPicture2()==null) {
                        film.setPicture2("/img/"+FileNameUuid);
                    }else if (film.getPicture3()==null) {
                        film.setPicture3("/img/"+FileNameUuid);
                    }else if (film.getPicture4()==null) {
                        film.setPicture4("/img/"+FileNameUuid);
                    }else if (film.getPicture5()==null) {
                        film.setPicture5("/img/"+FileNameUuid);
                    }
                }
            }
        }
        film.setVideo(video);
        film.setType(type);
        film.setUrl(url);
        film.setTitle(title);
        film.setKeywords(keywords);
        film.setDescript(descript);
        filmService.saveFilm(film);

        return "redirect:/admin-film";
    }



}
