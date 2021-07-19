package com.example.consumingwebservice;

import com.example.consumingwebservice.wsdl.BiBusHeader;

public class ResponseAndHeader {
    private Object response;
    private BiBusHeader header;

    public ResponseAndHeader(Object response, BiBusHeader header) {
        this.response = response;
        this.header = header;
    }

    public Object getResponse() {
        return response;
    }

    public BiBusHeader getHeader() {
        return header;
    }
}
