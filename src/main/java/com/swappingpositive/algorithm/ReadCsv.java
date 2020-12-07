package com.swappingpositive.algorithm;
import java.util.Map;
import java.util.HashMap;
import java .io. BufferedReader;
import java.io.File;
import java.io.FileReader;

class ReadCsv {
    Map<String, String> readCsv(){
        BufferedReader br = null;
        String file_name = "Book_1.csv"; // 入力ファイル
        // データを格納するリスト
        Map<String, String> data = new HashMap<>();

        try {
            File file = new File(file_name);
            br = new BufferedReader(new FileReader(file));
            int index = 0;

            // readLineで一行ずつ読み込む
            String line;
            while ((line = br.readLine()) != null) {
                // lineをカンマで分割し、配列リストdataに追加
                String[] splitedStrings = line.split(",");
                data.put(splitedStrings[0], splitedStrings[1]);
            }

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                br.close();
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return data;
    }
}
