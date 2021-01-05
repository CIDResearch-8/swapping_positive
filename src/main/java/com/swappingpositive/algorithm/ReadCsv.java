package com.swappingpositive.algorithm;
import java.util.Map;
import java.util.HashMap;
import java .io. BufferedReader;
import java.io.File;
import java.io.FileReader;

class ReadCsv {
    public Map<String, String> readCsv(){
        String file_name = "Book_1.csv"; // 入力ファイル
        // データを格納するリスト
        Map<String, String> data = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file_name))){
            // readLineで一行ずつ読み込む
            String line;
            while ((line = br.readLine()) != null) {
                // lineをカンマで分割し、配列リストdataに追加
                String[] splitedStrings = line.split(",");
                data.put(splitedStrings[0], splitedStrings[1]);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
