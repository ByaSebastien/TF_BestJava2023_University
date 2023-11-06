package be.bstorm.models.entities;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor @AllArgsConstructor
//Annotation qui specifie a jpa/hibernate que ceci n'est pas une table
//Mais une gestion d'heritage coté Java
@MappedSuperclass
@Data
//Type T (Genericité)
//Au moment d'heriter de base entity on devra specifier quel est ce type T (ex : String, Long, Integer)
public abstract class BaseEntity<T extends Serializable>{

    //Le type de l'id sera celui spécifié au moment de l'utilisation
    //La generecite peut servir a "retarder" la déclaration d'un type
    @Id
    @Getter
    private T id;
}
