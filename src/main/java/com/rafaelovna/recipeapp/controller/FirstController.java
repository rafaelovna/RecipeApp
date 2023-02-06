package com.rafaelovna.recipeapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {

    @GetMapping("/")
    public String helloWorld() {
        return "<h1 style=\"text-align:center\">Приложение запущено</h1>";
    }

    @GetMapping("/info")
    public String printInfo() {
        return """
                Ученик: Арутюнян Айкануш Рафаеловна. </br>
                Проект: Приложение рецептов. Recipe app. </br>
                Дата создания проекта: 06.02.2023. </br>
                Описание проекта: Простое и удобное приложение с большим количеством пошаговых рецептов.\s
                Готовить можно по инструкции с пошаговыми рекомендациями. Приложение помогает учиться готовить, совершенствовать отработанные навыки.""";
    }

}
