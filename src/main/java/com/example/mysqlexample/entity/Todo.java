package com.example.mysqlexample.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private  Long id;
    private  String content;
    @Builder.Default
    private Boolean completed = Boolean.FALSE;

    @ManyToOne
    @JsonIgnoreProperties("todo")
    @JoinTable(name = "users_todos",
            joinColumns =  @JoinColumn(name = "todo_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User user;

}


//@Entity:
//Bu anotasyon, bir sınıfın JPA varlığı olduğunu belirtir.
//Veritabanındaki bir tabloya karşılık gelecek şekilde tasarlanmış sınıflar için kullanılır.

//@Id:
//Bu anotasyon, bir alanın veritabanı tablosundaki birincil anahtar (primary key) olduğunu belirtir.

//@GeneratedValue:
//Bu anotasyon, birincil anahtar değerlerinin nasıl oluşturulacağını belirtir.
//Otomatik artan (auto-increment) veya diğer yöntemlerle değerleri oluşturabilirsiniz.

//@Column:
//Bu anotasyon, bir alanın veritabanı sütunuyla nasıl eşleşeceğini belirtir.
//Özel sütun adları veya veri tipleri belirleyebilirsiniz.

//@OneToMany, @ManyToOne, @OneToOne, @ManyToMany:
//Bu ilişki anotasyonları, farklı varlık sınıfları arasındaki ilişkileri belirtmek için kullanılır.
//Örneğin, bir varlık sınıfının diğer bir varlık sınıfıyla ilişkisini belirtmek için @OneToMany veya @ManyToOne kullanabilirsiniz.

//@Data:
//Java sınıflarını basit ve hızlı bir şekilde veri sınıfı (POJO - Plain Old Java Object) olarak oluşturmayı kolaylaştıran bir anotasyondur.

//@Builder:
// Lombok kütüphanesinde yer alan ve özellikle nesne oluşturma sürecini daha okunabilir ve esnek hale getiren bir anotasyondur.