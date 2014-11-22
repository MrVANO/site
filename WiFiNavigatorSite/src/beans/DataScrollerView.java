package beans;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import entities.Plan;


@ManagedBean
@ViewScoped
public class DataScrollerView implements Serializable {
     
    private List<Plan> plans;
    private Plan selectedPlan;
         
    @ManagedProperty("#{planService}")
    private PlanService service;
     
    @PostConstruct
    public void init() {
        try {
			plans = service.getPlans();
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public Plan getSelectedPlan() {
		return selectedPlan;
	}

	public void setSelectedPlan(Plan selectedPlan) {
		this.selectedPlan = selectedPlan;
	}

	public List<Plan> getPlans() {
		return plans;
	}

	public void setService(PlanService service) {
		this.service = service;
	}
 
}
