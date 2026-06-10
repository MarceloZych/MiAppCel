package org.example.service;

import org.example.models.BannerSlide;
import org.example.repository.BannerSlideRepository;

import java.util.List;

public class BannerSlideService {
    private final BannerSlideRepository bannerSlideRepository;

    public BannerSlideService(BannerSlideRepository bannerSlideRepository) {
        this.bannerSlideRepository = bannerSlideRepository;
    }

    public List<BannerSlide> listarBannerSlide() {
        return bannerSlideRepository.obtenerBannerSlideActivos();
    }
}
