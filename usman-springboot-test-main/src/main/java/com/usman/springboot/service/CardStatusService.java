package com.usman.springboot.service;

import com.usman.springboot.domain.dtos.CardStatus;
import com.usman.springboot.repository.CardStatusRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 20-01-2023
 * @since : 1.0.0
 */
@Slf4j
@Service
public class CardStatusService {

    @Autowired
    private CardStatusRepository cardStatusRepository;

    public List<CardStatus> getCardStatus(String customerRefId) {
        log.info("get card for customerRefId:{}", customerRefId);
        List<CardStatus> cardStatusData = cardStatusRepository.findByCustomerReferenceId(customerRefId);
        log.info("get card successfully size:{}", cardStatusData.size());
        return  cardStatusData;
    }

    public int insertCardStatus(List<CardStatus> cardList) {
        int count = 0;
        for (CardStatus card: cardList) {
            count++;
            log.info("start card saving count:{}", count);
            cardStatusRepository.save(card);
            log.info("card saved successfully:{}", card);
        }
        return  count;
    }

}
