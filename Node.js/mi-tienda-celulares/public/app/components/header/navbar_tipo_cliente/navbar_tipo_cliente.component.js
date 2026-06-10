export class NavbarTipoClienteComponent {
    
    constructor() {
        this.inyectarEstilos();
    }

    inyectarEstilos() {
        const cssPath = 'app/components/header/navbar_tipo_cliente/navbar_tipo_cliente.component.css';
        if (!document.querySelector(`link[href="${cssPath}"]`)) {
            const link = document.createElement('link');
            link.rel = 'stylesheet';
            link.href = cssPath;
            document.head.appendChild(link);
        }
    }

    async render(container) {
        const responseHtml = await fetch('app/components/header/navbar_tipo_cliente/navbar_tipo_cliente.component.html');
        container.innerHTML = await responseHtml.text();
        this.marcarEnlaceActivo();

        window.addEventListener('hashchange', () => this.marcarEnlaceActivo());
        // Se dispara cuando cambia. Ejemplo: #/inicio, #/productos, #/contacto
    }

    marcarEnlaceActivo() {
        const currentHash = window.location.hash || '#/inicio';
        const links = document.querySelectorAll('.nav-item');
        
        links.forEach(link => {
            const targetRoute = link.getAttribute('data-route');
            if (targetRoute === currentHash) {
                link.classList.add('active-main');
            } else {
                link.classList.remove('active-main');
            }
        });
    }
}