package com.eduardoes.news;

public final class TextMsg implements SealedExample {
    @Override
    public void sendMessage(String msg) {
        System.out.println(msg);
    }
}
