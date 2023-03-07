package com.example.cinenademo.cinema.service.banners;

import com.example.cinenademo.cinema.model.banners.ActionNewsBanner;
import com.example.cinenademo.cinema.model.banners.ActionNewsBannerRotationSpeed;
import com.example.cinenademo.cinema.model.banners.MainTopBanner;
import com.example.cinenademo.cinema.model.banners.MainTopBannerRotationSpeed;
import com.example.cinenademo.cinema.repository.banners.ActionNewsBannerRepository;
import com.example.cinenademo.cinema.repository.banners.ActionNewsBannersRotationSpeedRepository;
import com.example.cinenademo.cinema.repository.banners.MainTopBannerRepository;
import com.example.cinenademo.cinema.repository.banners.MainTopBannersRotationSpeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActionNewsBannerServiceImpl implements ActionNewsBannerService {
    private final ActionNewsBannerRepository actionNewsBannerRepository;
    private final ActionNewsBannersRotationSpeedRepository rotationSpeedRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public List<ActionNewsBanner> findAll() {
        return actionNewsBannerRepository.findAll();
    }

    @Override
    public ActionNewsBannerRotationSpeed findRotationSpeed() {
        return rotationSpeedRepository.findFirstById(1L).orElse(new ActionNewsBannerRotationSpeed());
    }

    @Override
    @Transactional
    public void saveAllAndRotationSpeed(ActionNewsBannersDto dto) {
        List<ActionNewsBanner> banners = new ArrayList<>();
        System.out.println("--- DTO id " + dto.getIds());
        System.out.println("--- DTO img " + dto.getImages());
        System.out.println("--- DTO url " + dto.getUrls());
        System.out.println("--- DTO rotSp " + dto.getRotationSpeed());
        System.out.println("--- DTO status " + dto.getStatus());

        if (dto.getIds() != null) {
            try {
                if (!(Files.exists(Path.of(uploadPath + "img/action_news_banners/"))))
                    Files.createDirectories(Path.of(uploadPath + "img/action_news_banners/"));
                /* Добавление новых баннеров */
                for (int i = 0; i < dto.getIds().size(); i++) {
                    ActionNewsBanner banner = actionNewsBannerRepository.findById(dto.getIds().get(i)).orElse(new ActionNewsBanner());
                    if (!dto.getImages().get(i).isEmpty()) {
                        if (banner.getId() != null) Files.delete(Path.of(uploadPath + banner.getImagePath()));
                        UUID uuid = UUID.randomUUID();
                        Files.copy(dto.getImages().get(i).getInputStream(),
                                Path.of(uploadPath + "img/action_news_banners/" + uuid + dto.getImages().get(i).getOriginalFilename()));
                        banner.setImagePath("/img/action_news_banners/" + uuid + dto.getImages().get(i).getOriginalFilename());
                    }
                    banner.setUrl(dto.getUrls().get(i));
                    banners.add(banner);
                }

                /* Удаление баннеров, которых нет на странице */
                for (ActionNewsBanner banner : actionNewsBannerRepository.findAllByIdNotIn(dto.getIds())) {
                    Files.delete(Path.of(uploadPath + banner.getImagePath()));
                    actionNewsBannerRepository.deleteById(banner.getId());
                }
                System.out.println("banners - " + banners);
                /* Сохранение баннеров */
                actionNewsBannerRepository.saveAll(banners);
                System.out.println("+++" + actionNewsBannerRepository.findAll());

                /* Сохранение скорости вращения */
                ActionNewsBannerRotationSpeed rotationSpeed = rotationSpeedRepository.findFirstById(1L).orElse(new ActionNewsBannerRotationSpeed());
                rotationSpeed.setRotationSpeed(dto.rotationSpeed);
                rotationSpeed.setStatus(dto.status);
                rotationSpeed.setId(1L);
                rotationSpeedRepository.save(rotationSpeed);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
