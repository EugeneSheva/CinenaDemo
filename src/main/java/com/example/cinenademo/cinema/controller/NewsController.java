package com.example.cinenademo.cinema.controller;

import com.example.cinenademo.cinema.model.News;
import com.example.cinenademo.cinema.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class NewsController {
    private final NewsService newsService;
    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @Value("${upload.path}")
    private String uploadPath;
    private File uploadDir;


    @GetMapping("/admin-news")
    public String findAll(Model model){
        List<News> news = newsService.findAll();
        model.addAttribute("news",news);
        return "Admin/admin-news-list";
    }


    @GetMapping("/admin-news-card-form")
    public String createNewsForm(Model model) {
        model.addAttribute("news", new News());
        return "Admin/admin-news-card";
    }


    @PostMapping("/admin-news-card-add")
    public String createNews(@RequestParam("name") String name, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, @RequestParam ("description") String description, @RequestParam("mainpicture") MultipartFile file,
                             @RequestParam("picture1") MultipartFile[] files,
                             @RequestParam("video") String video, @RequestParam("url") String url,
                             @RequestParam("title") String title, @RequestParam("keywords") String keywords, @RequestParam("descript") String descript){
        News news = new News();
        news.setName(name);
        news.setDate(date);
        news.setDescription(description);
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
            news.setMainpicture("/img/"+FileNameUuid);


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
                    if (news.getPicture1()==null) {
                        news.setPicture1("/img/"+FileNameUuid);
                    }else if (news.getPicture2()==null) {
                        news.setPicture2("/img/"+FileNameUuid);
                    }else if (news.getPicture3()==null) {
                        news.setPicture3("/img/"+FileNameUuid);
                    }else if (news.getPicture4()==null) {
                        news.setPicture4("/img/"+FileNameUuid);
                    }else if (news.getPicture5()==null) {
                        news.setPicture5("/img/"+FileNameUuid);
                    }
                }
            }
        }
        news.setVideo(video);
        news.setUrl(url);
        news.setTitle(title);
        news.setKeywords(keywords);
        news.setDescript(descript);
        newsService.save(news);

        return "redirect:/admin-news";
    }


    @GetMapping("news-delete/{id}")
    public String deleteNews(@PathVariable("id") Long id) {
        newsService.deleteById(id);
        return "redirect:/admin-news";
    }


    @GetMapping("/admin-news-card-update/{id}")
    public String updateNewsForm(@PathVariable("id") Long id, Model model) {
        News news = newsService.findById(id);
        model.addAttribute("news", news);
        return "Admin/admin-news-update-card";
    }


    @PostMapping("/admin-news-card-update-save")
    public String updateNews(@RequestParam ("idnews") Long idnews,@RequestParam("name") String name, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, @RequestParam ("description") String description, @RequestParam("mainpicture") MultipartFile file,
                             @RequestParam("picture1") MultipartFile[] files,
                             @RequestParam("video") String video, @RequestParam("url") String url,
                             @RequestParam("title") String title, @RequestParam("keywords") String keywords, @RequestParam("descript") String descript){
        News news = new News();
        news.setIdnews(idnews);
        news.setName(name);
        news.setDate(date);
        news.setDescription(description);
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
            news.setMainpicture("/img/"+FileNameUuid);


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
                    if (news.getPicture1()==null) {
                        news.setPicture1("/img/"+FileNameUuid);
                    }else if (news.getPicture2()==null) {
                        news.setPicture2("/img/"+FileNameUuid);
                    }else if (news.getPicture3()==null) {
                        news.setPicture3("/img/"+FileNameUuid);
                    }else if (news.getPicture4()==null) {
                        news.setPicture4("/img/"+FileNameUuid);
                    }else if (news.getPicture5()==null) {
                        news.setPicture5("/img/"+FileNameUuid);
                    }
                }
            }
        }
        news.setVideo(video);
        news.setUrl(url);
        news.setTitle(title);
        news.setKeywords(keywords);
        news.setDescript(descript);
        newsService.save(news);

        return "redirect:/admin-news";
    }

}
