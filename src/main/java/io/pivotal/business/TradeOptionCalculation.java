package io.pivotal.business;

import io.pivotal.domain.TradeData;
import io.pivotal.gemfire.core.TradeCalculation;
import io.pivotal.repositories.TradeRepository;

import javax.annotation.Resource;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.Region;

@Component(value="tradeOptionCalculation")
public class TradeOptionCalculation {

	@Resource(name="gemfireCache")
	private Cache cache;

	@Autowired
	private TradeRepository tradeRepository;

	@Resource(name="tradeCalculation")
	private TradeCalculation tradeCalculation;

//	@Resource(name="userTransaction")
//	UserTransaction userTransaction;
//
//	@Autowired
//	private ApplicationContext appContext;

	@Autowired
	JtaTransactionManager transactionManager;

	protected final Logger log = Logger.getLogger(getClass().getName());

	Region<String, TradeData> tradeRegion;

	@Transactional(rollbackFor = RuntimeException.class)
	private void persistData(TradeData trade) throws RuntimeException {

		tradeRepository.save(trade);

	}


	public void calculate(String tradeDataJson) {

		TradeData trade = tradeCalculation.calculate(tradeDataJson);
		tradeRegion = cache.getRegion("trade");
		try {
			persistData(trade);

			transactionManager.getUserTransaction().begin();
//			userTransaction.begin();
			tradeRegion.put(trade.getId(), trade);
			transactionManager.getUserTransaction().commit();
//			userTransaction.commit();
		} catch (RuntimeException e) {
			log.info("Exception while saving data: Rolled Back !!");
		} catch (NotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicMixedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
