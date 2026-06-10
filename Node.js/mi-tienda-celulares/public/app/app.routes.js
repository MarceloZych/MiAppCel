import { InventarioComponent } from './components/inventario/inventario.component.js';
import { NavbarTipoClienteComponent } from './components/header/navbar_tipo_cliente/navbar_tipo_cliente.component.js';
import { NavbarSectionComponent } from './components/header/navbar_section/navbar_section.component.js';
import { BannerCarouselComponent } from './components/header/banner_carousel/banner_carousel.component.js';

let NavbarTipoClienteRenderizado = false;
let NNavbarSectionRenderizado = false;
let bannerRenderizado = false;

// instancia de componentes NavbarTipoClienteComponent
const NavbarTipoCliente = new NavbarTipoClienteComponent();

// instancia de componentes NavbarSectionComponent
const NavbarSection = new NavbarSectionComponent();

// instancia de componentes BannerCarouselComponent
const BannerCarousel = new BannerCarouselComponent();

// Definición de rutas como lo harías en Angular Routes
const routes = {
    '/inventario': new InventarioComponent(),
};

async function router() {
    const outlet = document.getElementById('router-outlet');
    const NavbarTipoClienteOutlet = document.getElementById('NavbarTipoCliente-outlet');
    const NavbarSectionOutlet = document.getElementById('NavbarSection-outlet');

    try {
        if (!NavbarTipoClienteRenderizado && NavbarTipoClienteOutlet) {
            await NavbarTipoCliente.render(NavbarTipoClienteOutlet);
            NavbarTipoClienteRenderizado = true;
            console.log("✅ NavbarTipoCliente renderizado con éxito.");
        }
    } catch (error) {
        console.error("❌ ERROR AL CARGAR EL NavbarTipoCliente:", error);
    }    

     try {
        if (!NNavbarSectionRenderizado && NavbarSectionOutlet) {
            await NavbarSection.render(NavbarSectionOutlet);
            NNavbarSectionRenderizado = true;
            console.log("✅ NavbarSection renderizado con éxito.");
        }
    } catch (error) {
        console.error("❌ ERROR AL CARGAR EL NavbarSection:", error);
    }   
    
    // Buscamos el componente asociado a la ruta
    const component = routes['/inventario'];
    
    if (component) {
        // Si el componente tiene un método render (como nuestra clase), lo ejecutamos
        if (typeof component.render === 'function') {
            await component.render(outlet);
        } else {
            component.render(outlet);
        }
    } else {
        outlet.innerHTML = '<h2>Error 404</h2><p>La página que buscas no existe.</p>';
    }

    if (!bannerRenderizado && document.getElementById('banner-outlet')) {
        await BannerCarousel.render(document.getElementById('banner-outlet'));
        bannerRenderizado = true;
    }
}

// Escuchamos cuando cambia la URL o cuando la página se carga por primera vez
window.addEventListener('hashchange', router);
window.addEventListener('load', router);