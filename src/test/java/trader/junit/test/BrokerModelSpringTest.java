package trader.junit.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import trader.BrokerModelImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={BrokerModelImpl.class})
public class BrokerModelSpringTest {
	
}
