package com.jjunpro.koreanair.project.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class EnumMapper {

	private Map<String, List<EnumValue>> factory = 
			new HashMap<String, List<EnumValue>>();

	// Role List 
	private List<EnumValue> toEnumValues(Class<? extends EnumModel> e){
	    return Arrays
	            .stream(e.getEnumConstants())
	            .map(EnumValue::new)
	            .collect(Collectors.toList());
	}
	
	public void put(String key, Class<? extends EnumModel> e){
        factory.put(key, toEnumValues(e));
    }
	
    /**
     * <UserRole> Role 값을 List 형태로 변환하는 메서드
     */
	private List<SimpleGrantedAuthority> toEnumValuesList(UserRole userRole){
	    return Arrays
	    		.asList(userRole)
	            .stream()
	            .map(r -> new SimpleGrantedAuthority(r.getValue()))
				.collect(Collectors.toList()
				);
	}
    public List<SimpleGrantedAuthority> userRoleList(UserRole userRole){
    	
    	return toEnumValuesList(userRole);
    }
  
    /**
     * <String> Role 값을 List 형태로 변환하는 메서드
     */
	private static List<SimpleGrantedAuthority> toEnumStringList(String userRole) {
		return Arrays
				.asList(userRole)
				.stream()
				.map(r -> new SimpleGrantedAuthority(userRole))
				.collect(Collectors.toList());
	}
    public List<SimpleGrantedAuthority> userRoleListString(String userRole){
    	return toEnumStringList(userRole);
    }
    
    public Map<String, List<EnumValue>> getAll(){
        return factory;
    }
    
    /**
     * <String> Client 에서 받은 Role 값이 메칭되는지 확인 후 List 형태로 변환하는 메서드
     */
    public static UserRole getRoleByName(String roleName) {
        return Arrays
        		.stream(UserRole.values())
        		.filter(r -> r.isCorrectName(roleName))
        		.findFirst()
        		.orElseThrow(() -> new NoSuchElementException("검색된 권한이 없습니다."));
    }
	
	public Map<String, List<EnumValue>> get(String keys){
        return Arrays
                .stream(keys.split(","))
                .collect(Collectors.toMap(Function.identity(), key -> factory.get(key)));
    }
}
