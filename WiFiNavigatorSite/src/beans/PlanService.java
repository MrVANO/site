package beans;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import DAO.DatabaseHelper;
import entities.Plan;


@ManagedBean(name = "planService")
@ApplicationScoped
public class PlanService {
	
	private final static String[] colors;
	
	static {
        colors = new String[10];
        colors[0] = "Black";
        colors[1] = "White";
        colors[2] = "Green";
        colors[3] = "Red";
        colors[4] = "Blue";
        colors[5] = "Orange";
        colors[6] = "Silver";
        colors[7] = "Yellow";
        colors[8] = "Brown";
        colors[9] = "Maroon";
	}
	
	public List<Plan> getPlans() throws ClassNotFoundException, SQLException, IOException{
		DatabaseHelper dbh = new DatabaseHelper();
		return dbh.getPlans();
	}
}
