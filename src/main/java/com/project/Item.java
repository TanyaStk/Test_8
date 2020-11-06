package com.project;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Item {
    private final SimpleDateFormat
            format = new SimpleDateFormat("dd.MM.yyyy");

    private String number;
    private String name;
    private Date deliveryDate;
    private String category;
    private double frequencyOFSearching;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(number, item.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, name, deliveryDate, category, frequencyOFSearching);
    }

    public Item(String inf) throws Exception {
        String[] words = inf.split(";");
        if (words.length < 5) {
            throw new Exception();
        }
        number = words[0];
        name = words[1];
        deliveryDate = format.parse(words[2]);
        category = words[3];
        frequencyOFSearching = Double.parseDouble(words[4]);
    }

    @Override
    public String toString() {
        return  "name='" + name + '\'' +
                ", deliveryDate=" + format.format(deliveryDate) +
                ", category='" + category + '\'' +
                ", frequencyOFSearching=" + frequencyOFSearching +
                '}';
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getFrequencyOFSearching() {
        return frequencyOFSearching;
    }

    public void setFrequencyOFSearching(double frequencyOFSearching) {
        this.frequencyOFSearching = frequencyOFSearching;
    }

}
