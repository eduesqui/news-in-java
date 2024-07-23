package com.eduardoes.news;

public sealed  interface SealedExample permits TextMsg, VoiceMessage {
    void sendMessage(String msg);
}
