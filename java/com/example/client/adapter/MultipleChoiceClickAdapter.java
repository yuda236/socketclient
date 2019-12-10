package com.example.client.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.client.MainActivity;
import com.example.client.R;
import com.example.client.model.ChatModel;
import com.example.client.model.MultipleChoiceModel;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MultipleChoiceClickAdapter extends RecyclerView.Adapter<MultipleChoiceClickAdapter.ViewHolder> {

    private final ChatModelAdapter.ViewHolder viewHolderParent;
    List<MultipleChoiceModel> data;
    Context c;
    ChatModel chatmodel;
    Socket socket;
    private PrintWriter output;

    public MultipleChoiceClickAdapter(List<MultipleChoiceModel> data, Context c, ChatModel chatModel, ChatModelAdapter.ViewHolder vh, Socket socket) {
        this.data = data;
        this.c = c;
        this.chatmodel = chatModel;
        viewHolderParent = vh;
        this.socket = socket;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(c).inflate(R.layout.layout_multiple_choice_adapter_detail, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder vh, final int i) {
        final String choice = data.get(i).getIndex();
        final String answer = data.get(i).getAnswer();
        vh.tvChoice.setText(choice);
        vh.tvAnswer.setText(answer);

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index_answer = i +1;
                chatmodel.setIndex_answer(index_answer);
                chatmodel.setWas_send(true);
                Gson gson = new Gson();
                String sendResult = gson.toJson(chatmodel);

                viewHolderParent.linearLayoutChoice.setVisibility(View.GONE);
                viewHolderParent.chatText.setText(answer);
                viewHolderParent.chatText.setVisibility(View.VISIBLE);

                new Thread(new ThreadSend(sendResult)).start();

            }
        });
    }

    public class ThreadSend implements Runnable{

        private String message;

        public ThreadSend(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            try {
                output = new PrintWriter(socket.getOutputStream());
                output.println(message);
                output.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_choice)
        TextView tvChoice;
        @BindView(R.id.tv_answer)
        TextView tvAnswer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
