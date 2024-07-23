package com.eduardoes.news;

public final class VoiceMessage implements SealedExample {
    @Override
    public void sendMessage(String msg) {
        System.out.println("Ny voice Message");
    }
}
