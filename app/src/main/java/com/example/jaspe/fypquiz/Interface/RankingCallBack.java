package com.example.jaspe.fypquiz.Interface;

//an interface to callback user's score
public interface RankingCallBack<T> {
    void  callBack(T ranking);
}