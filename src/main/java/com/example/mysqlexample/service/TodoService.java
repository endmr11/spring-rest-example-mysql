package com.example.mysqlexample.service;

import com.example.mysqlexample.entity.Todo;
import com.example.mysqlexample.entity.User;
import com.example.mysqlexample.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;
    public Todo findTodoById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    public void deleteTodo(Todo todo){
        todoRepository.delete(todo);
    }
}
