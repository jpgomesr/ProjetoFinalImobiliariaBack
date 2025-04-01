package com.hav.imobiliaria.repository;

import com.hav.imobiliaria.model.entity.Imovel;
import com.hav.imobiliaria.model.entity.Usuario;
import com.hav.imobiliaria.model.enums.RoleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    Page<Usuario> findByNomeContaining(String nome, Pageable pageable);

    Boolean existsByEmail(String email);
    Boolean existsByTelefone(String telefone);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByTelefone(String telefone);

    List<Usuario> findByAtivoTrue();

    List<Usuario> findByRoleAndAtivoTrue(RoleEnum role);

    Page<Usuario> findAll(Specification<Usuario> specs, Pageable pageable);
  
    @Query(value = "SELECT i FROM Imovel i JOIN i.usuariosFavoritos u WHERE u.id = :usuarioId")
    Page<Imovel> findImoveisFavoritadosByUsuarioId(@Param("usuarioId") Long usuarioId, Pageable pageable);
  
    @Query("SELECT u FROM Usuario u WHERE u.role = :role")
    List<Usuario> buscarPorRole(@Param("role") RoleEnum role);

    @Query(value = "SELECT i.id FROM Imovel i JOIN i.usuariosFavoritos u WHERE u.id = :usuarioId")
    List<Long> findIdImoveisFavoritadosByUsuarioId(@Param("usuarioId") Long usuarioId);

    Long id(Long id);
}
