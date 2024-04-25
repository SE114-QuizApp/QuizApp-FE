package com.example.quizapp_fe.models;

import com.example.quizapp_fe.entities.Question;
import com.example.quizapp_fe.entities.Quiz;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JsonHelper {
    public static Quiz loadQuizFromJson(InputStream inputStream) {
        Quiz quiz = null;
        try {
            // Đọc dữ liệu từ InputStream thành chuỗi
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String json = stringBuilder.toString();

            // Sử dụng Gson để phân tích chuỗi JSON thành danh sách câu hỏi
            Type listType = new TypeToken<Quiz>(){}.getType();
            quiz = new Gson().fromJson(json, listType);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return quiz;
    }
}
