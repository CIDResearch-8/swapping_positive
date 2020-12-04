package com.swappingpositive.fizzy;

import java.util.List;

public interface Dao<T> {
    /**
     * elementをテーブルに挿入するために実装します。
     * @param element 挿入したい仮型クラスのインスタンス
     * @return 挿入できたかどうか
     */
    boolean insert(T element);

    /**
     * keyを用いてテーブルから一致した行を削除します。
     * @param key テーブルのカラム名で削除したいid
     * @return 削除できたかどうか
     */
    boolean delete(Object key);

    /**
     * 主キーの中でkeyが一致するかを検索し、一致した場合返します。
     * @param key 検索したいキー
     * @return 一致した行のインスタンス
     */
    T selectByPrimaryKey(Object key);

    /**
     * columnNameの中でcolumnと一致する行をすべて返します。
     * @param columnName カラム名
     * @param value 検索したい値
     * @return 一致したすべての行
     */
    List<T> selectByColumn(String columnName, Object value);

    /**
     * テーブルに入っているすべての行を返します。
     * @return 全ての行が入った仮型クラスのリスト
     */
    List<T> selectAll();
}