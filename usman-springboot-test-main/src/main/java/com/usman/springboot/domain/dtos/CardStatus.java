package com.usman.springboot.domain.dtos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author : FUsman
 * @description: This class is to handle the card_status table data.
 * @date : 29-04-2022
 * @since : 1.0.0
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CARD_STATUS")
public class CardStatus extends AuditAwareBaseEntity {

    @Column(name = "task")
    protected String task;

    @Column(name = "priority")
    protected String priority;

    @Column(name = "status")
    protected String status;

    @Column(name = "customer_reference_id")
    protected String customerReferenceId;

}
