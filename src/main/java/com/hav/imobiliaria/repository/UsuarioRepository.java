package com.hav.imobiliaria.repository;

import com.hav.imobiliaria.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    Page<Usuario> findByNomeContaining(String nome, Pageable pageable);


    Optional<Usuario> findByTelefone(String telefone);

    List<Usuario> findByDeletadoFalse();

    Page<Usuario> findByDeletadoFalse(Pageable pageable);

}
