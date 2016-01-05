package io.pivotal.repositories;

import io.pivotal.domain.TradeData;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<TradeData, String> {

}
