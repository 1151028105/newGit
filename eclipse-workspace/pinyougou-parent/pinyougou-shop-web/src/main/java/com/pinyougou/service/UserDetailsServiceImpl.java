package com.pinyougou.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;
/**
 * 认证类
 * @author 86189
 *
 */
public class UserDetailsServiceImpl implements UserDetailsService {

	private SellerService sellerService;
	
	public void setSellerService(SellerService sellerService) {
		this.sellerService = sellerService;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("经过了....");

		List<GrantedAuthority> grantAuths =new ArrayList();
		grantAuths.add(new SimpleGrantedAuthority("ROLE_SELLER"));
		
		//得到商家对象，
		TbSeller seller = sellerService.findOne(username);
		if(seller!=null) {//不等于null值，说明登录名有效，在把对象中密码拿出
			if (seller.getStatus().equals("1")) {
				return new User(seller.getSellerId(), seller.getPassword(), grantAuths);
			}else {
				return null;
			}
			
		}else {
			return null;
		}
		
		
	}

}
