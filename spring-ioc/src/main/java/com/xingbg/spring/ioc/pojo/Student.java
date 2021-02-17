package com.xingbg.spring.ioc.pojo;

import java.util.*;

public class Student {
    private String name;
    private String[] books;
    private List<String> hobbys;
    private Map<String,String> cards;
    private Set<String> games;
    private Properties info;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", books=" + Arrays.toString(books) +
                ", hobbys=" + hobbys +
                ", cards=" + cards +
                ", games=" + games +
                ", info=" + info +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBooks(String[] books) {
        this.books = books;
    }

    public void setHobbys(List<String> hobbys) {
        this.hobbys = hobbys;
    }

    public void setCards(Map<String, String> cards) {
        this.cards = cards;
    }

    public void setGames(Set<String> games) {
        this.games = games;
    }

    public void setInfo(Properties info) {
        this.info = info;
    }



    public String getName() {
        return name;
    }

    public String[] getBooks() {
        return books;
    }

    public List<String> getHobbys() {
        return hobbys;
    }

    public Map<String, String> getCards() {
        return cards;
    }

    public Set<String> getGames() {
        return games;
    }

    public Properties getInfo() {
        return info;
    }
}
