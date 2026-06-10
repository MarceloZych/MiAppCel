import { obtenerProductos, eliminarProductoAPI } from '../../core/api/producto.api.js';

export class InventarioComponent {

    constructor() {
        this.inyectarEstilos();
    }

    inyectarEstilos() {
        const cssPath = 'app/components/inventario/inventario.component.css';
        if (!document.querySelector(`link[href="${cssPath}"]`)) {
            const link = document.createElement('link');
            link.rel = 'stylesheet';
            link.href = cssPath;
            document.head.appendChild(link);
        }
    }
    
    // Método equivalente al ngOnInit() de Angular
    async render(container) {
        // 1. Inyectamos la estructura HTML del componente
        const responseHtml = await fetch('app/components/inventario/inventario.component.html');
        container.innerHTML = await responseHtml.text();

        // 2. Ejecutamos la lógica de carga de datos
        await this.cargarProductosUI();
    }

    async cargarProductosUI() {
        const tbody = document.getElementById('cuerpo-tabla');
        if (!tbody) return;

        try {
            const productos = await obtenerProductos();
            tbody.innerHTML = '';

            productos.forEach(celular => {
                const fila = document.createElement('tr');
                fila.innerHTML = `
                    <td>${celular.id}</td>
                    <td>${celular.marca}</td>
                    <td style="font-weight: bold;">${celular.modelo}</td>
                    <td>${celular.color}</td>
                    <td>${celular.almacenamientoGb} GB</td>
                    <td>${celular.ramGb} GB</td>
                    <td style="font-weight: bold;">
                        $${new Intl.NumberFormat('es-AR').format(celular.precio)}
                    </td>
                    <td>${celular.stock} u.</td>
                    <td><img src="${celular.imagenUrl}" alt="${celular.modelo}"></td>
                    <td>${celular.es5g === "true" || celular.es5g === true ? 'Sí' : 'No'}</td>
                    <td><button data-id="${celular.id}" class="btn-eliminar">Quitar</button></td>
                `;
                tbody.appendChild(fila);
            });

            // Listeners locales del componente
            tbody.querySelectorAll('.btn-eliminar').forEach(boton => {
                boton.addEventListener('click', (e) => this.manejarEliminacion(e.target.getAttribute('data-id')));
            });

        } catch (error) {
            console.error("Error en InventarioComponent:", error);
        }
    }

    async manejarEliminacion(id) {
        if (confirm('¿Estás seguro de que quieres eliminar este producto?')) {
            try {
                await eliminarProductoAPI(id);
                await this.cargarProductosUI(); // Refresca los datos localmente
            } catch (error) {
                alert("Error al eliminar el producto.");
            }
        }
    }
}