package com.portal.controllers;

import com.portal.models.*;
import com.portal.services.PortalService;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class PortalController implements ErrorController {
    private PortalService portalService;

    @RequestMapping(value = "/login")
    public ModelAndView login() {
        return new ModelAndView("html/login1");
    }

    @RequestMapping(value = "/name", method = RequestMethod.GET)
    @ResponseBody
    public String getName(Principal principal) {
        return principal.getName();
    }


    @RequestMapping(value = "/")
    public String goHome() {
        return "redirect:/login";
    }


    @RequestMapping(value = "/home")
    public ModelAndView home() {
        return new ModelAndView("html/index").addObject("jobs", new Jobs());
    }

    @RequestMapping(value = "/logfail")
    public String loginFail() {
        return "errors/loginfail";
    }


    @RequestMapping(value = "/company")
    public String company() {
        return "html/company";
    }

    @RequestMapping(value = "/admin")
    public String admin() {
        return "html/admin";
    }


    @RequestMapping(value = "/applicantReg")
    public ModelAndView applicantReg() {

        return new ModelAndView("html/applicantRegister").addObject("obj", new Applicant()).addObject("names", new Names());
    }

    @RequestMapping(value = "/saveApplicant")
    public String saveApplicant(@ModelAttribute("obj") Applicant applicant, @ModelAttribute("names") Names names) {
        if (applicant != null && names != null) {
            if (!(this.portalService.isMailAlreadyExist(applicant.getEmail()))) {
                applicant.setNames(names);
                this.portalService.saveApplicant(applicant);
                return "success/successfully";
            } else {
                return "errors/userExist";
            }
        }
        return "errors/fail";
    }

    @RequestMapping(value = "/logoutSuccess")
    public String logOut() {
        return "success/logoutsuccess";
    }

    @RequestMapping(value = "/createCom")
    public ModelAndView createCom() {
        return new ModelAndView("html/createCom").addObject("obj", new Company());
    }

    @RequestMapping(value = "/saveCom")
    public String saveCom(@ModelAttribute("com") Company company) {
        if (company != null) {
            this.portalService.saveCompany(company);
            return "success/createCompanySuccess";
        }
        return "errors/createCompanyFail";
    }

    @RequestMapping(value = "/availableJobs")
    public ModelAndView availableJobs(@ModelAttribute("jobs") Jobs jobs) {
        return new ModelAndView("html/availableJos").addObject("jobList", this.portalService.availableJobs(jobs.getSkills(), jobs.getCName(), jobs.getLocation()));
    }

    @RequestMapping(value = "/home/alljobs")
    public ModelAndView alljobs() {
        return new ModelAndView("html/alljobs").addObject("jobList", this.portalService.getAllJobs());
    }

    @RequestMapping(value = "/company/jobreg")
    public ModelAndView jobreg() {
        return new ModelAndView("html/jobRegister").addObject("jobs", new Jobs());
    }

    @RequestMapping(value = "/company/saveJob")
    public String saveJob(@ModelAttribute("jobs") Jobs jobs) {
        if (jobs != null) {
            if (this.portalService.saveJobs(jobs) != null) {
                return "success/saveJobSuccess";
            }
        } else {
            return "errors/jobSaveFailed";
        }
        return "errors/jobSaveFailed";
    }

    @RequestMapping(value = "/home/apply/{id}")
    public String jobApply(@PathVariable("id") Long id, Principal principal) {
        Jobs jobs = this.portalService.getJobs(id);
        if (jobs != null) {
            Applicant applicantByName = this.portalService.getApplicantByName(principal.getName());
            if (applicantByName != null) {
                this.portalService.saveInterviews(new Interviews(applicantByName.getId(), id));
                return "success/jobAppySuccess";
            }
        }
        return "errors/applyFail";
    }

    @RequestMapping(value = "/home/interviewList")
    public ModelAndView listOfInterView(Principal principal) {
        System.out.println(principal.getName());
        Long id = this.portalService.getApplicantByName(principal.getName()).getId();
        System.out.println("name to id= " + id);
        List<Interviews> byInterviews = this.portalService.getByInterviews(id);
        System.out.println("id to interview " + byInterviews);
        List<Jobs> jobs = this.portalService
                .putInterIdGetJobs(byInterviews);
        System.out.println("inter di to jobs " + jobs);


        return new ModelAndView("html/interviewList")
                .addObject("lists", jobs);

    }

    @RequestMapping(value = "/home/companies")
    public ModelAndView companies() {
        return new ModelAndView("html/listOfCompany").addObject("comList", this.portalService.listOfCompanyName());
    }


    @RequestMapping(value = "/admin/viewAllUser")
    public ModelAndView viewAllUser() {
        return new ModelAndView("html/viewAllUser").addObject("alluser", this.portalService.getAllApplicant());
    }

    @RequestMapping(value = "/admin/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        return new ModelAndView("html/applicantEdit").addObject("update", this.portalService.getApplicant(id));
    }

    @RequestMapping(value = "/admin/saveUpdate")
    public String update(@ModelAttribute("update") Applicant applicant) {
        Applicant applicant1 = this.portalService.getApplicant(applicant.getId());
        if (applicant1 != null) {
            if (applicant.getEmail() != null) {
                applicant1.setEmail(applicant.getEmail());
            }
            if (applicant.getGender() != null) {
                applicant1.setGender(applicant.getGender());
            }
            if (applicant.getPhone() != null) {
                applicant1.setPhone(applicant.getPhone());
            }
        }
        this.portalService.saveApplicant(applicant1);
        return "redirect:/admin/viewAllUser";
    }

    @RequestMapping(value = "/admin/delete/{id}")
    public String deleteApplicant(@PathVariable("id") Long id) {
        this.portalService.deleteApplicant(id);
        return "redirect:/admin/viewAllUser";
    }

    @RequestMapping(value = "/admin/allCompany")
    public ModelAndView allCompany() {
        return new ModelAndView("html/viewAllCompany").addObject("allCompany", this.portalService.getAllCompany());
    }

    @RequestMapping(value = "/admin/com/update/{id}")
    public ModelAndView companyUpdate(@PathVariable("id") Long id) {
        return new ModelAndView("html/companyView").addObject("comView", this.portalService.getCompanyById(id));
    }

    @RequestMapping(value = "/admin/saveUpdateCompany")
    public String saveUpdateCompany(@ModelAttribute("comView") Company company) {
        this.portalService.saveCompany(company);
        return "redirect:/admin/allCompany";
    }

    @RequestMapping(value = "/admin/com/delete/{id}")
    public String deleteCompany(@PathVariable("id") Long id) {
        this.portalService.deleteCompanyById(id);
        return "redirect:/admin/allCompany";
    }

    @RequestMapping(value = "/accessdenied")
    public String accessdenied() {
        return "html/accessdenied";
    }

    @RequestMapping(value = "/error")
    public ModelAndView badRequest(HttpServletRequest req) {
        Object status = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value() || statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return new ModelAndView("errors/badRequest");
            }
        }
        return new ModelAndView("errors/badRequest");
    }

}


