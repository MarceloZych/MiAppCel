/**
 * Convierte un número flotante o entero a formato de divisa de Argentina (ARS).
 * @param {number} valor - El precio bruto del producto
 * @returns {string} El precio formateado con el signo $ y separadores de miles/centavos
 */

export function formatearMoneda(valor) {
    if (isNaN(valor) || valor === null) return "$0,00";
    return new Intl.NumberFormat('es-AR', {
        style: 'currency',
        currency: 'ARS'
    }).format(valor);
}