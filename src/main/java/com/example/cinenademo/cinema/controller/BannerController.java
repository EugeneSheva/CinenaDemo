package com.example.cinenademo.cinema.controller;

import com.example.cinenademo.cinema.model.banners.ActionNewsBanner;
import com.example.cinenademo.cinema.model.banners.BackgroundBanner;
import com.example.cinenademo.cinema.model.banners.MainTopBanner;
import com.example.cinenademo.cinema.model.banners.MainTopBannerRotationSpeed;
import com.example.cinenademo.cinema.repository.banners.ActionNewsBannerRepository;
import com.example.cinenademo.cinema.repository.banners.BackgroundBannerRepository;
import com.example.cinenademo.cinema.repository.banners.MainTopBannerRepository;
import com.example.cinenademo.cinema.service.banners.ActionNewsBannerService;
import com.example.cinenademo.cinema.service.banners.ActionNewsBannersDto;
import com.example.cinenademo.cinema.service.banners.MainTopBannerService;
import com.example.cinenademo.cinema.service.banners.MainTopBannersDto;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class BannerController {
    private final MainTopBannerService mainTopBannerService;
    private final ActionNewsBannerService actionNewsBannerService;
    private final MainTopBannerRepository mainTopBannerRepository;
    private final ActionNewsBannerRepository actionNewsBannerRepository;
    private final BackgroundBannerRepository backgroundBannerRepository;

    @Value("${upload.path}")
    private String uploadPath;
    private File uploadDir;

    @GetMapping("/admin-banners")
    public String findAll(Model model) {
        if (mainTopBannerRepository.findAll() == null) mainTopBannerRepository.save(new MainTopBanner());
        if (actionNewsBannerRepository.findAll() == null) actionNewsBannerRepository.save(new ActionNewsBanner());
        BackgroundBanner banner = backgroundBannerRepository.findById(1L).orElse(new BackgroundBanner());
        model.addAttribute("banner", banner);
        model.addAttribute("mbanners", mainTopBannerService.findAll());
        model.addAttribute("mrotationSpeed", mainTopBannerService.findRotationSpeed());
        model.addAttribute("abanners", actionNewsBannerService.findAll());
        model.addAttribute("arotationSpeed", actionNewsBannerService.findRotationSpeed());
        return "Admin/admin-bannermain-list";
    }

    @PostMapping("/save-banners")
    public String saveBanners(MainTopBannersDto bannersDto) {
        mainTopBannerService.saveAllAndRotationSpeed(bannersDto);
        return "redirect:/admin-banners";
    }

    @PostMapping("/save-banners1")
    public String saveBanner1(@RequestParam("backbanner") MultipartFile file) {
        try {
            if (!(Files.exists(Path.of(uploadPath + "img/background_banner/"))))
                Files.createDirectories(Path.of(uploadPath + "img/background_banner/"));
            BackgroundBanner banner = backgroundBannerRepository.findById(1L).orElse(new BackgroundBanner());
            if ((file.getSize() > 0) && (banner.getImagePath() != null))
                Files.delete(Path.of(uploadPath + banner.getImagePath()));
            UUID uuid = UUID.randomUUID();
            Files.copy(file.getInputStream(),
                    Path.of(uploadPath + "img/background_banner/" + uuid + file.getOriginalFilename()));
            banner.setImagePath("/img/background_banner/" + uuid + file.getOriginalFilename());
            banner.setId(1L);
            backgroundBannerRepository.save(banner);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return "redirect:/admin-banners";
    }

    @PostMapping("/save-banners2")
    public String saveBanners2(ActionNewsBannersDto bannersDto) {
        System.out.println("--- controllerDTO" + bannersDto);
        actionNewsBannerService.saveAllAndRotationSpeed(bannersDto);
        return "redirect:/admin-banners";
    }

    @GetMapping("del-back-banner")
    public String deleteBackBann() {
        backgroundBannerRepository.deleteById(1L);
        return "redirect:/admin-banners";
    }

//    @PostMapping("/admin-bannermain-card")
//    public String createBannermain(@ModelAttribute BannermainList bannmainlist, Model model, @RequestParam("filetmp") MultipartFile[] files) {
//        System.out.println(files);
//        List<Bannermain> bml = bannmainlist.getBannermainlist();
//        Long id = 1L;
//        for (Bannermain bannermain : bml) {
//            bannermain.setIdbannermain(id++);
//            bannermainService.save(bannermain);
//        }
//
//        return "redirect:/admin-banners";
//    }
//
//    @PostMapping("/admin-bannermain-card-add")
//    public String BannermainAddPict(@ModelAttribute BannermainList bannmainlist, Model model) {
//
//        List<Bannermain> bml = bannmainlist.getBannermainlist();
//        bml.add(new Bannermain());
//        Long id = 1L;
//        for (Bannermain bannermain : bml) {
//            bannermain.setIdbannermain(id++);
//
//            bannermainService.save(bannermain);
//        }
//
//        return "redirect:/admin-banners";
//    }

//    @GetMapping("banmain-delete/{id}")
//    public String deleteBanMain(@PathVariable("id") Long id) {
//        bannermainService.deleteById(id);
//        return "redirect:/admin-banners";
//    }
//    @PostMapping("/admin-banner-card-add2")
//    public String createBanner2(@RequestParam ("idbanner") Long idbanner, @RequestParam("picture1") MultipartFile file) {
//        Bannermain bannermain2 = new Bannermain();
//        bannermain2.setIdbanner(idbanner);
//        if (file.getSize() > 0) {
//            uploadDir = new File(uploadPath);
//            if (!uploadDir.exists()) {
//                uploadDir.mkdirs();
//            }
//            String uuid = UUID.randomUUID().toString();
//            String FileNameUuid = uuid + "-" + file.getOriginalFilename();
//            try {
//                file.transferTo(new File(uploadDir + "/" + FileNameUuid));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            bannermain2.setPicture1("/img/" + FileNameUuid);
//        }
//        bannermainService.save(bannermain2);
//
//        return "redirect:/admin-banners";
//    }
//    @PostMapping("/admin-banner-card-add3")
//    public String createBanner3(@RequestParam ("idbanner") Long idbanner, @RequestParam(name = "status", defaultValue = "false") Boolean status,
//                               @RequestParam("speed") Integer speed, @RequestParam("picture1") MultipartFile[] files) {
//        Bannermain bannermain3 = new Bannermain();
//        bannermain3.setIdbanner(idbanner);
////        Boolean sts;
//        if (files.length > 0) {
//            for (MultipartFile file1 : files) {
//                if (file1.getSize() > 0) {
//                    uploadDir = new File(uploadPath);
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
//                    if (bannermain3.getPicture1() == null) {
//                        bannermain3.setPicture1("/img/" + FileNameUuid);
//                    } else if (bannermain3.getPicture2() == null) {
//                        bannermain3.setPicture2("/img/" + FileNameUuid);
//                    } else if (bannermain3.getPicture3() == null) {
//                        bannermain3.setPicture3("/img/" + FileNameUuid);
//                    } else if (bannermain3.getPicture4() == null) {
//                        bannermain3.setPicture4("/img/" + FileNameUuid);
//                    } else if (bannermain3.getPicture5() == null) {
//                        bannermain3.setPicture5("/img/" + FileNameUuid);
//                    } else if (bannermain3.getPicture6() == null) {
//                        bannermain3.setPicture6("/img/" + FileNameUuid);
//                    } else if (bannermain3.getPicture7() == null) {
//                        bannermain3.setPicture7("/img/" + FileNameUuid);
//                    } else if (bannermain3.getPicture8() == null) {
//                        bannermain3.setPicture8("/img/" + FileNameUuid);
//                    } else if (bannermain3.getPicture9() == null) {
//                        bannermain3.setPicture9("/img/" + FileNameUuid);
//                    } else if (bannermain3.getPicture10() == null) {
//                        bannermain3.setPicture10("/img/" + FileNameUuid);
//                    }
//                }
//            }
//        }
////        if (status == true) {
////            sts = 1;
////        } else {
////            sts = 0;
////        }
//        bannermain3.setStatus(status);
//        bannermain3.setSpeed(speed);
//        bannermainService.save(bannermain3);
//
//        return "redirect:/admin-banners";
//    }
//

}
