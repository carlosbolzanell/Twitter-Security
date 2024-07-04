package sc.senai.twetter2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Entity
@Table(name = "tb_roles")
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    private Set<Authorite> authoritys;

    @Getter
    public enum Values {
        ADMIN(1L),
        BASIC(2L);

        Long roleId;
        Values(Long roleId){
            this.roleId = roleId;
        }
    }
}
