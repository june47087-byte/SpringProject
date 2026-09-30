package com.mnu.sample.domain;

import java.util.ArrayList;
import java.util.Collection;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 생성자 자동 주입
public class CustomUserDetails implements UserDetails {
	// 가입된 회원정보를 리턴
	public final UserDTO userDTO;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> rollList = new ArrayList<GrantedAuthority>();
		rollList.add(new SimpleGrantedAuthority(userDTO.getRole().toString()));
		return null;
	}

	@Override
	public @Nullable String getPassword() {
		
		return userDTO.getPasswd();
	}

	@Override
	public String getUsername() {
		
		return userDTO.getUserid();
	}

}
