import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {

  @Test
  public void testGetLocalNumber(){

    MainClass number = new MainClass();
    int result = number.getLocalNumber();
    int expected = 14;

    Assert.assertTrue("Got wrong number. Expected: "+expected+", received: "+ result, result==expected );
  }

  @Test
  public void testGetClassNumber(){
    MainClass number = new MainClass();
    int result = number.getClassNumber();
    int compare = 45;

    Assert.assertTrue("Got wrong number. Expected more than "+compare+", received: "+ result, result>compare );
  }
}
