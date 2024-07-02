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
@Table(name = "GRUPOSEMILLA", schema = "CAMPOSAPRENDIZAJEUDEC")
public class GrupoSemilla {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gruposemilla_seq")
    @SequenceGenerator(name = "gruposemilla_seq", sequenceName = "CAMPOSAPRENDIZAJEUDEC.S_GRSE_ID", allocationSize = 1)
    @Column(name = "GRSE_ID")
    private Long grseId;

    @Column(name = "SEMO_ID", nullable = false)
    private Long semoId;

    @Column(name = "GRUP_ID")
    private Long grupId;

    @Column(name = "PEUN_ID")
    private Long peunId;

    @Column(name = "INST_ID", nullable = false)
    private Long instId;

    @Column(name = "GRSE_IDMOODLE", nullable = false)
    private String grseIdMoodle;

    @Column(name = "GRSE_NOMBRECORTO", nullable = false)
    private String grseNombreCorto;

    @Column(name = "GRSE_NOMBRELARGO", nullable = false)
    private String grseNombreLargo;

    @Column(name = "GRSE_REGISTRADOPOR")
    private String grseRegistradoPor;

    @Column(name = "GRSE_FECHACAMBIO")
    private Timestamp grseFechaCambio;


}
