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
@Table(name = "SEMILLAMOODLE", schema = "CAMPOSAPRENDIZAJEUDEC")
public class SemillaMoodle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "semillamoodle_seq")
    @SequenceGenerator(name = "semillamoodle_seq", sequenceName = "CAMPOSAPRENDIZAJEUDEC.S_SEMO_ID", allocationSize = 1)
    @Column(name = "SEMO_ID")
    private Long semoId;

    @Column(name = "CADI_ID")
    private Long cadiId;

    @Column(name = "CAI_ID")
    private Long caiId;

    @Column(name = "PENS_ID")
    private Long pensId;

    @Column(name = "SEMO_IDMOODLE", nullable = false)
    private String semoIdMoodle;

    @Column(name = "SEMO_NOMBRELARGO", nullable = false)
    private String semoNombreLargo;

    @Column(name = "SEMO_NOMBRECORTO", nullable = false)
    private String semoNombreCorto;

    @Column(name = "MATE_CODIGOMATERIA")
    private String mateCodigoMateria;

    @Column(name = "SEMO_REGISTRADOPOR")
    private String semoRegistradoPor;

    @Column(name = "SEMO_FECHACAMBIO")
    private Timestamp semoFechaCambio;

    @Column(name = "SEMO_IDCATEGORIA")
    private String semoIdCategoria;

    @Column(name = "SEMI_ESTADO")
    private String semiEstado;
}
