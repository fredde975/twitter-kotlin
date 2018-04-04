package com.example.twitterkotlin.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordItem implements Comparable{
    private String word;
    private Integer count;


    @Override
    public int compareTo(Object that) {
        return ((WordItem)that).count - this.count;
    }

}
