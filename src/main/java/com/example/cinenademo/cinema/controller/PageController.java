package com.example.cinenademo.cinema.controller;

import com.example.cinenademo.cinema.model.Film;
import com.example.cinenademo.cinema.model.User;
import com.example.cinenademo.cinema.model.pages.Page;
import com.example.cinenademo.cinema.model.pages.Pagecontacts;
import com.example.cinenademo.cinema.model.pages.Pagemain;
import com.example.cinenademo.cinema.model.pages.Pageuser;
import com.example.cinenademo.cinema.service.pages.PageService;
import com.example.cinenademo.cinema.service.pages.PagecontactsService;
import com.example.cinenademo.cinema.service.pages.PagemainService;
import com.example.cinenademo.cinema.service.pages.PageuserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.SchemaOutputResolver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final PageService pageService;
    private final PagemainService pagemainService;
    private final PageuserService pageuserService;
    private final PagecontactsService pagecontactsService;

    String sts;

    @Value("${upload.path}")
    private String uploadPath;
    private File uploadDir;


    @GetMapping("/admin-pages")

    public String findAll(Model model) {
        Pagemain pagemain;
        Pagemain pagecont;
        List<Page> pages = pageService.findAll();
        if (pagemainService.findAll().size() == 0) {
            pagemain = pagemainService.save(new Pagemain(1L, "Главная страница"));
            pagecont = pagemainService.save(new Pagemain(2L, "Контакты"));
        } else {
            pagemain = pagemainService.findById(1L);
            pagecont = pagemainService.findById(2L);
        }
        List<Pageuser> pageusers = pageuserService.findAll();
        if (pages.size() == 0) {
//            pages = new ArrayList<>();
            pages.add(pageService.save(new Page("О кинотеатре")));
            pages.add(pageService.save(new Page("Кафе-Бар")));
            pages.add(pageService.save(new Page("VIP-зал")));
            pages.add(pageService.save(new Page("Реклама")));
            pages.add(pageService.save(new Page("Детская комната")));
        }

//        if (pageusers.size() == 0) {
//            pageusers.add(pageuserService.save(new Pageuser("Новая страница")));
//        }
        model.addAttribute("page", pages);
        model.addAttribute("pageuser", pageusers);
        model.addAttribute("pagemain", pagemain);
        model.addAttribute("pagecont", pagecont);
        return "Admin/admin-page-list";
    }

    //       главная страница
    @GetMapping("/admin-page-card-update-main")
    public String updatePageMainForm(Model model) {
        Pagemain page = pagemainService.findById(1L);
        model.addAttribute("page", page);
        return "Admin/admin-page-update-main";
    }

    @PostMapping("/admin-page-card-main-save")
    public String updateNews(@RequestParam("idpage") Long idpage, @RequestParam("name") String name, @RequestParam("phonenumber1") Integer phonenumber1, @RequestParam("phonenumber2") Integer phonenumber2, @RequestParam(name = "status", defaultValue = "false") Boolean status,
                             @RequestParam("seotext") String seotext, @RequestParam("url") String url, @RequestParam("title") String title,
                             @RequestParam("keywords") String keywords, @RequestParam("descript") String descript, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        Pagemain page = new Pagemain();
        page.setIdpage(1L);
        page.setName(name);
        page.setPhonenumber1(phonenumber1);
        page.setPhonenumber2(phonenumber2);
        page.setSeotext(seotext);
        page.setUrl(url);
        page.setTitle(title);
        page.setKeywords(keywords);
        page.setDescript(descript);
        if (status == true) {
            sts = "on";
        } else {
            sts = "off";
        }
        page.setStatus(sts);
        page.setDate(date);
        pagemainService.save(page);

        return "redirect:/admin-pages";
    }


    //Стандартные страницы
    @GetMapping("/admin-page-card-update/{id}")
    public String updatePageForm(@PathVariable("id") Long id, Model model) {
        Page page = pageService.findById(id);
        model.addAttribute("page", page);
        return "Admin/admin-page-update-card";
    }

    @PostMapping("/admin-page-card-update-save")
    public String updatePage(@RequestParam("idpage") Long idpage, @RequestParam("name") String name, @RequestParam(name = "status", defaultValue = "false") Boolean status, @RequestParam("description") String description, @RequestParam("mainpicture") MultipartFile file,
                             @RequestParam("picture1") MultipartFile[] files, @RequestParam("url") String url, @RequestParam("title") String title,
                             @RequestParam("keywords") String keywords, @RequestParam("descript") String descript, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) throws IOException {
        Page oldpage = pageService.findById(idpage);
        Page page = new Page();
        page.setIdpage(idpage);
        System.out.println(idpage);
        page.setName(name);
        page.setDescription(description);

        //             сохранение гловного фото
        uploadDir = new File(uploadPath + "/img/");
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        if (file.getSize() > 0) {
            Files.deleteIfExists(Paths.get(uploadPath + oldpage.getMainpicture()));
            String uuid = UUID.randomUUID().toString();
            String FileNameUuid = uuid + "-" + file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadDir + "/" + FileNameUuid));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            page.setMainpicture("img/" + FileNameUuid);
        } else {
            if (oldpage.getMainpicture() != null)
                page.setMainpicture(oldpage.getMainpicture());
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
                        Files.deleteIfExists(Paths.get(uploadPath + oldpage.getPicture1()));
                        page.setPicture1("img/" + FileNameUuid);
                    } else if (i == 2) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldpage.getPicture2()));
                        page.setPicture2("img/" + FileNameUuid);
                    } else if (i == 3) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldpage.getPicture3()));
                        page.setPicture3("img/" + FileNameUuid);
                    } else if (i == 4) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldpage.getPicture4()));
                        page.setPicture4("img/" + FileNameUuid);
                    } else if (i == 5) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldpage.getPicture5()));
                        page.setPicture5("img/" + FileNameUuid);
                    }
                } else {
                    if (i == 1) {
                        if (oldpage.getPicture1() != null)
                            page.setPicture1(oldpage.getPicture1());
                    } else if (i == 2) {
                        if (oldpage.getPicture2() != null)
                            page.setPicture2(oldpage.getPicture2());
                    } else if (i == 3) {
                        if (oldpage.getPicture3() != null)
                            page.setPicture3(oldpage.getPicture3());
                    } else if (i == 4) {
                        if (oldpage.getPicture4() != null)
                            page.setPicture4(oldpage.getPicture4());
                    } else if (i == 5) {
                        if (oldpage.getPicture5() != null)
                            page.setPicture5(oldpage.getPicture5());
                    }
                }

            }

        }
        page.setUrl(url);
        page.setTitle(title);
        page.setKeywords(keywords);
        page.setDescript(descript);
        if (status == true) {
            sts = "on";
        } else {
            sts = "off";
        }
        page.setStatus(sts);
        page.setDate(date);
        System.out.println(page);
        pageService.save(page);

        return "redirect:/admin-pages";
    }


