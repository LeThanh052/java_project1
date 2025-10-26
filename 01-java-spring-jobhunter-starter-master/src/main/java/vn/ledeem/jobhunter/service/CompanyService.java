package vn.ledeem.jobhunter.service;

import org.springframework.stereotype.Service;

import vn.ledeem.jobhunter.domain.Company;
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
}
