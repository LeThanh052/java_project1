package vn.ledeem.jobhunter.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import vn.ledeem.jobhunter.domain.Role;
import vn.ledeem.jobhunter.domain.response.ResultPaginationDTO;
import vn.ledeem.jobhunter.service.RoleService;
import vn.ledeem.jobhunter.ultil.annotation.ApiMessage;
import vn.ledeem.jobhunter.ultil.error.IdInvalidException;

@RestController
@RequestMapping("/api/v1")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/roles")
    @ApiMessage("Create a Role")
    public ResponseEntity<Role> create(@Valid @RequestBody Role r) throws IdInvalidException {
        // check Name
        if (this.roleService.existByName(r.getName())) {
            throw new IdInvalidException("role với tên" + r.getName() + "không tồn tại");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(this.roleService.create(r));
    }

    @PutMapping("/roles")
    @ApiMessage("Update a role")
    public ResponseEntity<Role> update(@Valid @RequestBody Role r) throws IdInvalidException {

        // 1. Check id có tồn tại không
        Role existedRole = this.roleService.fetchById(r.getId());
        if (existedRole == null) {
            throw new IdInvalidException("Role với id = " + r.getId() + " không tồn tại");
        }

        // 2. Check name trùng (trừ chính nó)
        // if (this.roleService.existByName(r.getName())) {
        // throw new IdInvalidException("Role với name = " + r.getName() + " đã tồn
        // tại");
        // }

        // 3. Update
        Role updated = this.roleService.update(r);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/roles/{id}")
    @ApiMessage("Delete a role")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) throws IdInvalidException {

        // 1. Check id tồn tại
        if (this.roleService.fetchById(id) == null) {
            throw new IdInvalidException("Role với id = " + id + " không tồn tại");
        }

        // 2. Xóa role
        this.roleService.delete(id);

        // 3. Trả về 204 NO CONTENT đúng chuẩn REST
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/roles")
    @ApiMessage("Fetch roles")
    public ResponseEntity<ResultPaginationDTO> getRoles(
            @Filter Specification<Role> spec,
            Pageable pageable) {
        return ResponseEntity.ok(this.roleService.getRoles(spec, pageable));
    }

}
