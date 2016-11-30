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
	
	public void Deposit(BigDecimal amount)
	{
		// checks if the deposit amount is positive
		if (amount.compareTo(BigDecimal.ZERO) > 0)
		{
			_balance = _balance.add(amount);
		}
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

	public void withdraw(BigDecimal amount) {
		if(amount.compareTo(BigDecimal.ZERO) > 0)
		{
			_balance = _balance.subtract(amount);
		}

	}
	
}
