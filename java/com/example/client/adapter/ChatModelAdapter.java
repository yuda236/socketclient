package com.example.client.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.client.MainActivity;
import com.example.client.R;
import com.example.client.model.ChatModel;
import com.example.client.model.MultipleChoiceModel;
import com.squareup.picasso.Picasso;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatModelAdapter extends RecyclerView.Adapter<ChatModelAdapter.ViewHolder> {
    Context c;
    List<ChatModel> data;
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    PrintWriter output;
    Socket socket;


    public ChatModelAdapter(Context c, List<ChatModel> data) {
        this.c = c;
        this.data = data;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(c).inflate(R.layout.layout_adapter_left, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, int i) {
        String text = data.get(i).getText();
        int type_chat = data.get(i).getTypechat();
        if (type_chat == 1) {
            vh.chatText.setVisibility(View.VISIBLE);
            vh.linearLayoutImage.setVisibility(View.GONE);
            vh.linearLayoutChoice.setVisibility(View.GONE);
            vh.chatText.setText(text);
        } else if (type_chat == 2) {
            final String filepath = data.get(i).getImagesrc();
            vh.chatText.setVisibility(View.GONE);
            vh.linearLayoutChoice.setVisibility(View.GONE);
            vh.linearLayoutImage.setVisibility(View.VISIBLE);
            // Picasso.with(c).load(filepath).placeholder(R.drawable.noimage).error(R.drawable.noimage).into(vh.chatImage);
            Glide.with(c).load(filepath).into(vh.chatImage);

            vh.chatImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(c);

                    View view = ((Activity)c).getLayoutInflater().inflate(R.layout.dialog_image_layout,null,false);
                    ViewHolderImage vhImage = new ViewHolderImage(view);



 //                   Picasso.with(c).load(filepath).placeholder(R.drawable.noimage).error(R.drawable.noimage).into(vhImage.ivImageResize);
                   Glide.with(c).load(filepath).into(vhImage.ivImageResize);

                    dialog.setView(view);
                    dialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();

                }
            });
        } else {

            List<MultipleChoiceModel> multiplechoicemodels = data.get(i).getMultiplechoice();
            int choice_answer = data.get(i).getIndex_answer();
            String text1 = "";
            if(choice_answer == -1) {
                vh.chatText.setVisibility(View.GONE);
                vh.linearLayoutImage.setVisibility(View.GONE);
                vh.linearLayoutChoice.setVisibility(View.VISIBLE);
                MultipleChoiceClickAdapter adapter = new MultipleChoiceClickAdapter(multiplechoicemodels, c, data.get(i),vh,socket);
                vh.rvDataChoice.setAdapter(adapter);
                vh.rvDataChoice.setLayoutManager(new LinearLayoutManager(c));
            }
            else{
                vh.chatText.setVisibility(View.VISIBLE);
                vh.linearLayoutImage.setVisibility(View.GONE);
                vh.linearLayoutChoice.setVisibility(View.GONE);
                int index_answer =  data.get(i).getIndex_answer() -1;
                String index = multiplechoicemodels.get(index_answer).getIndex();
                String answer = multiplechoicemodels.get(index_answer).getAnswer();
                vh.chatText.setText(index + " " + answer);
            }
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Context getC() {
        return c;
    }

    public void setC(Context c) {
        this.c = c;
    }

    public List<ChatModel> getData() {
        return data;
    }

    public void setData(List<ChatModel> data) {
        this.data = data;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.chat_text)
        TextView chatText;
        @BindView(R.id.chat_image)
        ImageView chatImage;
        @BindView(R.id.linear_layout_image)
        LinearLayout linearLayoutImage;
        @BindView(R.id.rv_data_choice)
        RecyclerView rvDataChoice;
        @BindView(R.id.linear_layout_choice)
        LinearLayout linearLayoutChoice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class ViewHolderImage {

        ImageView ivImageResize;
        public ViewHolderImage(View view) {
           ivImageResize = view.findViewById(R.id.iv_image_resize);
        }
    }
}
