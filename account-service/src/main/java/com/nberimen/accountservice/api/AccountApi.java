package com.nberimen.accountservice.api;

import com.nberimen.accountservice.dto.AccountDto;
import com.nberimen.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/1.0/account")
@RequiredArgsConstructor
public class AccountApi {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAll(Pageable pageable){
        return ResponseEntity.ok(accountService.getAll(pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> get(@PathVariable String id){
        return ResponseEntity.ok(accountService.get(id));
    }

    @PostMapping
    public ResponseEntity<AccountDto> save(@RequestBody AccountDto account){
        return ResponseEntity.ok(accountService.save(account));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> update(@PathVariable String id, @RequestBody AccountDto account){
        return ResponseEntity.ok(accountService.update(id, account));
    }

    @DeleteMapping
    public void delete(String id){
        accountService.delete(id);
    }
}
