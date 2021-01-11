package com.raf.airtickuserservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raf.airtickuserservice.dto.CancelTicketDto;
import com.raf.airtickuserservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import javax.jms.Message;
import javax.jms.TextMessage;

@Component
@AllArgsConstructor
public class CancelTicketListener {

        private UserService userService;
        private ObjectMapper objectMapper;

        @JmsListener(destination = "${destination.cancelTicket}", concurrency = "5-10")
        public void handleCancelTicket(Message message){
            try {
                String jsonString = ((TextMessage) message).getText();
                CancelTicketDto cancelTicketDto = objectMapper.readValue(jsonString, CancelTicketDto.class);
                userService.cancelTicket(cancelTicketDto.getUserId(), cancelTicketDto.getMiles());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

}