//    пользовательские страницы

    @GetMapping("/admin-page-card-form")
    public String createPageForm(Model model) {
        model.addAttribute("page", new Pageuser());
        return "Admin/admin-page-user";
    }

    @PostMapping("/admin-page-user-add")
    public String createNews(@RequestParam("name") String name, @RequestParam(name = "status", defaultValue = "false") Boolean status, @RequestParam(name = "notremovable", defaultValue = "false") Boolean notremovable, @RequestParam("description") String description, @RequestParam("mainpicture") MultipartFile file,
                             @RequestParam("picture1") MultipartFile[] files, @RequestParam("url") String url, @RequestParam("title") String title,
                             @RequestParam("keywords") String keywords, @RequestParam("descript") String descript, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        Pageuser page = new Pageuser();
        page.setName(name);
        page.setDescription(description);

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
            page.setMainpicture("img/" + FileNameUuid);

            //       загрузка остальных фото

        }
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
                    if (page.getPicture1() == null) {
                        page.setPicture1("img/" + FileNameUuid);
                    } else if (page.getPicture2() == null) {
                        page.setPicture2("img/" + FileNameUuid);
                    } else if (page.getPicture3() == null) {
                        page.setPicture3("img/" + FileNameUuid);
                    } else if (page.getPicture4() == null) {
                        page.setPicture4("img/" + FileNameUuid);
                    } else if (page.getPicture5() == null) {
                        page.setPicture5("img/" + FileNameUuid);
                    }
                }
            }
        }
        page.setUrl(url);
        page.setTitle(title);
        page.setKeywords(keywords);
        page.setDescript(descript);
        if (status == true) {
            sts = "on";
        } else {
            sts = "off";
        }
        page.setStatus(sts);
        page.setDate(date);
        pageuserService.save(page);

        return "redirect:/admin-pages";
    }


    @GetMapping("/admin-page-card-update-user/{id}")
    public String updatePageUserForm(@PathVariable("id") Long id, Model model) {
        Pageuser page = pageuserService.findById(id);
        model.addAttribute("page", page);
        return "Admin/admin-page-update-user";
    }

    @PostMapping("/admin-page-card-user-update")
    public String updatePageuser(@RequestParam("idpage") Long idpage, @RequestParam("name") String name, @RequestParam(name = "status", defaultValue = "false") Boolean status, @RequestParam("description") String description, @RequestParam("mainpicture") MultipartFile file,
                                 @RequestParam("picture1") MultipartFile[] files, @RequestParam("url") String url, @RequestParam("title") String title,
                                 @RequestParam("keywords") String keywords, @RequestParam("descript") String descript, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) throws IOException {
        Pageuser oldpage = pageuserService.findById(idpage);
        Pageuser page = new Pageuser();
        page.setIdpage(idpage);
        System.out.println(idpage);
        page.setName(name);
        page.setDescription(description);

        //             сохранение гловного фото
        uploadDir = new File(uploadPath + "/img/");
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        if (file.getSize() > 0) {
            Files.deleteIfExists(Paths.get(uploadPath + oldpage.getMainpicture()));
            String uuid = UUID.randomUUID().toString();
            String FileNameUuid = uuid + "-" + file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadDir + "/" + FileNameUuid));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            page.setMainpicture("img/" + FileNameUuid);
        } else {
            if (oldpage.getMainpicture() != null)
                page.setMainpicture(oldpage.getMainpicture());
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
                        Files.deleteIfExists(Paths.get(uploadPath + oldpage.getPicture1()));
                        page.setPicture1("img/" + FileNameUuid);
                    } else if (i == 2) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldpage.getPicture2()));
                        page.setPicture2("img/" + FileNameUuid);
                    } else if (i == 3) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldpage.getPicture3()));
                        page.setPicture3("img/" + FileNameUuid);
                    } else if (i == 4) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldpage.getPicture4()));
                        page.setPicture4("img/" + FileNameUuid);
                    } else if (i == 5) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldpage.getPicture5()));
                        page.setPicture5("img/" + FileNameUuid);
                    }
                } else {
                    if (i == 1) {
                        if (oldpage.getPicture1() != null)
                            page.setPicture1(oldpage.getPicture1());
                    } else if (i == 2) {
                        if (oldpage.getPicture2() != null)
                            page.setPicture2(oldpage.getPicture2());
                    } else if (i == 3) {
                        if (oldpage.getPicture3() != null)
                            page.setPicture3(oldpage.getPicture3());
                    } else if (i == 4) {
                        if (oldpage.getPicture4() != null)
                            page.setPicture4(oldpage.getPicture4());
                    } else if (i == 5) {
                        if (oldpage.getPicture5() != null)
                            page.setPicture5(oldpage.getPicture5());
                    }
                }

            }

        }
        page.setUrl(url);
        page.setTitle(title);
        page.setKeywords(keywords);
        page.setDescript(descript);
        if (status == true) {
            sts = "on";
        } else {
            sts = "off";
        }
        page.setStatus(sts);
        page.setDate(date);
        System.out.println(page);
        pageuserService.save(page);

        return "redirect:/admin-pages";
    }


    @GetMapping("page-delete/{id}")
    public String deletePage(@PathVariable("id") Long id) {
        Pageuser page = pageuserService.findById(id);
        try {
            Files.deleteIfExists(Path.of(uploadPath + page.getMainpicture()));
            Files.deleteIfExists(Path.of(uploadPath + page.getPicture1()));
            Files.deleteIfExists(Path.of(uploadPath + page.getPicture2()));
            Files.deleteIfExists(Path.of(uploadPath + page.getPicture3()));
            Files.deleteIfExists(Path.of(uploadPath + page.getPicture4()));
            Files.deleteIfExists(Path.of(uploadPath + page.getPicture5()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        pageuserService.deleteById(id);
        return "redirect:/admin-pages";
    }
    //страныца контактов

    @GetMapping("/admin-page-update-contact")
    public String updatePageContForm(Model model) {
        Pagemain page = pagemainService.findById(2L);
        List<Pagecontacts> pagecont = pagecontactsService.findAll();
        System.out.println(pagecont);
        model.addAttribute("pagecontList", pagecont);
        model.addAttribute("page", page);
        return "Admin/admin-page-update-contact";
    }


    @PostMapping("/admin-pageconturl-card-add")
    public String updateFilm(@ModelAttribute("Page") Pagemain page) {
        page.setIdpage(2L);
        pagemainService.save(page);
        return "redirect:/admin-page-update-contact";
    }

    @PostMapping("/admin-pagecont-card-add")
    public String updatePagecont(@RequestParam("idpage") Long idpage, @RequestParam("name") String name, @RequestParam(name = "status", defaultValue = "false") Boolean status, @RequestParam("description") String description, @RequestParam("mainpicture") MultipartFile file,
                                 @RequestParam("coordinates") String coordinates) throws IOException {
        Pagecontacts oldpage = pagecontactsService.findById(idpage);
        Pagecontacts page = new Pagecontacts();
        page.setIdpage(idpage);
        page.setName(name);
        page.setDescription(description);

        //             сохранение гловного фото
        uploadDir = new File(uploadPath + "/img/");
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        if (file.getSize() > 0) {
            Files.deleteIfExists(Paths.get(uploadPath + oldpage.getMainpicture()));
            String uuid = UUID.randomUUID().toString();
            String FileNameUuid = uuid + "-" + file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadDir + "/" + FileNameUuid));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            page.setMainpicture("img/" + FileNameUuid);
        } else {
            if (oldpage.getMainpicture() != null)
                page.setMainpicture(oldpage.getMainpicture());
        }
        page.setCoordinates(coordinates);
        if (status == true) {
            sts = "on";
        } else {
            sts = "off";
        }
        page.setStatus(sts);

        pagecontactsService.save(page);

        return "redirect:/admin-page-update-contact";
    }

    @GetMapping("admin-pagecont-delete/{id}")
    public String deletePageCont(@PathVariable("id") Long id, Model model) {
        Pagecontacts paged = pagecontactsService.findById(id);
        try {
            Files.deleteIfExists(Path.of(uploadPath + paged.getMainpicture()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        pagecontactsService.deleteById(id);
        Pagemain page = pagemainService.findById(2L);
        List<Pagecontacts> pagecont = pagecontactsService.findAll();
        model.addAttribute("pagecontList", pagecont);
        model.addAttribute("page", page);
        return "redirect:/admin-page-update-contact";
    }

    //    /admin-pagecont-add-new
    @GetMapping("/admin-pagecont-add-new")
    public String newPageContForm(Model model) {
        pagecontactsService.save(new Pagecontacts());
        Pagemain page = pagemainService.findById(2L);
        List<Pagecontacts> pagecont = pagecontactsService.findAll();
        model.addAttribute("pagecontList", pagecont);
        model.addAttribute("page", page);
        return "Admin/admin-page-update-contact";
    }
}
