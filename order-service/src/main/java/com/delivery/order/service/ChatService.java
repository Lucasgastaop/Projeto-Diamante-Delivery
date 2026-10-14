package com.delivery.order.service;

import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.delivery.order.repository.DishRepository;

@Service
public class ChatService {

    private final ChatClient chatClient;
    private final DishRepository dishRepository;

    public ChatService(ChatClient.Builder chatClientBuilder, DishRepository dishRepository) {
        this.chatClient = chatClientBuilder.build();
        this.dishRepository = dishRepository;
    }

    public String answer(String question) {
        String menu = dishRepository.findAll().stream()
                .map(dish -> "- %s | price: %s | stock: %d | %s".formatted(
                        dish.getName(),
                        dish.getPrice(),
                        dish.getStock(),
                        dish.getDescription()))
                .collect(Collectors.joining("\n"));

        String system = """
                You are the attendant of a restaurant delivery app.
                Reply with short answers in Brazilian Portuguese.
                Use only the current menu below when talking about dishes, prices, ingredients, stock or suggestions.
                If the question is outside the restaurant topic, politely refuse and steer the conversation back to the menu.
                Current menu:
                %s
                """.formatted(menu);

        return chatClient.prompt()
                .system(system)
                .user(question)
                .call()
                .content();
    }
}
