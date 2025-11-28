package vn.ledeem.jobhunter.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.ledeem.jobhunter.domain.Permission;
import vn.ledeem.jobhunter.domain.Role;
import vn.ledeem.jobhunter.domain.response.ResultPaginationDTO;
import vn.ledeem.jobhunter.repository.PermissionRepository;
import vn.ledeem.jobhunter.repository.RoleRepository;

@Service
@RequestMapping("/api/v1")
public class RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public boolean existByName(String name) {
        return this.roleRepository.existsByName(name);
    }

    public Role fetchById(Long id) {
        Optional<Role> rOptional = this.roleRepository.findById(id);
        if (rOptional.isPresent()) {
            return rOptional.get();
        }
        return null;
    }

    public Role create(Role r) {
        // check permissions
        if (r.getPermissions() != null) {
            // Lấy danh sách ID permission từ request
            List<Long> reqPermissions = r.getPermissions()
                    .stream()
                    .map(x -> x.getId())
                    .collect(Collectors.toList());

            // Lấy danh sách permission hợp lệ trong DB
            List<Permission> dbPermissions = this.permissionRepository.findByIdIn(reqPermissions);

            // Gán lại vào role
            r.setPermissions(dbPermissions);
        }

        // Lưu role
        return this.roleRepository.save(r);
    }

    public Role update(Role r) {
        Role roleDB = this.fetchById(r.getId());
        // check permission
        if (r.getPermissions() != null) {
            List<Long> reqPermissions = r.getPermissions()
                    .stream().map(x -> x.getId())
                    .collect(Collectors.toList());

            List<Permission> dbPermissions = this.permissionRepository.findByIdIn(reqPermissions);
            r.setPermissions(dbPermissions);
        }

        roleDB.setName(r.getName());
        roleDB.setDescription(r.getDescription());
        roleDB.setActive(r.isActive());
        roleDB.setPermissions(r.getPermissions());

        return this.roleRepository.save(roleDB);
    }

    public void delete(Long id) {
        this.roleRepository.deleteById(id);
    }

    public ResultPaginationDTO getRoles(Specification<Role> spec, Pageable pageable) {

        // Lấy page data từ DB
        Page<Role> pRole = this.roleRepository.findAll(spec, pageable);

        // Tạo DTO kết quả
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        // Set thông tin meta
        mt.setPage(pageable.getPageNumber() + 1); // Trang hiện tại (tính từ 1)
        mt.setPageSize(pageable.getPageSize()); // Kích thước trang
        mt.setPages(pRole.getTotalPages()); // Tổng số trang
        mt.setTotal(pRole.getTotalElements()); // Tổng số bản ghi

        // Gán vào DTO trả về
        rs.setMeta(mt);
        rs.setResult(pRole.getContent()); // Danh sách role

        return rs;
    }

}
