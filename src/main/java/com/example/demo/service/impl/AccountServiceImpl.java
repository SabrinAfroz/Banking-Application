package com.example.demo.service.impl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.AccountDto;
import com.example.demo.entity.Account;
import com.example.demo.mapper.AccountMapper;
import com.example.demo.repository.AccountRepository;
import com.example.demo.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{

	
	private AccountRepository accountRepository;
	

	public AccountServiceImpl(AccountRepository accountRepository) {
		
		this.accountRepository = accountRepository;
	}


	@Override
	public AccountDto createAccount(AccountDto accountDto) {
		Account account = AccountMapper.mapToAccount(accountDto);
		
		Account savedAccount = accountRepository.save(account);
		return AccountMapper.maptoAccountDto(savedAccount);
		
	}


	@Override
	public AccountDto getAccountById(Long id) {
		
	Account account	= accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account does not exists"));
		return AccountMapper.maptoAccountDto(account);
	}


	@Override
	public AccountDto deposit(Long id, double balance) {
		
		Account account	= accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account does not exists"));
		
		double total = account.getBalance() + balance;
		account.setBalance(total);
		
		Account savedAccount = accountRepository.save(account);
		return AccountMapper.maptoAccountDto(savedAccount);
	}


	@Override
	
	public AccountDto withdraw(Long id, double amount) {
		
		Account account	= accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account does not exists"));

		if(account.getBalance() < amount) {
			throw new RuntimeException("Insufficient Amount");
		}
		
		double total = account.getBalance() - amount;
		
		account.setBalance(total);
		Account savedAccount = accountRepository.save(account);
		return AccountMapper.maptoAccountDto(savedAccount);
	}


	@Override
	public List<AccountDto> getAllAccounts() {
		List<Account> accounts =  accountRepository.findAll();
		
		return accounts.stream().map((account) -> AccountMapper.maptoAccountDto(account)).collect(Collectors.toList());
		
	}


	@Override
	public void deleteAccount(Long id) {
		Account account	= accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account does not exists"));

		accountRepository.deleteById(id);
	}

}
