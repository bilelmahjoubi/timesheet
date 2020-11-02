package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class EmployeServiceImpl implements IEmployeService {

	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;
	
	private static final Logger l = LogManager.getLogger(EmployeServiceImpl.class);

	@Override
	public Employe authenticate(String login, String password) {
		l.info("In  authenticate : ");
		Employe emp = employeRepository.getEmployeByEmailAndPassword(login, password); 
		l.info("Out  authenticate : ");
		return emp ;
	}

	@Override
	public Employe addOrUpdateEmploye(Employe employe) {
		employeRepository.save(employe);
		return employe;
	}

	public Employe mettreAjourEmailByEmployeId(String email, int employeId) {
		Employe employe = employeRepository.findById(employeId).orElse(null);
		if(employe != null) {
			employe.setEmail(email);
			employeRepository.save(employe);
		}
		return employe;

	}

	@Transactional	
	public void affecterEmployeADepartement(int employeId, int depId) {
		l.info("In  affecterEmployeADepartement : ");
		Departement depManagedEntity = deptRepoistory.findById(depId).get();
		
		Employe employeManagedEntity = employeRepository.findById(employeId).get();

		if(depManagedEntity.getEmployes() == null){
			l.info("No employe found in this departement ");

			List<Employe> employes = new ArrayList<>();
			employes.add(employeManagedEntity);
			depManagedEntity.setEmployes(employes);
		}else{

			depManagedEntity.getEmployes().add(employeManagedEntity);
			l.debug("Employe: " + employeId + "affecter a" + depId);
		}

		// à ajouter? 
		deptRepoistory.save(depManagedEntity); 
		l.info("Out  affecterEmployeADepartement : ");

	}
	@Transactional
	public void desaffecterEmployeDuDepartement(int employeId, int depId)
	{
		l.info("In  desaffecterEmployeDuDepartement : ");
		Departement dep = deptRepoistory.findById(depId).get();

		int employeNb = dep.getEmployes().size();
		l.debug("emplyeNB : "+employeNb);
		for(int index = 0; index < employeNb; index++){
			
			if(dep.getEmployes().get(index).getId() == employeId){
				dep.getEmployes().remove(index);
				l.debug("index +++ : " + index);
				l.info("employe removed ");
				
				break;//a revoir
			}
		}
		l.info("Out  desaffecterEmployeDuDepartement : ");
	} 
	
	// Tablesapce (espace disque) 

	public int ajouterContrat(Contrat contrat) {
		l.info("In  ajouterContrat : ");
		contratRepoistory.save(contrat);
		
		int x =		contrat.getReference();
		l.debug("contract reference" + x);
		l.info("In  ajouterContrat : ");		
		return	x ;	
	}

	public void affecterContratAEmploye(int contratId, int employeId) {
		l.info("In  affecterContratAEmploye : ");
		Contrat contratManagedEntity = contratRepoistory.findById(contratId).get();
		Employe employeManagedEntity = employeRepository.findById(employeId).get();

		contratManagedEntity.setEmploye(employeManagedEntity);
		contratRepoistory.save(contratManagedEntity);
		l.info("Out  affecterContratAEmploye : ");
	}

	public String getEmployePrenomById(int employeId) {
		l.info("In  getEmployePrenomById : ");
		Employe employeManagedEntity = employeRepository.findById(employeId).get();
		String emp = employeManagedEntity.getPrenom();
		l.info("Out  getEmployePrenomById : ");
		 return emp;
		 
	}
	 
	public Boolean deleteEmployeById(int employeId)
	{
		try {
			Employe employe = employeRepository.findById(employeId).orElse(null);

			if(employe!=null) {
				for(Departement dep : employe.getDepartements()){
					dep.getEmployes().remove(employe);
				}

				employeRepository.delete(employe);
				return true;
			}
			return false;
			
		} catch (Exception e) {
			return false;
		}
		
	}

	public void deleteContratById(int contratId) {
		l.info("In  deleteContratById : ");
		Contrat contratManagedEntity = contratRepoistory.findById(contratId).get();
		contratRepoistory.delete(contratManagedEntity);
		l.info("Out  deleteContratById : ");
	}

	public int getNombreEmployeJPQL() {
		l.info("In  getNombreEmployeJPQL : ");
		int x = employeRepository.countemp();
		l.debug("NombreEmploye :" +x);
		l.info("Out  getNombreEmployeJPQL : ");
		 return x ;
	}

	public List<String> getAllEmployeNamesJPQL() {
		l.info("In  getAllEmployeNamesJPQL : ");
		List<String> x =	 employeRepository.employeNames();
		l.info("Out  getAllEmployeNamesJPQL : ");
		 return x;

	}

	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		l.info("In  getAllEmployeByEntreprise : ");
		List<Employe> x = employeRepository.getAllEmployeByEntreprisec(entreprise);
		l.info("Out  getAllEmployeByEntreprise : ");
		return x;
	}

	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		l.info("In  mettreAjourEmailByEmployeIdJPQL : ");
		employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);
		l.info("Out  mettreAjourEmailByEmployeIdJPQL : ");

	}
	public void deleteAllContratJPQL() {
		l.info("In  deleteAllContratJPQL : ");
		employeRepository.deleteAllContratJPQL();
		l.info("Out  deleteAllContratJPQL : ");
	}

	public float getSalaireByEmployeIdJPQL(int employeId) {
		l.info("In  getSalaireByEmployeIdJPQL : ");
		float x = employeRepository.getSalaireByEmployeIdJPQL(employeId);
		l.info("Out  getSalaireByEmployeIdJPQL : ");
		 return x;
	}

	public Double getSalaireMoyenByDepartementId(int departementId) {
		l.info("In  getSalaireMoyenByDepartementId : ");
		Double x= employeRepository.getSalaireMoyenByDepartementId(departementId);
		l.info("Out  getSalaireMoyenByDepartementId : ");
		return x ;
	}

	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {
		l.info("In  getTimesheetsByMissionAndDate : ");
		List<Timesheet> x= timesheetRepository.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
		l.info("In  getTimesheetsByMissionAndDate : ");
		return x ;
	}

	public List<Employe> getAllEmployes() {
		l.info("In  getAllEmployes : ");
		List<Employe> x = (List<Employe>) employeRepository.findAll();
		l.info("Out  getAllEmployes : ");
		return x ;
	}
}

