package es.accesoadatos.controladores.seguridad;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.accesoadatos.Constantes;
import es.accesoadatos.modelos.Articulo;

public class ControladorImportaciones {

    public static List<Articulo> importarDatos(File archivo) throws UnsupportedEncodingException {

        if (archivo.getName().endsWith(".csv")) {
            try (BufferedReader lector = new BufferedReader(
                    new InputStreamReader(archivo.toURI().toURL().openStream()))) {
                List<Articulo> articulos = List.of();
                String line;
                while ((line = lector.readLine()) != null) {
                    String[] campos = line.split(";");
                    articulos.add(new Articulo(Integer.parseInt(campos[0]), campos[1], Double.parseDouble(campos[2]),
                            campos[3], Integer.parseInt(campos[4]), Integer.parseInt(campos[5])));
                }
                return articulos;
            } catch (IOException io) {
                Logger.getLogger(Constantes.NOMBRE_LOGGER).log(Level.SEVERE,
                        "[ControladorImportaciones.class]: No se ha podido leer el archivo: " + archivo + " devido a: "
                                + io.getMessage());
            }
        } else if (archivo.getName().endsWith(".json")) {
            // TODO : ES PROBABLE QUE FALLE
            try (BufferedReader lector = new BufferedReader(
                    new InputStreamReader(archivo.toURI().toURL().openStream()))) {
                StringBuilder jsonBuilder = new StringBuilder();
                String line;
                while ((line = lector.readLine()) != null) {
                    jsonBuilder.append(line);
                }
                String jsonData = jsonBuilder.toString();
                JSONArray jsonArray = new JSONArray(jsonData);
                List<Articulo> articulos = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Articulo articulo = new Articulo(
                            jsonObject.getInt("codigo"),
                            jsonObject.getString("denominacion"),
                            jsonObject.getDouble("pvp"),
                            jsonObject.getString("categoria"),
                            jsonObject.getInt("uv"),
                            jsonObject.getInt("stock"));
                    articulos.add(articulo);
                }
                return articulos;
            } catch (IOException | JSONException e) {
                Logger.getLogger(Constantes.NOMBRE_LOGGER).log(Level.SEVERE,
                        "[ControladorImportaciones.class]: No se ha podido leer o parsear el archivo: " + archivo
                                + " debido a: " + e.getMessage());
            }

        } else {
            throw new UnsupportedEncodingException("[ControladorImportaciones.class]: Formato de archivo no soportado");
        }

        return null;
    }

}
