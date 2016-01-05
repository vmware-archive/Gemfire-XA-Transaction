package io.pivotal.jms.core;

import io.pivotal.business.TradeOptionCalculation;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

public class TradeMessageListener implements MessageListener {

	protected final Logger log = Logger.getLogger(getClass().getName());

	@Resource(name="tradeOptionCalculation")
	TradeOptionCalculation tradeCalc;


	private void invokeGemfireCalculation(String tradeData) {
		tradeCalc.calculate(tradeData);
	}

	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			try {
				log.info("Received Message");
				String tradeData = ((TextMessage) message).getText();
				invokeGemfireCalculation(tradeData);
			} catch (JMSException ex) {
				throw new RuntimeException(ex);
			}
		} else {
			throw new IllegalArgumentException(
					"Message must be of type TextMessage");
		}
	}
}
