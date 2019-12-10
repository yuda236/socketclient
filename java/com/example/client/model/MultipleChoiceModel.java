package com.example.client.model;

public class MultipleChoiceModel {
    String Index;
    String Answer;

    public MultipleChoiceModel(String index, String answer, int next_id) {
        Index = index;
        Answer = answer;
        this.next_id = next_id;
    }

    public int getNext_id() {
        return next_id;
    }

    public void setNext_id(int next_id) {
        this.next_id = next_id;
    }

    int next_id;


    public MultipleChoiceModel() {
    }



    public MultipleChoiceModel(String index, String answer) {
        Index = index;
        Answer = answer;
    }


    public String getIndex() {
        return Index;
    }

    public void setIndex(String index) {
        Index = index;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }
}
