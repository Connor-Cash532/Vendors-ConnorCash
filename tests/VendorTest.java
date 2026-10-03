import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VendorTest {


    @Test
    void addition() {
        Vending v1 = new Vending(10, 10);
        v1.addMoney(2);
        assertEquals(2, v1.getBalance());
    }

}