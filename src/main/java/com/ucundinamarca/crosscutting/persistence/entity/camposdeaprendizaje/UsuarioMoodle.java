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
@Table(name = "USUARIOMOODLE", schema = "CAMPOSAPRENDIZAJEUDEC")
public class UsuarioMoodle {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuariomoodle_seq")
    @SequenceGenerator(name = "usuariomoodle_seq", sequenceName = "CAMPOSAPRENDIZAJEUDEC.S_USMO_ID", allocationSize = 1)
    @Column(name = "USMO_ID")
    private Long usmoId;

    @Column(name = "INST_ID", nullable = false)
    private Long instId;

    @Column(name = "TIUS_ID", nullable = false)
    private Long tiusId;

    @Column(name = "PEGE_ID", nullable = false)
    private Long pegeId;

    @Column(name = "USMO_USUARIO", nullable = false)
    private String usmoUsuario;

    @Column(name = "AREA_ID")
    private Long areaId;

    @Column(name = "PROG_ID")
    private Long progId;

    @Column(name = "ESTP_ID")
    private Long estpId;

    @Column(name = "UNID_ID")
    private Long unidId;

    @Column(name = "USMO_REGISTRADOPOR")
    private String usmoRegistradoPor;

    @Column(name = "USMO_FECHACAMBIO")
    private Timestamp usmoFechaCambio;

    @Column(name = "USMO_IDMOODLE")
    private String usmoIdMoodle;


}
