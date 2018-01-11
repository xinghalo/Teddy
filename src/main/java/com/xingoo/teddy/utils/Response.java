package com.xingoo.teddy.utils;

public class Response {
    private String state;
    private Object data;

    public Response(String state,Object info){
        this.state = state;
        this.data = info;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Response ERROR(String info) {
        return new Response("error",info);
    }

    public static Response SUCCESS(Object data){
        return new Response("success",data);
    }
}
