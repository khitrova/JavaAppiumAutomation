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

  @Test
  public void testGetClassString(){
    boolean compare;

    MainClass main = new MainClass();
    String result = main.getClassString();
    if (result.contains("hello")){
      compare = true;
    } else if (result.contains("Hello")){
      compare = true;
    } else {
      compare = false;
    }
    Assert.assertTrue("String "+result+" doesn't have required word.", compare == true);
  }
}
