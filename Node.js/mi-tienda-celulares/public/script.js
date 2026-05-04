document.addEventListener('DOMContentLoaded' ,() => {
    cargarProductos();
});

function cargarProductos() {
    console.log("Intentando conectar con el Backend en el puerto 8080...");

    fetch('http://localhost:8080/api/productos')
        .then(response => {
            // Verificamos si la respuesta es exitosa
            console.log("Estado de la respuesta:", response.status);
            return response.text(); // Primero lo leemos como texto puro
        })
        .then(textoRaw => {
            console.log("Datos brutos (RAW) recibidos del Backend:", textoRaw);
            
            // Intentamos convertir ese texto a un objeto JSON
            const data = JSON.parse(textoRaw);
            console.log("Datos convertidos a JSON con éxito:", data);

            const tbody = document.getElementById('cuerpo-tabla');
            tbody.innerHTML = ''; 

            data.forEach(celular => {
                const fila = document.createElement('tr');
                fila.innerHTML = `
                    <td>${celular.id}</td>
                    <td>${celular.marca}</td>
                    <td>${celular.modelo}</td>
                    <td style="color: #00aae4; front-weight: bold;">
                        $${new Intl.NumberFormat('es-AR').format(celular.precio)}
                    </td>
                    <td><button>Quitar</button></td>
                `;
                tbody.appendChild(fila);
            });
        })
        .catch(error => {
            console.error('--- ERROR DETECTADO ---');
            console.error('Mensaje:', error.message);
        });
}