package com.swappingpositive.abstracts;

import java.util.List;

public interface AbstractDao<T> {
    /**
     * elementをテーブルに挿入するために実装します。
     * @param element 挿入したい仮型クラスのインスタンス
     * @return 挿入できたかどうか
     */
    boolean insert(T element);


    /**
     * idを用いてテーブルから一致した行を削除します。
     * @param id テーブルのカラム名で削除したいid
     * @return 削除できたかどうか
     */
    boolean delete(Object id);

    /**
     * idを用いてテーブルを検索します。通常、idはプライマリキーのため、
     * 必ず一つかnullを返す必要があります。
     * @param id　テーブルのカラム内で検索したいid
     * @return 一致した仮型クラスのインスタンス　なければnull
     */
    T getById(Object id);

    /**
     * テーブルに入っているすべての行を返します。
     * @return 全ての行が入った仮型クラスのリスト
     */
    List<T> getAll();
}