package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();

        System.out.println("Исходный набор данных");
        flights.forEach(System.out::println);

        System.out.println("Применено правило 1: Удалены перелеты c вылетом раньше текущего момента времени");
        Rule1 rule1 = new Rule1(LocalDateTime.now());
        flights.stream().filter(rule1::check).forEach(System.out::println);

        System.out.println("Применено правило 2: Удалены перелеты, в которых есть сегменты с датой прилета, меньшей даты вылета");
        Rule2 rule2 = new Rule2();
        flights.stream().filter(rule2::check).forEach(System.out::println);

        System.out.println("Применено правило 3: Удалены перелеты, в которых суммарное время, проведенное на земле, больше 2 часов");
        Rule3 rule3 = new Rule3(2L);
        flights.stream().filter(rule3::check).forEach(System.out::println);

        System.out.println("Применены все три правила");
        flights.stream().filter(rule1::check).filter(rule2::check).filter(rule3::check).forEach(System.out::println);

    }
}