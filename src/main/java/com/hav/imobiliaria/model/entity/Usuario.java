package com.hav.imobiliaria.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hav.imobiliaria.model.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import software.amazon.awssdk.core.util.PaginatorUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @Column(nullable = false, length = 45)
    private String nome;

    @Column(length = 13)
    private String telefone;

    @Column
    private String senha;

    @Column(nullable = false, length = 45, unique = true)
    private String email;

    @Column(length = 500)
    private String descricao;

    @Column(length = 300)
    private String foto;

    @Column(columnDefinition = "BIT DEFAULT 1")
    private Boolean ativo;

    @Column
    private LocalDateTime dataDelecao;

    @Column(columnDefinition = "BIT DEFAULT 0")
    private Boolean autenticacaoDoisFatoresHabilitado;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "imovel_favorito_usuario",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_imovel")
    )
    private List<Imovel> imoveisFavoritados;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
    private List<Notificacao> notificacoes;

    @ManyToMany(mappedBy = "usuarios", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Chats> chats;


    public Page<Imovel> getImoveisFavoritosPaginados(Pageable pageable) {
        List<Imovel> imoveis = this.getImoveisFavoritados();
        return new PageImpl<>(imoveis, pageable, imoveis.size());
    }
    public void  adicionarImovelFavorito(Imovel imovel) {
        if(!this.imoveisFavoritados.contains(imovel)) {
            this.imoveisFavoritados.add(imovel);
        }
    }

    public void removerImovelFavorito(Long id) {
        imoveisFavoritados.removeIf(i -> i.getId().equals(id));
    }


    @PreRemove
    public void removerChats(){
        this.chats.forEach(chat ->  chat.setUsuarios(null));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Converte a RoleEnum para GrantedAuthority
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
    }

    @Override
    public String getPassword() {
        return this.getSenha();
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Ou lógica personalizada
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Ou lógica personalizada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Ou lógica personalizada
    }

    @Override
    public boolean isEnabled() {
        return this.ativo; // Usa o campo ativo
    }
}
