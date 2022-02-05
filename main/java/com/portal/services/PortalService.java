package com.portal.services;

import com.portal.models.*;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Set;

@Configuration
public interface PortalService {

    public void saveUser(Users user);
    public Users getUsers(Long id);

    public void saveRole(Role role);
    public Role getRoleById(Long id);

    public void saveApplicant(Applicant applicant);
    public Applicant getApplicant(Long id);
    public Applicant getApplicant(String email);
    public boolean isMailAlreadyExist(String email);
    public List<Applicant> getAllApplicant();
    public Applicant getApplicantByName(String name);

    public Jobs saveJobs(Jobs jobs);
    public Jobs getJobs(Long id);
    public List<Jobs> getAllJobs();
    public List<Jobs> availableJobs(String skill,String company,String location);
    public List<Jobs> putInterIdGetJobs(List<Interviews> interviewsList);

    public void saveInterviews(Interviews interviews);
    public List<Interviews> getInterviews();
    public void deleteApplicant(Long id);
    public List<Interviews> getByInterviews(Long id);
    public Interviews getInterviewsById(Long id);


    public void saveCompany(Company company);
    public Company getCompanyById(Long id);
    public boolean isCompanyMail(String mail);
    public Company getCompanyByMail(String mail);
    public List<Company> getAllCompany();
    public Set<String> listOfCompanyName();
    public void deleteCompanyById(Long id);
}
