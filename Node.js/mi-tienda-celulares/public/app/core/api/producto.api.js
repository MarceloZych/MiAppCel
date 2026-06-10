const API_URL = 'http://localhost:8080/api/productos';
const API_BANNERS_URL = 'http://localhost:8080/api/banners';

export async function obtenerProductos() {
    try {
        const response = await fetch(API_URL);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }        
        return await response.json();
    }     catch (error) {
        console.error("Error al obtener productos desde el backend: ", error);
        throw error;
    }
}

export async function eliminarProductoAPI(id) {
    try {
        const response = await fetch(`${API_URL}?id=${id}`,{
            method: 'DELETE'
        });
        if (!response.ok) {
            throw new Error(`Error al intentar eliminar el producto`);
        }
        return true;
    } catch (error) {
        console.error("Error de red al eliminar: ", error);
        throw error;
    }
}

export async function carouselBanner() {
    try {
        const response = await fetch(API_BANNERS_URL);
        if (!response.ok) throw new Error('Error al obtener banners');

        const data = await response.json();
        // Ordenamos los banners por su propiedad "orden" de menor a mayor
        return data.sort((a, b) => a.orden - b.orden);
    } catch (error) {
        console.error("Fallo la comunicación con la API de Banners:", error);
        return [];
    }
}