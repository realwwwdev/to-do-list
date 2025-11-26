package dao;

import model.Task;
import java.util.List;

public interface TaskDao {
    Task add(Task task);
    void delete(int id);
    Task update(Task task);
    Task findById(int id);
    List<Task> findAll();
}
