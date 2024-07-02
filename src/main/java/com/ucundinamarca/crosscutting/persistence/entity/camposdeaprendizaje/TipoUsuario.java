package com.ucundinamarca.crosscutting.persistence.entity.camposdeaprendizaje;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "TIPOUSUARIO", schema = "CAMPOSAPRENDIZAJEUDEC")
public class TipoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipousuario_seq")
    @SequenceGenerator(name = "tipousuario_seq", sequenceName = "CAMPOSAPRENDIZAJEUDEC.S_TIUM_ID", allocationSize = 1)
    @Column(name = "TIUM_ID")
    private Long tiumId;

    @Column(name = "TIUM_NOMBRE", nullable = false)
    private String tiumNombre;

    @Column(name = "TIUM_DESCRIPCION", nullable = false)
    private String tiumDescripcion;

    @Column(name = "TIUM_REGISTRADOPOR")
    private String tiumRegistradoPor;

    @Column(name = "TIUM_FECHACAMBIO")
    private Timestamp tiumFechaCambio;
}
