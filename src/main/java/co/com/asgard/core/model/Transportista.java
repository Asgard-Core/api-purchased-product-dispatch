package co.com.asgard.core.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "transportistas")
public class Transportista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 100)
    private String empresa;

    @Column(length = 20)
    private String telefono;

    @Column(length = 100, unique = true)
    private String email;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    // Relación con Reportes
    @OneToMany(mappedBy = "transportista", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reportes;

    // Constructor vacío
    public Transportista() {}

    // Constructor con parámetros
    public Transportista(Long id, String nombre, String empresa, String telefono, String email, LocalDateTime fechaCreacion, List<Report> reportes) {
        this.id = id;
        this.nombre = nombre;
        this.empresa = empresa;
        this.telefono = telefono;
        this.email = email;
        this.fechaCreacion = fechaCreacion;
        this.reportes = reportes;
    }

    // Getters y Setters manuales
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<Report> getReportes() {
        return reportes;
    }

    public void setReportes(List<Report> reportes) {
        this.reportes = reportes;
    }
}