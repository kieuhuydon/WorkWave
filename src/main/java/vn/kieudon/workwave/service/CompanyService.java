package vn.kieudon.workwave.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import vn.kieudon.workwave.domain.Company;
import vn.kieudon.workwave.repository.CompanyRepository;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    public Company handleSaveCompany(Company company){
       return  this.companyRepository.save(company);
    }

    public Company findCompanyById(Long id){
        Optional<Company> otpCompany = this.companyRepository.findById(id);
        if(otpCompany.isPresent()){
            return otpCompany.get();
        }
        return null;
    }

    public List<Company> findAllCompanies(){
        return this.companyRepository.findAll();
    }

    public Company handleUpdateCompany(Company company, Long id){
        Company currentCompany = this.findCompanyById(id);
        if(currentCompany != null){
            currentCompany.setName(company.getName());
            currentCompany.setAddress(company.getAddress());
            currentCompany.setLogo(company.getLogo());
            currentCompany.setDescription(company.getDescription());
            
            this.handleSaveCompany(currentCompany);
            return currentCompany;
        }
        return null;
    }

    public Company handlePartialUpdateCompany(Company company, Long id) {
    Company currentCompany = this.findCompanyById(id);
    if (currentCompany != null) {
        if (company.getName() != null) {
            currentCompany.setName(company.getName());
        }
        if (company.getAddress() != null) {
            currentCompany.setAddress(company.getAddress());
        }
        if (company.getLogo() != null) {
            currentCompany.setLogo(company.getLogo());
        }
        if (company.getDescription() != null) {
            currentCompany.setDescription(company.getDescription());
        }
        return this.handleSaveCompany(currentCompany);
    }
    return null;
}


    public void deleteCompanyById(Long id){
        this.companyRepository.deleteById(id);
    }
    
}
