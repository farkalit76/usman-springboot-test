package com.usman.springboot.web.controller;

import com.usman.springboot.annotation.LogExecutionTime;
import com.usman.springboot.domain.dtos.CardStatus;
import com.usman.springboot.domain.request.CardStatusRequest;
import com.usman.springboot.service.CardStatusService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 20-01-2023
 * @since : 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
@LogExecutionTime
public class CardStatusController {

    @Autowired
    private CardStatusService cardStatusService;

    @GetMapping(value = "/card/fetch")
    public List<CardStatus> getCardData(@RequestBody CardStatusRequest request) {
        log.info("get card started....");
        return cardStatusService.getCardStatus(request.getCustomerRefId());
    }

    @PostMapping(value = "/card/insert")
    public Integer getMessage(@RequestBody  CardStatusRequest request) {
        log.info("insert card started....");
        List<CardStatus> cardStatuses = getCardStatusList(request);
        return cardStatusService.insertCardStatus(cardStatuses);
    }

    private List<CardStatus> getCardStatusList(CardStatusRequest request) {
        List<CardStatus> cardList = new ArrayList<>();
        CardStatus card1 = new CardStatus("Fund your account","P1","PENDING",request.getCustomerRefId());
        CardStatus card2 = new CardStatus("Video KYC","P2","PENDING",request.getCustomerRefId());
        CardStatus card3 = new CardStatus("Activate VPA","P3","PENDING",request.getCustomerRefId());
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);
        return cardList;
    }


}
