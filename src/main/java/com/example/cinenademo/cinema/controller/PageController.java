package com.example.cinenademo.cinema.controller;

import com.example.cinenademo.cinema.model.Action;
import com.example.cinenademo.cinema.model.Page;
import com.example.cinenademo.cinema.service.PageService;
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
public class PageController {

    private final PageService pageService;

    @Autowired
    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    String sts;

    @Value("${upload.path}")
    private String uploadPath;
    private File uploadDir;

    @GetMapping("/admin-pages")
    public String findAll(Model model) {
        List<Page> page = pageService.findAll();
        model.addAttribute("page", page);
        return "Admin/admin-page-list";
    }


    @GetMapping("/admin-page-card-form")
    public String createPageForm(Model model) {
        model.addAttribute("page", new Page());
        return "Admin/admin-page-card";
    }


    @PostMapping("/admin-page-card-add")
    public String createNews(@RequestParam("name") String name, @RequestParam(name = "status", defaultValue = "false") Boolean status, @RequestParam(name = "notremovable", defaultValue = "false") Boolean notremovable, @RequestParam("description") String description, @RequestParam("mainpicture") MultipartFile file,
                             @RequestParam("picture1") MultipartFile[] files, @RequestParam("url") String url, @RequestParam("title") String title,
                             @RequestParam("keywords") String keywords, @RequestParam("descript") String descript, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        Page page = new Page();
        page.setName(name);
        page.setDescription(description);
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
            page.setMainpicture("/img/" + FileNameUuid);


        }
        if (files.length > 0) {
            for (MultipartFile file1 : files) {
                if (file1.getSize() > 0) {


                    uploadDir = new File(uploadPath);

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
                    if (page.getPicture1() == null) {
                        page.setPicture1("/img/" + FileNameUuid);
                    } else if (page.getPicture2() == null) {
                        page.setPicture2("/img/" + FileNameUuid);
                    } else if (page.getPicture3() == null) {
                        page.setPicture3("/img/" + FileNameUuid);
                    } else if (page.getPicture4() == null) {
                        page.setPicture4("/img/" + FileNameUuid);
                    } else if (page.getPicture5() == null) {
                        page.setPicture5("/img/" + FileNameUuid);
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
        page.setNotremovable(notremovable);
        page.setDate(date);

        pageService.save(page);

        return "redirect:/admin-pages";
    }


    @GetMapping("page-delete/{id}")
    public String deletePage(@PathVariable("id") Long id) {
        pageService.deleteById(id);
        return "redirect:/admin-pages";
    }


    @GetMapping("/admin-page-card-update/{id}")
    public String updatePageForm(@PathVariable("id") Long id, Model model) {
        Page page = pageService.findById(id);
        model.addAttribute("page", page);
        return "Admin/admin-page-update-card";
    }

    @GetMapping("/admin-page-card-update-main/{id}")
    public String updatePageMainForm(@PathVariable("id") Long id, Model model) {
        Page page = pageService.findById(id);
        model.addAttribute("page", page);
        return "Admin/admin-page-update-main";
    }

    @GetMapping("/admin-page-card-update-contact/{id}")
    public String updatePageContactForm(@PathVariable("id") Long id, Model model) {
        Page page = pageService.findById(id);
        model.addAttribute("page", page);
        return "Admin/admin-page-update-contact";
    }

    @PostMapping("/admin-page-card-update-save")
    public String updatePage(@RequestParam("idpage") Long idpage, @RequestParam("name") String name, @RequestParam(name = "status", defaultValue = "false") Boolean status, @RequestParam(name = "notremovable", defaultValue = "false") Boolean notremovable, @RequestParam("description") String description, @RequestParam("mainpicture") MultipartFile file,
                             @RequestParam("picture1") MultipartFile[] files, @RequestParam("url") String url, @RequestParam("title") String title,
                             @RequestParam("keywords") String keywords, @RequestParam("descript") String descript, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        Page page = new Page();
        page.setIdpage(idpage);
        System.out.println(idpage);
        page.setName(name);
        page.setDescription(description);
        if (file.getSize() > 0) {
            uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            String uuid = UUID.randomUUID().toString();
            String FileNameUuid = uuid + "-" + file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadDir + "/" + FileNameUuid));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            page.setMainpicture("/img/" + FileNameUuid);
        }
        if (files.length > 0) {
            for (MultipartFile file1 : files) {
                if (file1.getSize() > 0) {


                    uploadDir = new File(uploadPath);

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
                    if (page.getPicture1() == null) {
                        page.setPicture1("/img/" + FileNameUuid);
                    } else if (page.getPicture2() == null) {
                        page.setPicture2("/img/" + FileNameUuid);
                    } else if (page.getPicture3() == null) {
                        page.setPicture3("/img/" + FileNameUuid);
                    } else if (page.getPicture4() == null) {
                        page.setPicture4("/img/" + FileNameUuid);
                    } else if (page.getPicture5() == null) {
                        page.setPicture5("/img/" + FileNameUuid);
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
        page.setNotremovable(notremovable);
        page.setDate(date);
        System.out.println(page);
        pageService.save(page);

        return "redirect:/admin-pages";
    }

    @PostMapping("/admin-page-card-main-save")
    public String updateNews(@RequestParam("idpage") Long idpage,@RequestParam("name") String name, @RequestParam("phonenumber1") Integer phonenumber1, @RequestParam("phonenumber2") Integer phonenumber2, @RequestParam(name = "status", defaultValue = "false") Boolean status,
                             @RequestParam(name = "notremovable", defaultValue = "false") Boolean notremovable, @RequestParam("description") String description, @RequestParam("url") String url, @RequestParam("title") String title,
                             @RequestParam("keywords") String keywords, @RequestParam("descript") String descript, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        Page page = new Page();
        page.setIdpage(idpage);
        page.setName(name);
        page.setPhonenumber1(phonenumber1);
        page.setPhonenumber2(phonenumber2);
        page.setDescription(description);
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
        page.setNotremovable(notremovable);
        page.setDate(date);
        page.setIsmain(true);
        pageService.save(page);

        return "redirect:/admin-pages";
    }
}
