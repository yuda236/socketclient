package com.example.client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.client.adapter.ChatModelAdapter;
import com.example.client.model.ChatModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.ip_address)
    EditText ipAddress;
    @BindView(R.id.port)
    EditText port;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.btn_connected)
    Button btnConnected;

    List<ChatModel> data;


    Thread Thread1 = null;
    String SERVER_IP;
    int SERVER_PORT;
    @BindView(R.id.linear_layout_socket)
    LinearLayout linearLayoutSocket;
    @BindView(R.id.rv_data)
    RecyclerView rvData;
    private ChatModelAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        data = new ArrayList<ChatModel>();

        ipAddress.setText("192.168.0.4");
        port.setText("8080");
    }

    @OnClick({R.id.btn_connected})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_connected:
                SERVER_IP = ipAddress.getText().toString().trim();
                SERVER_PORT = Integer.parseInt(port.getText().toString().trim());
                Thread1 = new Thread(new Thread1());
                Thread1.start();
                break;
        }
    }

    private PrintWriter output;
    private BufferedReader input;
    private Socket socket;
    private class Thread1 implements Runnable {

        @Override
        public void run() {

            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                output = new PrintWriter(socket.getOutputStream());
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        status.setText("Connected\n");
                        linearLayoutSocket.setVisibility(View.GONE);
                        new Thread(new Thread2()).start();
                        new Thread(new Thread3("start")).start();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private class Thread2 implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    final String message = input.readLine();
                    if (message != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();
                                ChatModel c = gson.fromJson(message, ChatModel.class);
                                boolean needanswer = c.isNeed_answer();
                                String text = c.getText().toString();
                                int typechat = c.getTypechat();
                                data.add(c);
                                RefreshRV();

                                if (typechat == 1 && needanswer == false) {
                                    c.setWas_send(true);
                                    String sendingchat = gson.toJson(c);
                                    new Thread(new Thread3(sendingchat)).start();
                                }
                                else if (typechat == 2 && needanswer == false) {
                                    c.setWas_send(true);
                                    String sendingchat = gson.toJson(c);
                                    new Thread(new Thread3(sendingchat)).start();
                                }
                            }
                        });
                    } else {
                        Thread1 = new Thread(new Thread1());
                        Thread1.start();
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void RefreshRV() {
        adapter = new ChatModelAdapter(this, data);
        adapter.setSocket(socket);
        rvData.setHasFixedSize(true);
        rvData.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setStackFromEnd(true);
        rvData.setLayoutManager(manager);
    }

    private class Thread3 implements Runnable {

        private String message;

        public Thread3(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            output.println(message);
            output.flush();
        }
    }
}
