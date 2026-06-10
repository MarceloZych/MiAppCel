package org.example.util;

import java.lang.reflect.Field;

import java.util.List;
import java.util.Objects;

public class JsonUtils {
    /**
     * Convierte cualquier lista de objetos a formato JSON de forma dinámica.
     * @param "<T>" Tipo genérico de la entidad (Producto, Cliente, etc.)
     * @param "lista" Lista de elementos a serializar
     * @return String en formato JSON válido
     */

     public static <T> String convertirListaAJson(List<T> lista) {
       if (lista==null || lista.isEmpty()) {
            return "[]";
        }

        StringBuilder json = new StringBuilder("[");
        try {
            for (int i = 0; i < lista.size(); i++) {
                T objeto = lista.get(i);
                json.append("{");
                // Obtenemos todos los atributos de la clase dinámicamente
                Field[] campos = objeto.getClass().getDeclaredFields();
                for (int j = 0; j < campos.length; j++){
                    campos[j].setAccessible(true); // permitir leer atributos privados
                    String nombre = campos[j].getName();
                    Object valor = campos[j].get(objeto);

                    json.append("\"").append(nombre).append("\":");

                    // Validamos el tipo de dato para colocar comillas si es texto
                    if (valor instanceof Number || valor instanceof Boolean) {
                        json.append(valor);
                    } else {
                        json.append("\"").append(valor != null ? valor.toString() : "").append("\"");
                    }

                    if (j < campos.length - 1){
                        json.append(",");
                    }
                }
                json.append("}");

                if (i < lista.size() - 1) {
                    json.append(",");
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return "{\"error\": \"Error al serializar datos\"}";
        }
        json.append("]");
        return json.toString();
    }
}
