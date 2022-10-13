package com.example.cinenademo.cinema.service.banners;

import com.example.cinenademo.cinema.model.banners.MainTopBanner;
import com.example.cinenademo.cinema.model.banners.MainTopBannerRotationSpeed;
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
public class MainTopBannerServiceImpl implements MainTopBannerService{
    private final MainTopBannerRepository mainTopBannerRepository;
    private final MainTopBannersRotationSpeedRepository rotationSpeedRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public List<MainTopBanner> findAll() {
        return mainTopBannerRepository.findAll();
    }

    @Override
    public MainTopBannerRotationSpeed findRotationSpeed() {
        return rotationSpeedRepository.findFirstById(1).orElse(new MainTopBannerRotationSpeed());
    }

    @Override
    @Transactional
    public void saveAllAndRotationSpeed(MainTopBannersDto dto) {
        List<MainTopBanner> banners = new ArrayList<>();
        try {
            /* Добавление новых баннеров */
            for (int i=0; i<dto.getIds().size(); i++){
                MainTopBanner banner = mainTopBannerRepository.findById(dto.getIds().get(i)).orElse(new MainTopBanner());
                if (!dto.getImages().get(i).isEmpty()) {
                    if (banner.getId()!=0) Files.delete(Path.of(uploadPath + banner.getImagePath()));
                    UUID uuid = UUID.randomUUID();
                    Files.copy(dto.getImages().get(i).getInputStream(),
                            Path.of(uploadPath+"img/main_top_banners/"+uuid+dto.getImages().get(i).getOriginalFilename()));
                    banner.setImagePath("/img/main_top_banners/"+uuid+dto.getImages().get(i).getOriginalFilename());
                }
                banner.setUrl(dto.getUrls().get(i));
                banner.setText(dto.getTexts().get(i));
                banners.add(banner);
            }

            /* Удаление баннеров, которых нет на странице */
            for(MainTopBanner banner: mainTopBannerRepository.findAllByIdNotIn(dto.getIds())){
                Files.delete(Path.of(uploadPath+banner.getImagePath()));
                mainTopBannerRepository.deleteById(banner.getId());
            }

            /* Сохранение баннеров */
            mainTopBannerRepository.saveAll(banners);

            /* Сохранение скорости вращения */
            MainTopBannerRotationSpeed rotationSpeed = rotationSpeedRepository.findFirstById(1).orElse(new MainTopBannerRotationSpeed());
            rotationSpeed.setRotationSpeed(dto.rotationSpeed);
            rotationSpeedRepository.save(rotationSpeed);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
