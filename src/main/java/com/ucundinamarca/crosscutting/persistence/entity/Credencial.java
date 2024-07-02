package com.ucundinamarca.crosscutting.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Credencial.
 *
 * @author miguel.moreno
 * @version 1.0
 * @since 2024-03-10
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "credenciales")
public class Credencial {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "credencial_id")
  private Integer credencialId;

  @Column(name = "hash_contraseña", nullable = false)
  private String hashContrasena;

  @Column(name = "token_reset_password")
  private String tokenResetPassword;


  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private Usuario usuario;


}
