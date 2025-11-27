package app;

import dao.InMemoryTaskDao;
import service.TaskService;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        var service = new TaskService(new InMemoryTaskDao());
        var scanner = new Scanner(System.in);

        System.out.println("ToDo List запущен. Введите 'help', чтобы увидеть команды.");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equals("exit")) break;

            if (input.equals("help")) {
                System.out.println("""
                        Команды:
                          add "текст" YYYY-MM-DD
                          list
                          delete ID
                          edit ID "новый текст"
                          done ID
                          update-deadline ID YYYY-MM-DD
                          exit
                        """);
                continue;
            }

            try {
                handleCommand(input, service);
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }

        System.out.println("Пока!");
    }

    private static void handleCommand(String input, TaskService service) {

        switch (input.split(" ")[0]) {

            case "add" -> {
                String text = input.replaceFirst("add\\s+\"", "")
                        .replaceFirst("\"\\s+\\d{4}-\\d{2}-\\d{2}$", "");
                String dateStr = input.substring(input.length() - 10);
                LocalDate date = LocalDate.parse(dateStr);

                var t = service.addTask(text, date);
                System.out.println("Добавлена задача: " + t);
            }

            case "list" -> service.listTasks().forEach(System.out::println);

            case "delete" -> {
                int id = Integer.parseInt(input.split(" ")[1]);
                service.deleteTask(id);
                System.out.println("Удалено.");
            }

            case "edit" -> {
                int id = Integer.parseInt(input.split(" ")[1]);
                String newText = input.replaceFirst("edit\\s+\\d+\\s+\"", "")
                        .replaceFirst("\"$", "");

                var t = service.editTaskText(id, newText);
                System.out.println("Обновлено: " + t);
            }

            case "done" -> {
                int id = Integer.parseInt(input.split(" ")[1]);
                var t = service.setDone(id);
                System.out.println("Готово: " + t);
            }

            case "update-deadline" -> {
                String[] p = input.split(" ");
                int id = Integer.parseInt(p[1]);
                LocalDate date = LocalDate.parse(p[2]);

                var t = service.updateDeadline(id, date);
                System.out.println("Дедлайн обновлён: " + t);
            }

            default -> System.out.println("Неизвестная команда.");
        }
    }
}
