package com.rimi.componet;

import java.util.UUID;

public class UUIDComponet {
    //生城uuid
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static void main(String[] args) {
        System.out.println(uuid());
    }
}
