package com.example.mysqlexample.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity //Java sınıflarını veritabanı tablolarına eşleştirmek için kullanılır
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Birincil anahtarımız için Hibernate'nin birbirinden farklı değer üreten üretecin özelliklerinin tanımlanmasını sağlayan bir anaotasyonudur
    @Column(name = "user_id")
    private Long id;
    private String username;
    private String password;
    private String roles;

    @JsonIgnoreProperties("user")
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinTable(name = "users_todos",
            joinColumns =  @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "todo_id"))

    final private List<Todo> todoList = new ArrayList<>();
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