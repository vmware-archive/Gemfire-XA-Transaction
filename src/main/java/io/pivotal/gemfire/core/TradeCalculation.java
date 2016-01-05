package io.pivotal.gemfire.core;

import io.pivotal.domain.TradeData;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.gemstone.gemfire.pdx.JSONFormatter;
import com.gemstone.gemfire.pdx.PdxInstance;

@Component(value="tradeCalculation")
public class TradeCalculation {

	public static final String FUNCTION_ID = "TradeCalculation";
	protected final Logger log = Logger.getLogger(getClass().getName());

//	@Resource(name="gemfireCache")
//	private Cache cache;

	public TradeData calculate(String tradeData) {

//		Region<String, TradeData> tradeRegion = cache.getRegion("trade");

		TradeData trade = new TradeData();
		PdxInstance tradeDataPdx = JSONFormatter.fromJSON(tradeData);

		trade.setId((String) tradeDataPdx.getField("id"));
    	trade.setSymbol((String) tradeDataPdx.getField("sym"));

    	String tempValue = (String) tradeDataPdx.getField("qnt");
    	trade.setQuantity(Integer.parseInt(tempValue));

    	tempValue = (String) tradeDataPdx.getField("amt");
    	trade.setAmount(Double.parseDouble(tempValue));

//    	tradeRegion.put((String)tradeDataPdx.getField("id"), trade);

		return trade;
	}


}
