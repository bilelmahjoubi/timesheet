package tn.esprit.spring.services;

import org.junit.Assert.*;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Role;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeServiceImplTest {
	
	@Autowired
	IEmployeService iEmployeService;
	
	@Test
	public void testMettreAjourEmailByEmployeId() {
		Employe employe = new Employe("bilel", "mahjoubi", "bmahjoub@gmailcom", true, Role.INGENIEUR);
		Employe addedEmp = iEmployeService.addOrUpdateEmploye(employe);
		Employe updatedEmploye = iEmployeService.mettreAjourEmailByEmployeId("bmahjoubi@gmailcom", addedEmp.getId());
		assertNotEquals(addedEmp.getEmail(), updatedEmploye.getEmail());
	}
	
	@Test
	public void testaddOrUpdateEmploye() {
		Employe employe = new Employe("Ahmed", "Bahri2", "raed.bahri2@esprit.tn", true, Role.INGENIEUR);
		Employe addedEmp = iEmployeService.addOrUpdateEmploye(employe);
		assertEquals(employe.getNom(), addedEmp.getNom());
	}
	
	@Test
	public void testGetAllEmployes() {
		assertEquals(20, iEmployeService.getAllEmployes().size());
	}
	
	@Test
	public void testdeleteEmployeById() {
		Employe employe = new Employe("Aymen", "Bahri", "raed.bahri@esprit.tn", true, Role.INGENIEUR);
		Employe addedEmp = iEmployeService.addOrUpdateEmploye(employe);
		assertTrue(iEmployeService.deleteEmployeById(addedEmp.getId()));
	}
	
	@Test
	public void testGetEmployePrenomById() {
		Employe employe = new Employe("Sami", "Bahri", "raed.bahri@esprit.tn", true, Role.INGENIEUR);
		Employe addedEmp = iEmployeService.addOrUpdateEmploye(employe);
		assertNotNull(iEmployeService.getEmployePrenomById(addedEmp.getId()));

	}

}
