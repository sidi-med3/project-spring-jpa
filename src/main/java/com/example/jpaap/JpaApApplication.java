package com.example.jpaap;

import com.example.jpaap.entities.Patient;
import com.example.jpaap.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class JpaApApplication implements CommandLineRunner {
    @Autowired
    PatientRepository patientRepository;
    public static void main(String[] args) {
        SpringApplication.run(JpaApApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i=0;i<100;i++) {
            patientRepository.save(
                    new Patient(null, "Mohamed", new Date(), Math.random()>0.5?true:false, (int)(Math.random()*1000)));
        }

        List<Patient> patients=patientRepository.findAll();
        for (Patient patient:patients){
            System.out.println("Patient numero : "+patient.getId());
            System.out.println(patient.getNom());
            System.out.println(patient.getDateNaissance());
            System.out.println(patient.isMalade());

        }
        Patient patient = patientRepository.findById(1L).orElse(null);
        if (patient!=null){
            System.out.println(patient.getNom());
            System.out.println(patient.getScore());
        }
        patient.setScore(874);
        patientRepository.save(patient);
        patientRepository.deleteById(1L);

        System.out.println("###########################---PAGINATION---#########################");
        Page<Patient> patientspages=patientRepository.findAll(PageRequest.of(0,5));
        for (Patient patientpage:patientspages){
            System.out.println("Patient numero : "+patientpage.getId());
            System.out.println(patientpage.getNom());
            System.out.println(patientpage.getDateNaissance());
            System.out.println(patientpage.isMalade());

        }
        System.out.println("******************--METHODES POUR PAGINATION--********************");
        System.out.println("Total pages : "+patientspages.getTotalPages());
        System.out.println("Total elements : "+patientspages.getTotalElements());
        System.out.println("Num Page : "+patientspages.getNumber());
        List<Patient> conten=patientspages.getContent();
        conten.forEach(p->{
            System.out.println("========================");
            System.out.println("Patient numero : "+p.getId());
            System.out.println(p.getNom());
            System.out.println(p.getDateNaissance());
            System.out.println(p.isMalade());
        });
        //Listes des Patients Malades
        List<Patient> patientsmalade=patientRepository.findByMalade(true);
        patientsmalade.forEach(p->{
            System.out.println("========================");
            System.out.println("Patient numero : "+p.getId());
            System.out.println(p.getNom());
            System.out.println(p.getDateNaissance());
            System.out.println(p.isMalade());
        });
    }
}
