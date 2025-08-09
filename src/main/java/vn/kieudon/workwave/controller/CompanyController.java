package vn.kieudon.workwave.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.kieudon.workwave.domain.Company;
import vn.kieudon.workwave.service.CompanyService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;






@RestController
public class CompanyController {

    private final CompanyService companyService;
    public CompanyController (CompanyService companyService){
        this.companyService = companyService;
    }

    @PostMapping("/companies")
    public ResponseEntity<Company> CreateCompany(@Valid @RequestBody Company company) {
        Company saveCompany = this.companyService.handleSaveCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveCompany);
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<Company> getCompany (@PathVariable long id) {
        Company company = this.companyService.findCompanyById(id);
        return ResponseEntity.status(HttpStatus.OK).body(company);
    }

    @GetMapping("/companies")
    public ResponseEntity<List<Company>> getAllCompanies () {
        List<Company> companies = this.companyService.findAllCompanies();
        return ResponseEntity.status(HttpStatus.OK).body(companies);
    }

    //trương hợp update một vài fiel thôi chứ không tất
    @PutMapping("/companies/{id}")
    public ResponseEntity<Company>updateCompany(@Valid @RequestBody Company reqCompany) {
        Company updateCompany = this.companyService.handleUpdateCompany(reqCompany);
        return ResponseEntity.status(HttpStatus.OK).body(updateCompany);
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<String> deleteCompanyById (@PathVariable Long id){
        this.companyService.deleteCompanyById(id);
        return ResponseEntity.status(HttpStatus.OK).body("success");
 
    }

}
