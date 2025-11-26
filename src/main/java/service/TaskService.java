package service;

import dao.TaskDao;
import model.Task;

import java.time.LocalDate;
import java.util.List;

public class TaskService {
    private final TaskDao dao;

    public TaskService(TaskDao dao) {
        this.dao = dao;
    }

    public Task addTask(String text, LocalDate deadline) {
        return dao.add(new Task(0, text, false, deadline));
    }

    public void deleteTask(int id) {
        dao.delete(id);
    }

    public Task editTaskText(int id, String newText) {
        var t = dao.findById(id);
        if (t == null) throw new RuntimeException("Задача не найдена");

        var updated = new Task(t.id(), newText, t.done(), t.deadline());
        return dao.update(updated);
    }

    public Task setDone(int id) {
        var t = dao.findById(id);
        if (t == null) throw new RuntimeException("Задача не найдена");

        var updated = new Task(t.id(), t.text(), true, t.deadline());
        return dao.update(updated);
    }

    public Task updateDeadline(int id, LocalDate deadline) {
        var t = dao.findById(id);
        if (t == null) throw new RuntimeException("Задача не найдена");

        var updated = new Task(t.id(), t.text(), t.done(), deadline);
        return dao.update(updated);
    }

    public List<Task> listTasks() {
        return dao.findAll();
    }
}
