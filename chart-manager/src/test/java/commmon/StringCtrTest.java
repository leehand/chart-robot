package commmon;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

/**
 * @author zl
 */
public class StringCtrTest {
    @Test
    public void isOnlyNumber(){
        String str = "123456.789";
        boolean b = NumberUtils.isDigits(str);
        System.out.println();
    }
}
