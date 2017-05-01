package com.chekhovych.service;

import java.io.UnsupportedEncodingException;

public interface GmailService {

    void sendEmail(String message) throws UnsupportedEncodingException;
}
