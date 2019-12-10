package com.example.client.model;

import java.util.List;

public class ChatModel {
    private int id;
    //1 = text, 2 = image, 3 = multiple choice
    private int typechat;
    private List<MultipleChoiceModel> multiplechoice;
    private String imagesrc;
    private String text;
    private Boolean is_Answer;
    private int index_answer;
    private int next_id;
    private boolean need_answer;
    private boolean was_send;


    public ChatModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypechat() {
        return typechat;
    }

    public void setTypechat(int typechat) {
        this.typechat = typechat;
    }

    public String getImagesrc() {
        return imagesrc;
    }

    public void setImagesrc(String imagesrc) {
        this.imagesrc = imagesrc;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public Boolean getIs_Answer() {
        return is_Answer;
    }

    public void setIs_Answer(Boolean is_Answer) {
        this.is_Answer = is_Answer;
    }

    public int getIndex_answer() {
        return index_answer;
    }

    public void setIndex_answer(int index_answer) {
        this.index_answer = index_answer;
    }

    public List<MultipleChoiceModel> getMultiplechoice() {
        return multiplechoice;
    }

    public void setMultiplechoice(List<MultipleChoiceModel> multiplechoice) {
        this.multiplechoice = multiplechoice;
    }

    public int getNext_id() {
        return next_id;
    }

    public void setNext_id(int next_id) {
        this.next_id = next_id;
    }

    public boolean isNeed_answer() {
        return need_answer;
    }

    public void setNeed_answer(boolean need_answer) {
        this.need_answer = need_answer;
    }

    public boolean isWas_send() {
        return was_send;
    }

    public void setWas_send(boolean was_send) {
        this.was_send = was_send;
    }
}
