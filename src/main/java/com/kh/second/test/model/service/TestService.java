package com.kh.second.test.model.service;

import com.kh.second.test.model.vo.CryptoUser;

public interface TestService {
	int insertUser(CryptoUser user);
	CryptoUser selectLogin(CryptoUser user);
}
