package bankingApp2.dao;

import java.util.ArrayList;
import java.util.List;
import bankingApp2.models.Customer;

//joint table; customers and applications
public class ApplicantsDAO implements DAO {
	List<Customer> apcs;
	
	public ApplicantsDAO() {
		apcs = new ArrayList<>();
	}
	
	public void add(Customer c) { apcs.add(c); }
	
}
