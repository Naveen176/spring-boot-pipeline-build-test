package com.nagarro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.entity.PageInfoEntity;
import com.nagarro.repository.ValidatedUserDataRepository;

@Service
public class PageInfoService {

	@Autowired
	private ValidatedUserDataRepository pageInfo;
	
	private PageInfoEntity info = new PageInfoEntity();

	public PageInfoEntity paginationInfo(Long userId) {
		info.setHasNextPage(pageInfo.existsById((int) (userId + 1)));
		info.setHasPreviousPage(pageInfo.existsById((int) (userId - 1)));
		info.setTotal(pageInfo.count());
		return info;
	}
}
