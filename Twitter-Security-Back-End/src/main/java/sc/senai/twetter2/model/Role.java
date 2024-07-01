package sc.senai.twetter2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_roles")
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

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
