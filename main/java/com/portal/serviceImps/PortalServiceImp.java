package com.portal.serviceImps;

import com.portal.mails.MailSenderService;
import com.portal.models.*;
import com.portal.repositories.*;
import com.portal.security.AppSecurityConfig;
import com.portal.services.PortalService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PortalServiceImp implements PortalService {
    @Autowired
    private final ApplicantRepository applicantRepository;

    @Autowired
    private final JobsRepository jobsRepository;

    @Autowired
    private final InterviewRepository interviewRepository;

    @Autowired
    private final CompanyRepository companyRepository;

    @Autowired
    private final MailSenderService mailSenderService;
    @Autowired
    private final UsersRepository usersRepository;
    @Autowired
    private final RoleRepository roleRepository;


    @Override
    public void saveUser(Users user) {
        this.usersRepository.save(user);
    }

    @Override
    public Users getUsers(Long id) {
        return this.usersRepository.getById(id);
    }

    @Override
    public void saveRole(Role role) {
        if (this.roleRepository.findAll().stream().noneMatch(r -> r.getName().equals(role.getName()))) {
            this.roleRepository.save(role);
        }
    }

    @Override
    public Role getRoleById(Long id) {
        return this.roleRepository.getById(id);
    }

    @Override
    public void saveApplicant(Applicant applicant) {
        if (!(isMailAlreadyExist(applicant.getEmail())) && !(isUserNameAlreadyExist(applicant.getNames().getUsername()))) {
            applicant.setRole(Roles.USER.name());
            applicant.setCreatedAt(LocalDate.now());
            mailSenderService.sendEmail(applicant.getEmail(), createApplicantSuccess(applicant).toString());
            applicant.setPassword(new AppSecurityConfig().encoder().encode(applicant.getPassword()));
            this.applicantRepository.save(applicant);
            Users users = new Users(applicant.getNames().getUsername(), applicant.getPassword(), true);
            users.setRoles(new HashSet<>() {{
                add(new Role(applicant.getRole()));
            }});
            this.usersRepository.save(users);

        } else {
            new Applicant();
        }
    }

    @Override
    public Applicant getApplicantByName(String name) {
        return this.applicantRepository
                .findAll()
                .stream()
                .filter(u -> u.getNames().getUsername().equals(name))
                .findAny().orElseGet(Applicant::new);
    }

    private boolean isUserNameAlreadyExist(String username) {
        return Optional.ofNullable(this.usersRepository.getUsersByUserName(username)).isPresent();
    }

    @Override
    public Applicant getApplicant(Long id) {
        System.out.println("Retrivel database");
        return Optional.ofNullable(applicantRepository.getApplicant(id)).orElseGet(Applicant::new);
    }

    @Override
    public boolean isMailAlreadyExist(String email) {
        return Optional.ofNullable(this.applicantRepository.mailAlreadyExist(email)).isPresent();
    }

    @Override
    public List<Applicant> getAllApplicant() {
        return applicantRepository.findAll();
    }

    @Override
    public Jobs saveJobs(Jobs jobs) {
        return this.jobsRepository.save(jobs);
    }

    @Override
    public List<Jobs> availableJobs(String skill, String company, String location) {
        return this.jobsRepository.findAll().stream().filter(j -> j.getSkills().equalsIgnoreCase(skill) || j.getCName().equalsIgnoreCase(company) || j.getLocation().equalsIgnoreCase(location)).collect(Collectors.toList());
    }

    @Override
    public Jobs getJobs(Long id) {
        return this.jobsRepository.findById(id).orElseGet(Jobs::new);
    }

    @Override
    public List<Jobs> getAllJobs() {
        return this.jobsRepository.findAll();
    }

    @Override
    public List<Jobs> putInterIdGetJobs(List<Interviews> interviewsList) {
        List<Jobs> list = new ArrayList<>();
        for (Interviews interview : interviewsList) {
            Optional<Jobs> byId = this.jobsRepository.findById(interview.getJobId());
            byId.ifPresent(list::add);
        }
        return list;
    }

    @Override
    public Interviews getInterviewsById(Long id) {
        return this.interviewRepository.findById(id).orElseGet(Interviews::new);
    }

    @Override
    public void saveInterviews(Interviews interviews) {
        this.interviewRepository.save(interviews);
    }

    @Override
    public List<Interviews> getByInterviews(Long id) {
        return this.interviewRepository.findAll().stream().filter(i -> i.getUserId().equals(id)).collect(Collectors.toList());
    }

    @Override
    public List<Interviews> getInterviews() {
        return this.interviewRepository.findAll();
    }


    @Override
    public void deleteApplicant(Long id) {
        Optional<Interviews> any = this.interviewRepository.findAll().stream().filter(i -> i.getUserId().equals(id)).findAny();
        any.ifPresent(interviews -> this.interviewRepository.deleteById(interviews.getId()));
        this.applicantRepository.deleteById(id);
    }

    @Override
    public Applicant getApplicant(String email) {
        return this.applicantRepository.mailAlreadyExist(email);
    }

    @Override
    public void saveCompany(Company company) {
        boolean f = false;
        if (company.getId() != null) {
            Optional<Company> present = this.companyRepository.findById(company.getId());
            Company companyById = present.orElse(null);
            if (companyById != null) {
                if (company.getCWeb() != null) {
                    companyById.setCWeb(company.getCWeb());
                    f = true;
                }
                if (company.getCLocation() != null) {
                    companyById.setCLocation(company.getCLocation());
                    f = true;
                }
                if (f) {
                    this.companyRepository.save(companyById);
                    return;
                }
            }
        }
        boolean b = !isExistCompany(company);
        boolean b1 = !(isUserNameAlreadyExist(company.getCName()));

        if (b1 && b) {
            company.setCreatedAt(LocalDate.now());
            company.setRoles(Roles.COMPANY.name());
            company.setPassword(new AppSecurityConfig().encoder().encode(company.getPassword()));
            Company save = this.companyRepository.save(company);
            Users users = new Users(save.getCName(), save.getPassword(), true);
            users.setRoles(new HashSet<>() {{
                add(new Role(Roles.COMPANY.name()));
            }});
            this.usersRepository.save(users);
        } else {
            new Company();
        }
    }

    private boolean isExistCompany(Company company) {
        return this.companyRepository
                .findAll()
                .stream()
                .anyMatch(c ->
                        c.getCName().equalsIgnoreCase(company.getCName())
                                || c.getCMail().equalsIgnoreCase(company.getCMail())
                                || c.getCWeb().equalsIgnoreCase(company.getCWeb())
                                || c.getCName().equalsIgnoreCase(company.getCName())
                                && c.getPassword().equalsIgnoreCase(company.getPassword()));
    }

    @Override
    public Company getCompanyById(Long id) {
        return this.companyRepository.getById(id);
    }

    @Override
    public List<Company> getAllCompany() {
        return this.companyRepository.findAll();
    }

    @Override
    public boolean isCompanyMail(String mail) {
        return this.companyRepository
                .findAll()
                .stream()
                .anyMatch(c -> c.getCMail().equals(mail));
    }

    @Override
    public Company getCompanyByMail(String mail) {
        return this.companyRepository.findAll().stream().filter(c -> c.getCMail().equals(mail)).findAny().orElseGet(Company::new);
    }

    @Override
    public void deleteCompanyById(Long id) {
        this.companyRepository.deleteById(id);
    }

    private StringBuffer createApplicantSuccess(Applicant applicant) {
        return new StringBuffer()
                .append("\t\tRegistration Successfully")
                .append("\nHello., ").append(applicant.getNames().getFName())
                .append("\n\tUser Name: ").append(applicant.getNames().getUsername())
                .append("\n\tPassword : ").append(applicant.getPassword())
                .append("\n\tCreated Date: ").append(applicant.getCreatedAt())
                .append("\n\t\t\t\tThanks");
    }

    @Override
    public Set<String> listOfCompanyName() {
        return this.companyRepository.findAll().stream().map(Company::getCName).collect(Collectors.toSet());
    }
}
