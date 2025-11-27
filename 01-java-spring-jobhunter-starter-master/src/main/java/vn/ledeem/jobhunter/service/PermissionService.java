package vn.ledeem.jobhunter.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.ledeem.jobhunter.domain.Permission;
import vn.ledeem.jobhunter.domain.response.ResultPaginationDTO;
import vn.ledeem.jobhunter.repository.PermissionRepository;

@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public boolean IspermissionExist(Permission p) {
        return permissionRepository.existsByModuleAndApiPathAndMethod(
                p.getModule(),
                p.getApiPath(),
                p.getMethod());
    }

    public Permission fetchById(Long id) {
        Optional<Permission> pOptional = this.permissionRepository.findById(id);
        if (pOptional.isPresent()) {
            return pOptional.get();
        }
        return null;
    }

    public Permission create(Permission p) {
        return this.permissionRepository.save(p);
    }

    public Permission update(Permission p) {
        Permission permissionDB = this.fetchById(p.getId());
        if (permissionDB != null) {
            permissionDB.setName(p.getName());
            permissionDB.setApiPath(p.getApiPath());
            permissionDB.setMethod(p.getMethod());
            permissionDB.setModule(p.getModule());

            // update
            permissionDB = this.permissionRepository.save(permissionDB);
            return permissionDB;
        }
        return null;
    }

    public void delete(Long id) {
        // delete permission roles
        Optional<Permission> pOptional = this.permissionRepository.findById(id);
        Permission currentPermission = pOptional.get();
        currentPermission.getRoles().forEach(role -> role.getPermissions().remove(currentPermission));

        // deletepemission
        this.permissionRepository.delete(currentPermission);

    }

    public ResultPaginationDTO fetchAll(Specification<Permission> spec, Pageable pageable) {

        Page<Permission> pageUser = this.permissionRepository.findAll(spec, pageable);

        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pageUser.getTotalPages());
        mt.setTotal(pageUser.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(pageUser.getContent());

        return rs;
    }

}
