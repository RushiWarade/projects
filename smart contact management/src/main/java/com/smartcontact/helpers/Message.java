package com.smartcontact.helpers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    private String content;

    @Builder.Default
    private MessageType type = MessageType.blue;

    private String icon ="fa-circle-info";

}
