import java.math.BigDecimal;

public class BankAccount {

	
	private String _accountNumber;
	private String _accountName;
	private String _PIN;
	private BigDecimal _balance;
	
	public BankAccount(String accountnumber, String accountname, String PIN, BigDecimal balance)
	{
		_accountNumber = accountnumber;
		_accountName = accountname;
		_PIN = PIN;
		_balance = balance;
	}
	
	public BigDecimal getBalance() 
	{
		return _balance;
	}

	public String getAccountNumber()
	{
		return _accountNumber;
	}
	
	public String getPIN()
	{
		return _PIN;
	}
	
	public String getAccountName()
	{
		return _accountName;
	}
	
}
