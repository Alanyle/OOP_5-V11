import java.io.*;
import java.net.*;
import java.util.*;

public class Server21 {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8030)) {
            System.out.println("Сервер запущен, ожидаем подключения клиента...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Клиент подключен!");

            // Потоки для связи с клиентом
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            Random random = new Random();
            int playerScore = 0;
            int dealerScore = random.nextInt(6) + 15; // Дилер получает от 15 до 20 очков

            out.println("Добро пожаловать в игру «21». Ваша цель — набрать очков ближе к 21, чем дилер, не превышая 21.");
            boolean playerFinished = false;

            // Игровой цикл
            while (!playerFinished) {
                int card = random.nextInt(10) + 1; // Карты от 1 до 10
                playerScore += card;
                out.println("Вы получили карту: " + card + ". Ваши очки: " + playerScore);

                if (playerScore > 21) {
                    out.println("Вы проиграли! Ваши очки превысили 21.");
                    playerFinished = true;
                    break;
                }

                out.println("Хотите взять еще карту? (да/нет)");
                String response = in.readLine();
                if (response.equalsIgnoreCase("нет")) {
                    playerFinished = true;
                }
            }

            if (playerScore <= 21) {
                out.println("Очки дилера: " + dealerScore);
                if (dealerScore > 21 || playerScore > dealerScore) {
                    out.println("Поздравляем! Вы выиграли!");
                } else if (playerScore == dealerScore) {
                    out.println("Ничья!");
                } else {
                    out.println("Вы проиграли! Очки дилера выше.");
                }
            }

            out.println("Игра окончена. Спасибо за игру!");
            clientSocket.close();
            System.out.println("Соединение закрыто.");
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
