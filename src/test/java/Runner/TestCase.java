package Runner;

import org.testng.annotations.Test;

import Generic.TestBase;
import Login.Login_page;

public class TestCase extends TestBase{

	
	
	@Test
   public void ulogin() {
	Login_page lp=new Login_page(gm);
	lp.userLogin();
	lp.hfValidLogin();

   }
	
	
	
	 
}
