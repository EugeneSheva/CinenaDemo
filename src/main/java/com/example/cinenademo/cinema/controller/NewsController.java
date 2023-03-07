package com.example.cinenademo.cinema.controller;

import com.example.cinenademo.cinema.model.Action;
import com.example.cinenademo.cinema.model.Film;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public String findAll(Model model) {
        List<News> news = newsService.findAll();
        model.addAttribute("news", news);
        return "Admin/admin-news-list";
    }


    @GetMapping("/admin-news-card-form")
    public String createNewsForm(Model model) {
        model.addAttribute("news", new News());
        return "Admin/admin-news-card";
    }


    @PostMapping("/admin-news-card-add")
    public String createNews(@RequestParam("name") String name, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, @RequestParam("description") String description, @RequestParam("mainpicture") MultipartFile file,
                             @RequestParam("picture1") MultipartFile[] files,
                             @RequestParam("video") String video, @RequestParam("url") String url,
                             @RequestParam("title") String title, @RequestParam("keywords") String keywords, @RequestParam("descript") String descript) {
        News news = new News();
        news.setName(name);
        news.setDate(date);
        news.setDescription(description);


        //    загрузка главного фото

        uploadDir = new File(uploadPath + "/img/");
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        if (file.getSize() > 0) {
            String uuid = UUID.randomUUID().toString();
            String FileNameUuid = uuid + "-" + file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadDir + "/" + FileNameUuid));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            news.setMainpicture("img/" + FileNameUuid);
        }

//       загрузка остальных фото

        if (files.length > 0) {
            for (MultipartFile file1 : files) {
                if (file1.getSize() > 0) {
                    String uuid = UUID.randomUUID().toString();
                    String FileNameUuid = uuid + "-" + file1.getOriginalFilename();
                    try {
                        file1.transferTo(new File(uploadDir + "/" + FileNameUuid));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if (news.getPicture1() == null) {
                        news.setPicture1("img/" + FileNameUuid);
                    } else if (news.getPicture2() == null) {
                        news.setPicture2("img/" + FileNameUuid);
                    } else if (news.getPicture3() == null) {
                        news.setPicture3("img/" + FileNameUuid);
                    } else if (news.getPicture4() == null) {
                        news.setPicture4("img/" + FileNameUuid);
                    } else if (news.getPicture5() == null) {
                        news.setPicture5("img/" + FileNameUuid);
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
        News news = newsService.findById(id);
        try {
            Files.deleteIfExists(Path.of(uploadPath + news.getMainpicture()));
            Files.deleteIfExists(Path.of(uploadPath + news.getPicture1()));
            Files.deleteIfExists(Path.of(uploadPath + news.getPicture2()));
            Files.deleteIfExists(Path.of(uploadPath + news.getPicture3()));
            Files.deleteIfExists(Path.of(uploadPath + news.getPicture4()));
            Files.deleteIfExists(Path.of(uploadPath + news.getPicture5()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
    public String updateNews(@RequestParam("idnews") Long idnews, @RequestParam("name") String name, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, @RequestParam("description") String description, @RequestParam("mainpicture") MultipartFile file,
                             @RequestParam("picture1") MultipartFile[] files,
                             @RequestParam("video") String video, @RequestParam("url") String url,
                             @RequestParam("title") String title, @RequestParam("keywords") String keywords, @RequestParam("descript") String descript) throws IOException {
        News oldnews = newsService.findById(idnews);
        News news = new News();
        news.setIdnews(idnews);
        news.setName(name);
        news.setDate(date);
        news.setDescription(description);


        //             сохранение гловного фото
        uploadDir = new File(uploadPath + "/img/");
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        if (file.getSize() > 0) {
            Files.deleteIfExists(Paths.get(uploadPath + oldnews.getMainpicture()));
            String uuid = UUID.randomUUID().toString();
            String FileNameUuid = uuid + "-" + file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadDir + "/" + FileNameUuid));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            news.setMainpicture("img/" + FileNameUuid);
        } else {
            if (oldnews.getMainpicture() != null)
                news.setMainpicture(oldnews.getMainpicture());
        }


        //             сохранение остальных фото

        if (files.length > 0) {
            int i = 0;
            for (MultipartFile file1 : files) {
                i++;
                if (file1.getSize() > 0) {
                    String uuid = UUID.randomUUID().toString();
                    String FileNameUuid = uuid + "-" + file1.getOriginalFilename();
                    try {
                        file1.transferTo(new File(uploadDir + "/" + FileNameUuid));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if (i == 1) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldnews.getPicture1()));
                        news.setPicture1("img/" + FileNameUuid);
                    } else if (i == 2) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldnews.getPicture2()));
                        news.setPicture2("img/" + FileNameUuid);
                    } else if (i == 3) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldnews.getPicture3()));
                        news.setPicture3("img/" + FileNameUuid);
                    } else if (i == 4) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldnews.getPicture4()));
                        news.setPicture4("img/" + FileNameUuid);
                    } else if (i == 5) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldnews.getPicture5()));
                        news.setPicture5("img/" + FileNameUuid);
                    }
                } else {
                    if (i == 1) {
                        if (oldnews.getPicture1() != null)
                            news.setPicture1(oldnews.getPicture1());
                    } else if (i == 2) {
                        if (oldnews.getPicture2() != null)
                            news.setPicture2(oldnews.getPicture2());
                    } else if (i == 3) {
                        if (oldnews.getPicture3() != null)
                            news.setPicture3(oldnews.getPicture3());
                    } else if (i == 4) {
                        if (oldnews.getPicture4() != null)
                            news.setPicture4(oldnews.getPicture4());
                    } else if (i == 5) {
                        if (oldnews.getPicture5() != null)
                            news.setPicture5(oldnews.getPicture5());
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
