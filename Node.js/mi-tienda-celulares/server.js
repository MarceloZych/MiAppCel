const http = require('node:http');
const fs = require('node:fs');
const path = require('node:path');

const PORT = 3000;
const server = http.createServer((req, res) => {
    let urlPath = req.url === '/' ? 'index.html' : req.url;
    const filePath = path.join(__dirname, 'public', urlPath);
        
    const extname = path.extname(filePath);

    const mimeTypes = {
        '.html': 'text/html',
        '.js': 'text/javascript',
        '.css': 'text/css',
        '.json': 'application/json',
        '.png': 'image/png',
        '.jpg': 'image/jpg'
    };

    //let contentType = 'text/html';
    const contentType = mimeTypes[extname] || 'application/octet-stream';
                    
    fs.readFile(filePath, (err, content) => {
       if (err) {
            if (err.code === 'ENOENT') {
                console.error(`Archivo no encontrado: ${filePath}`);
                res.writeHead(404);
                res.end('Error 404: Archivo no encontrado');
            } else {
                console.error(`Error de servidor: ${err.code}`);
                res.writeHead(500);
                res.end(`Error interno: ${err.code}`);
            }
        } else {
            res.writeHead(200, { 'Content-Type': contentType });
            res.end(content, 'utf-8');
        }    
    }); 
});

// Manejo de errores de encendido (ej. puerto ocupado)
server.on('error', (e) => {
    if (e.code === 'EADDRINUSE') {
        console.error(`¡Error! El puerto ${PORT} ya está siendo usado por otra aplicación.`);
    } else {
        console.error(e);
    }
});

server.listen(PORT, () => {
    console.log(`--- Servidor Front-end Iniciado ---`);
    console.log(`Local: http://localhost:${PORT}`);
    console.log(`Carpeta base: ${path.join(__dirname, 'public')}`);
});