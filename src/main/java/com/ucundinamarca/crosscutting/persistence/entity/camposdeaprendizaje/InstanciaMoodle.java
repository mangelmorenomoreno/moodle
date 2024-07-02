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
@Table(name = "INSTANCIAMOODLE", schema = "CAMPOSAPRENDIZAJEUDEC")
public class InstanciaMoodle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "instanciamoodle_seq")
    @SequenceGenerator(name = "instanciamoodle_seq", sequenceName = "CAMPOSAPRENDIZAJEUDEC.S_INST_ID", allocationSize = 1)
    @Column(name = "INST_ID")
    private Long instId;

    @Column(name = "INST_NOMBRE", nullable = false)
    private String instNombre;

    @Column(name = "INST_DESCRIPCION", nullable = false)
    private String instDescripcion;

    @Column(name = "INST_ESTADO", nullable = false)
    private String instEstado;

    @Column(name = "INST_URL", nullable = false)
    private String instUrl;

    @Column(name = "INST_REGISTRADOPOR")
    private String instRegistradoPor;

    @Column(name = "INST_FECHACAMBIO")
    private Timestamp instFechaCambio;

}
