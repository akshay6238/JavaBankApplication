import java.math.BigDecimal;

public class WithdrawalTray
{
    private final BigDecimal MAX_NUMBER_OF_BILLS = new BigDecimal(500);
    private BigDecimal availableBills;
    

    public void dispenseMoney() {
            // placeholder method -- this one will call the hardware controller
            
    }
    
    public void setAvailableBills(BigDecimal availableBills) {
        this.availableBills = availableBills;
    }

    public BigDecimal getAvailableBills() {
        return availableBills;
    }
    
    public void initializeAvailableBills() {
        setAvailableBills(MAX_NUMBER_OF_BILLS);
    }
}
