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
@Table(name = "MATRICULAMOODLE", schema = "CAMPOSAPRENDIZAJEUDEC")
public class RolMoodle {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rolmoodle_seq")
    @SequenceGenerator(name = "rolmoodle_seq", sequenceName = "CAMPOSAPRENDIZAJEUDEC.S_ROMO_ID", allocationSize = 1)
    @Column(name = "ROMO_ID")
    private Long romoId;

    @Column(name = "ROMO_NOMBRE", nullable = false)
    private String romoNombre;

    @Column(name = "ROMO_DESCRIPCION")
    private String romoDescripcion;

    @Column(name = "ROMO_ESTADO", nullable = false)
    private String romoEstado;

    @Column(name = "ROMO_REGISTRADOPOR")
    private String romoRegistradoPor;

    @Column(name = "ROMO_FECHACAMBIO")
    private Timestamp romoFechaCambio;

}
