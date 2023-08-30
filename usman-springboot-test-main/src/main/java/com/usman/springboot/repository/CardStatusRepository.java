package com.usman.springboot.repository;

import com.usman.springboot.domain.dtos.CardStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : FUsman
 * @description: This class is to handle the card_status repository data.
 * @date : 29-04-2022
 * @since : 1.0.0
 */
@Repository
public interface CardStatusRepository extends JpaRepository<CardStatus, String> {

    List<CardStatus> findByCustomerReferenceId(String customerRefId);

    List<CardStatus> findByCustomerReferenceIdAndTask(String customerRefId, String task);

}

