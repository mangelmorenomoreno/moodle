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
public class MatriculaMoodle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "matriculamoodle_seq")
    @SequenceGenerator(name = "matriculamoodle_seq", sequenceName = "CAMPOSAPRENDIZAJEUDEC.S_MAMO_ID", allocationSize = 1)
    @Column(name = "MAMO_ID")
    private Long mamoId;

    @Column(name = "USMO_ID", nullable = false)
    private Long usmoId;

    @Column(name = "GRSE_ID", nullable = false)
    private Long grseId;

    @Column(name = "ROMO_ID", nullable = false)
    private Long romoId;

    @Column(name = "MAMO_REGISTRADOPOR")
    private String mamoRegistradoPor;

    @Column(name = "MAMO_FECHACAMBIO")
    private Timestamp mamoFechaCambio;
}
