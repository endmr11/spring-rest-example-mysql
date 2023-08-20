package com.example.mysqlexample.controller;

import com.example.mysqlexample.entity.Todo;
import com.example.mysqlexample.entity.User;
import com.example.mysqlexample.dao.AddUserRequest;
import com.example.mysqlexample.model.BaseResponse;
import com.example.mysqlexample.repository.TodoRepository;
import com.example.mysqlexample.service.TodoService;
import com.example.mysqlexample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    TodoService todoService;
    public UserController(UserService userService, TodoService todoService) {
        this.userService = userService;
        this.todoService = todoService;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<BaseResponse<List<User>>> getAllUsers(){
        final var response = new BaseResponse<List<User>>();
        try {
            response.setModel(userService.getAllUsers());
            response.setStatusCode(200);
            return ResponseEntity.status(200).body(response);
        }catch (Exception error){
            response.setMessage(error.getLocalizedMessage());
            response.setStatusCode(422);
            response.setModel(new ArrayList<>());
            return ResponseEntity.status(422).body(response);
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<BaseResponse<User>> addUser(@RequestBody AddUserRequest userRequest){
        final var response = new BaseResponse<User>();
        try {
            final var user =  User.builder()
                    .username(userRequest.getUsername())
                    .password(userRequest.getPassword())
                    .build();
            response.setStatusCode(200);
            response.setModel(userService.saveUser(user));
            return ResponseEntity.status(200).body(response);
        }catch (Exception error){
            response.setMessage(error.getLocalizedMessage());
            response.setStatusCode(422);
            response.setModel(null);
            return ResponseEntity.status(422).body(response);
        }
    }

    @DeleteMapping("{userId}/todos/{todoId}")
    public ResponseEntity<BaseResponse<Todo>> deleteTodo(@PathVariable Long todoId, @PathVariable Long userId){
        final var response = new BaseResponse<Todo>();
        try {
            final var user = userService.findUserById(userId);
            final var todo = todoService.findTodoById(todoId);
            user.getTodoList().remove(todo);
            todoService.deleteTodo(todo);
            userService.saveUser(user);
            response.setStatusCode(200);
            response.setModel(todo);
            return ResponseEntity.status(200).body(response);
        }catch (Exception error){
            response.setMessage(error.getLocalizedMessage());
            response.setStatusCode(422);
            response.setModel(null);
            return ResponseEntity.status(422).body(response);
        }
    }
}

//@RestController:
//Bu anotasyon, bir sınıfın bir RESTful web hizmeti uygulayacağını belirtir.
//Bu sınıf, JSON veya XML gibi veri formatlarını döndüren API endpoint'leri tanımlamak için kullanılır.
//Sınıf seviyesinde kullanılır.

//@Controller:
//Bu anotasyon, bir sınıfın web taleplerini işlemek için kullanılan bir Controller olduğunu belirtir.
//Genellikle HTML sayfalarını döndüren Spring MVC uygulamalarında kullanılır. Sınıf seviyesinde kullanılır.

//@RequestMapping: Bu anotasyon, bir methodun belirli bir URL isteğine nasıl tepki vereceğini belirtir.
//Hem sınıf seviyesinde hem de method seviyesinde kullanılabilir.
//Method seviyesinde kullanıldığında, URL yolu belirtilen özelliklere uygun olarak yönlendirilir.

//@GetMapping, @PostMapping, @PutMapping, @DeleteMapping:
//Bu anotasyonlar, sırasıyla GET, POST, PUT ve DELETE HTTP isteklerine tepki veren methodları belirtmek için kullanılır.
//Her biri, belirtilen URL yollarına göre uygun HTTP isteklerini işlemek için kullanılır.

//@RequestParam:
//Bu anotasyon, bir method parametresinin URL'deki sorgu parametrelerini nasıl alacağını belirtir.

//@PathVariable:
//Bu anotasyon, bir method parametresinin URL'deki değişken yollarından nasıl alınacağını belirtir.

//@RequestBody: Bu anotasyon, bir HTTP isteğinin gövdesindeki verileri bir Java nesnesine dönüştürmek için kullanılır.
//JSON veya XML verilerini işlemek için sıkça kullanılır.

//@ResponseBody: Bu anotasyon, bir methodun döndürdüğü değeri doğrudan HTTP yanıt gövdesine dönüştürmek için kullanılır.
//JSON veya XML gibi formatlarda veri döndürmek için kullanılır.

//@ModelAttribute: Bu anotasyon, method parametresini model verileriyle doldurmak için kullanılır.
//Genellikle HTML form verilerini işlemek için kullanılır.

//@Valid: Bu anotasyon, method parametresi olarak verilen nesnenin doğrulama işlemlerini başlatmak için kullanılır.
//Nesnenin üzerinde tanımlanan doğrulama anotasyonlarına dayanarak giriş doğrulaması gerçekleştirilir.
