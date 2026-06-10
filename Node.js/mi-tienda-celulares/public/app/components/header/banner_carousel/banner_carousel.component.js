import { carouselBanner } from '../../../core/api/producto.api.js';

export class BannerCarouselComponent {
    constructor() {
        this.inyectarEstilos();
    }

    inyectarEstilos() {
        const cssPath = 'app/components/header/banner_carousel/banner_carousel.component.css';
        if (!document.querySelector(`link[href="${cssPath}"]`)) {
            const link = document.createElement('link');
            link.rel = 'stylesheet';
            link.href = cssPath;
            document.head.appendChild(link);
        }
    }

    async render(container) {
        const responseHtml = await fetch('app/components/header/banner_carousel/banner_carousel.component.html');
        container.innerHTML = await responseHtml.text();

        await this.cargarBanners();
        this.configurarBotones();
    }

    async cargarBanners() {
        const track = document.getElementById('carousel-track');
        if (!track) return;

        try {
            const banners = await carouselBanner();
            track.innerHTML = ''; // Limpiamos la pista

            if (banners.length === 0) {
                track.innerHTML = `<div class="carousel-slide"><p style="text-align:center; padding: 50px;">No hay banners disponibles</p></div>`;
                return;
            }

            // Inyectamos cada banner en el HTML dinámicamente
            banners.forEach(banner => {
                const slide = document.createElement('div');
                slide.className = 'carousel-slide';
                slide.innerHTML = `
                    <a href="${banner.linkUrl}">
                        <img src="${banner.imageUrl}" alt="${banner.altText}">
                    </a>
                `;
                track.appendChild(slide);
            });
        } catch (error) {
            console.error("Error renderizando banners:", error);
        }
    }

    configurarBotones() {
        const track = document.getElementById('carousel-track');
        const btnPrev = document.getElementById('btn-prev');
        const btnNext = document.getElementById('btn-next');

        if (!track || !btnPrev || !btnNext) return;

        // Avanzamos o retrocedemos exactamente el ancho de un banner
        btnNext.addEventListener('click', () => {
            const slideWidth = track.clientWidth;
            track.scrollBy({ left: slideWidth, behavior: 'smooth' });
        });

        btnPrev.addEventListener('click', () => {
            const slideWidth = track.clientWidth;
            track.scrollBy({ left: -slideWidth, behavior: 'smooth' });
        });
    }
}