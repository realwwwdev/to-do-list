package dao;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskDao implements TaskDao {

    private final List<Task> tasks = new ArrayList<>();
    private int idSeq = 1;

    @Override
    public Task add(Task task) {
        Task t = new Task(idSeq++, task.text(), task.done(), task.deadline());
        tasks.add(t);
        return t;
    }

    @Override
    public void delete(int id) {
        tasks.removeIf(t -> t.id() == id);
    }

    @Override
    public Task update(Task task) {
        delete(task.id());
        tasks.add(task);
        return task;
    }

    @Override
    public Task findById(int id) {
        return tasks.stream()
                .filter(t -> t.id() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Task> findAll() {
        return List.copyOf(tasks);
    }
}
