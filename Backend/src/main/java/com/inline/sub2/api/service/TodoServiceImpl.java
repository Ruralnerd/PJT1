package com.inline.sub2.api.service;

import com.inline.sub2.db.entity.TodoEntity;
import com.inline.sub2.db.repository.TodoRepository;
import com.inline.sub2.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService{

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public List<TodoEntity> UserTodoList(Long userId) {
        return todoRepository.findByUserId(userId);
    }

    @Override
    public TodoEntity registTodo(TodoEntity todoEntity) {
        todoEntity.setTodoDate(new Date());


        return todoRepository.save(todoEntity);
    }

    @Override
    public TodoEntity updateTodo(TodoEntity todoEntity) {
        todoEntity.setTodoDate(new Date());
        return todoRepository.save(todoEntity);



    }
    @Override
    public TodoEntity findUserIdByTodoId(Long todoId) {
        return todoRepository.findUserIdByTodoId(todoId);
    }

    @Override
    public void deleteTodo(Long todoId) {



    }
}
