import java.io.*;
import java.net.*;

public class Client21 {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8030)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                System.out.println("Сервер: " + serverMessage);

                // Если сервер ждет ответа игрока
                if (serverMessage.contains("Хотите взять еще карту?")) {
                    System.out.print("Ваш ответ: ");
                    String response = userInput.readLine();
                    out.println(response);
                }

                // Завершаем, если сервер сообщил об окончании игры
                if (serverMessage.contains("Игра окончена")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
