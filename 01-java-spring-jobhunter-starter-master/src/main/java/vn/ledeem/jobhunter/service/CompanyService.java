package vn.ledeem.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.ledeem.jobhunter.domain.Company;
import vn.ledeem.jobhunter.domain.User;
import vn.ledeem.jobhunter.repository.CompanyRepository;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company handleCreateCompany(Company c) {
        return this.companyRepository.save(c);
    }

    public void handleDeleteCompany(Long id) {
        this.companyRepository.deleteById(id);
    }

    public Company fetchCompanyById(Long id) {
        return this.companyRepository.findById(id).orElse(null);
    }

    public List<Company> handleGetCompany() {
        return this.companyRepository.findAll();
    }

    public Company handleUpdateCompany(Company c) {
        Optional<Company> companyOptional = this.companyRepository.findById(c.getId());
        if (companyOptional.isPresent()) {
            Company currentCompany = companyOptional.get();
            currentCompany.setLogo(c.getLogo());
            currentCompany.setName(c.getName());
            currentCompany.setDescription(c.getDescription());
            currentCompany.setAddress(c.getAddress());
            return this.companyRepository.save(currentCompany);
        }
        return null;
    }

}
