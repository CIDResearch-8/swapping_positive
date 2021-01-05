package com.swappingpositive.algorithm;
import java.util.Map;

class StringChanger {
    private String post;

    //StringChangerのメンバ変数に格納
    void readInString(String str){
        post = str;
        ChangeString();
    }
    //readInString()内で実行。文字列を検索し、listの中からヒットするものがあったら変換する。
    private void ChangeString(){
        ReadCsv rc = new ReadCsv();
        Map<String, String> data = rc.readCsv();	//ReadCsvクラスからリストを呼び出す

        //文字列検索
        for (Map.Entry<String, String> entry : data.entrySet()) {
            if(post.contains(entry.getKey())) {
                post = post.replace(entry.getKey(), entry.getValue());
            }
        }
    }
    //読み込んだ後、変換した文字列を出力
    String printString(){
        return post;
    }
}
