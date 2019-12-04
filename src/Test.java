import com.qinkuai.homework.dbpractice.hw3.model.Ticket;
import com.qinkuai.homework.dbpractice.hw3.model.User;
import com.qinkuai.homework.dbpractice.hw3.service.SellerManager;
import com.qinkuai.homework.dbpractice.hw3.service.TicketsSeller;

public class Test {

	public static void main(String[] args) {
		SellerManager manager = SellerManager.getInstance();
		User user = new User("QinKuai", "ab123456");
		manager.addSeller("G89", "2019-10-20", 50);
		int i = 0;
		TicketsSeller seller = manager.geTicketsSeller("G89");
		while(i++ < 50) {
			Ticket ticket = seller.sellTicket(user);
			System.out.println(seller.getRoutine().toString() + ticket.getSeatNumber());
		}
		
	}

}
