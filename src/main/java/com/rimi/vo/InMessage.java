package com.rimi.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InMessage {
    private String roomId;
    private String nickName;
    private String msg;
}
