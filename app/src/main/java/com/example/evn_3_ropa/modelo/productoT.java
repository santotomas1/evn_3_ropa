package com.example.evn_3_ropa.modelo;

public class productoT {
    private String uid;
    String producto, precio, codigo;
    public productoT(){}



    public productoT(String producto, String precio, String codigo) {
        this.producto = producto;
        this.precio = precio;
        this.codigo = codigo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return producto + " " +precio ;}
}



