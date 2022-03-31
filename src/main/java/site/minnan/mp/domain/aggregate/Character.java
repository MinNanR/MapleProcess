package site.minnan.mp.domain.aggregate;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "maple_character")
@Data
public class Character {

    @Id
    @Column
    private Integer id;

    @Column
    private String name;
}
