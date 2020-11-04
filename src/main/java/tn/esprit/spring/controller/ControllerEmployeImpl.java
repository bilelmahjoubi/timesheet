package tn.esprit.spring.controller;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.services.IEmployeService;


@Scope(value = "session")
@Controller(value = "employeController")
@ELBeanName(value = "employeController")
@Join(path = "/", to = "/login.jsf")
public class ControllerEmployeImpl  {

	@Autowired
	IEmployeService employeService;

	private String login; 
	private String password1; 
	private Boolean loggedIn1;

	private Employe authenticatedUser = null; 
	private String prenom1; 
	private String nom1; 
	private String email1;
	private boolean actif1;
	private Role role1;  
	public Role[] getRoles() { return Role.values(); }

	private List<Employe> employes; 


	private Integer employeIdToBeUpdated; 
	
	String loginUrl= "/login.xhtml?faces-redirect=true";


	public String doLogin() {

		String navigateTo = "null";
		authenticatedUser=employeService.authenticate(login, password1);
		if (authenticatedUser != null && authenticatedUser.getRole() == Role.ADMINISTRATEUR) {
			navigateTo = "/pages/admin/welcome.xhtml?faces-redirect=true";
			loggedIn1 = true;
		}		

		else
		{
			
			FacesMessage facesMessage =
					new FacesMessage("Login Failed: Please check your username/password and try again.");
			FacesContext.getCurrentInstance().addMessage("form:btn",facesMessage);
		}
		return navigateTo;	
	}

	public String doLogout()
	{
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	
	return "/login.xhtml?faces-redirect=true";
	}


	public String addEmploye() {

		if (authenticatedUser==null || !loggedIn1) return loginUrl;

		employeService.addOrUpdateEmploye(new Employe(nom1, prenom1, email1, password1, actif1, role1)); 
		return "null"; 
	}  

	public String removeEmploye(int employeId) {
		String navigateTo = "null";
		if (authenticatedUser==null || !loggedIn1) return loginUrl;

		employeService.deleteEmployeById(employeId);
		return navigateTo; 
	} 

	public String displayEmploye(Employe empl) 
	{
		String navigateTo = "null";
		if (authenticatedUser==null || !loggedIn1) return loginUrl;


		this.setPrenom1(empl.getPrenom());
		this.setNom1(empl.getNom());
		this.setActif1(empl.isActif()); 
		this.setEmail1(empl.getEmail());
		this.setRole1(empl.getRole());
		this.setPassword1(empl.getPassword());
		this.setEmployeIdToBeUpdated(empl.getId());

		return navigateTo; 

	} 

	public String updateEmploye() 
	{ 
		String navigateTo = "null";
		
		if (authenticatedUser==null || !loggedIn1) return loginUrl;

		employeService.addOrUpdateEmploye(new Employe(employeIdToBeUpdated, nom1, prenom1, email1, password1, actif1, role1)); 

		return navigateTo; 

	}

	public IEmployeService getEmployeService() {
		return employeService;
	}

	public void setEmployeService(IEmployeService employeService) {
		this.employeService = employeService;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public Boolean getLoggedIn1() {
		return loggedIn1;
	}

	public void setLoggedIn1(Boolean loggedIn1) {
		this.loggedIn1 = loggedIn1;
	}

	public Employe getAuthenticatedUser() {
		return authenticatedUser;
	}

	public void setAuthenticatedUser(Employe authenticatedUser) {
		this.authenticatedUser = authenticatedUser;
	}

	public String getPrenom1() {
		return prenom1;
	}

	public void setPrenom1(String prenom1) {
		this.prenom1 = prenom1;
	}

	public String getNom1() {
		return nom1;
	}

	public void setNom1(String nom1) {
		this.nom1 = nom1;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public boolean isActif1() {
		return actif1;
	}

	public void setActif1(boolean actif1) {
		this.actif1 = actif1;
	}

	public Role getRole1() {
		return role1;
	}

	public void setRole1(Role role1) {
		this.role1 = role1;
	}

	public List<Employe> getEmployes() {
		return employes;
	}

	public void setEmployes(List<Employe> employes) {
		this.employes = employes;
	}

	public Integer getEmployeIdToBeUpdated() {
		return employeIdToBeUpdated;
	}

	public void setEmployeIdToBeUpdated(Integer employeIdToBeUpdated) {
		this.employeIdToBeUpdated = employeIdToBeUpdated;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	} 


	

}