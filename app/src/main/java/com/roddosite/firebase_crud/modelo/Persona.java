package com.roddosite.firebase_crud.modelo;

public class Persona {
   private String id, nombre,telefono, mail, dirección;

    public Persona() {
    }

    public Persona(String id, String nombre, String telefono, String mail, String dirección) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.mail = mail;
        this.dirección = dirección;
    }

    public Persona(String nombre, String telefono, String mail, String dirección) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.mail = mail;
        this.dirección = dirección;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDirección() {
        return dirección;
    }

    public void setDirección(String dirección) {
        this.dirección = dirección;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
