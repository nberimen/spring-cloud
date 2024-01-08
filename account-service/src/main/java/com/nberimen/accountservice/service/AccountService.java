package com.nberimen.accountservice.service;

import com.nberimen.accountservice.dto.AccountDto;
import com.nberimen.accountservice.entity.Account;
import com.nberimen.accountservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public AccountDto get(String id){
        Account account = accountRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return modelMapper.map(account, AccountDto.class);
    }

    public List<AccountDto> getAll(Pageable pageable){
        Slice<Account> accounts = accountRepository.findAll(pageable);
        List<AccountDto> accountDtos = accounts.stream()
                .map(account -> modelMapper.map(account, AccountDto.class))
                .toList();
        return accountDtos;
    }

    @Transactional
    public AccountDto save(AccountDto accountDto){
        Account account = modelMapper.map(accountDto, Account.class);
        account = accountRepository.save(account);
        accountDto.setId(account.getId());
        return accountDto;
    }

    @Transactional
    public AccountDto update(String id, AccountDto accountDto){
        Assert.isNull(id, "Id cannot be null!");
        Optional<Account> account = accountRepository.findById(id);
        Account accountToUpdate = account.map(item -> {
            item.setBirthDate(accountDto.getBirthDate());
            item.setName(accountDto.getName());
            item.setSurname(accountDto.getSurname());
            return item;
        }).orElseThrow(IllegalAccessError::new);
        return modelMapper.map(accountRepository.save(accountToUpdate), AccountDto.class);
    }

    @Transactional
    public void delete(String id){
        Account account = accountRepository.findById(id).orElseThrow(IllegalAccessError::new);
        accountRepository.delete(account);
    }
}
