package es.accesoadatos.modelos;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.accesoadatos.Constantes;
import es.accesoadatos.modelos.interfaces.Imprimible;

public class Articulo implements Imprimible, Serializable {

    private Integer codigo;
    private String denominacion;
    private Double pvp;
    private String categoria;
    private Integer uv;
    private Integer stock;

    public Articulo(int codigo, String denominacion, double pvp, String categoria, int uv, int stock) {
        this.codigo = codigo;
        this.denominacion = denominacion;
        this.pvp = pvp;
        this.categoria = categoria;
        this.uv = uv;
        this.stock = stock;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public double getPvp() {
        return pvp;
    }

    public void setPvp(double pvp) {
        this.pvp = pvp;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getUv() {
        return uv;
    }

    public void setUv(int uv) {
        this.uv = uv;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Articulos [codigo=" + codigo + ", denominacion=" + denominacion + ", pvp=" + pvp
                + ", categoria="
                + categoria + ", uv=" + uv + ", stock=" + stock + "]";
    }

    @Override
    public String aCSV() {
        StringBuilder sb = new StringBuilder();
        sb.append(codigo).append(";").append(denominacion).append(";").append(pvp).append(";")
                .append(categoria)
                .append(";").append(uv).append(";").append(stock);
        return sb.toString();
    }

    @Override
    public String aJSON() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"codigo\":").append(codigo).append(", \"denominacion\":\"").append(denominacion)
                .append("\", \"pvp\":").append(pvp).append(", \"categoria\":\"").append(categoria)
                .append("\", \"uv\":").append(uv).append(", \"stock\":").append(stock).append("}");
        return sb.toString();
    }

}