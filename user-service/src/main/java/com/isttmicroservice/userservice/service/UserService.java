package com.isttmicroservice.userservice.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isttmicroservice.userservice.dto.ResponseDTO;
import com.isttmicroservice.userservice.dto.SearchDTO;
import com.isttmicroservice.userservice.dto.UserDTO;
import com.isttmicroservice.userservice.entity.Role;
import com.isttmicroservice.userservice.entity.User;
import com.isttmicroservice.userservice.repository.RoleRepo;
import com.isttmicroservice.userservice.repository.UserRepo;

import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface UserService {
    void create(UserDTO userDTO);

    UserDTO getUserByUsername(String username);

	void update(UserDTO userDTO);
	
	void updatePassword(UserDTO userDTO);

    void delete(Integer id);

    void deleteAll(List<Integer> ids);

    UserDTO get(Integer id);
    
    UserDTO findByUsername(String username);

    ResponseDTO<List<UserDTO>> find(SearchDTO searchDTO);
}

@Service
class UserServiceImpl implements  UserService{

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Transactional
    @Override
    public void create(UserDTO userDTO) {

    	  User user = new User();
          user.setName(userDTO.getName());
          user.setUsername(userDTO.getUsername());
          user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));  
          user.setEnabled(userDTO.isEnabled());
          user.setAddress(userDTO.getAddress());
          user.setCountry(userDTO.getCountry());
          user.setPhone(userDTO.getPhone());
          user.setAvatar(userDTO.getAvatar());
          user.setBirthdate(userDTO.getBirthday());
          user.setEmail(userDTO.getEmail());
            	
     		 Role role = new Role();
     		 role.setRole("ROLE_MEMBER") ;
     		 role.setUser(user);
    		 
     		 roleRepo.save(role);  

          userRepo.save(user);
          

          userDTO.setId(user.getId());
    }

    @Transactional
    @Override
    public void update(UserDTO userDTO) {
        ModelMapper mapper = new ModelMapper();
        mapper.createTypeMap(UserDTO.class, User.class)
                .setProvider(p -> userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new));

        User user = mapper.map(userDTO, User.class);
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        userRepo.save(user);
//        userDTO.setId(user.getId());
    }
	@Override
	@Transactional
	public void updatePassword(UserDTO userDTO){

		User user = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));  
		userRepo.save(user);
	}

    @Transactional
    @Override
    public void delete(Integer id) {
        User user = userRepo.findById(id).orElseThrow(NoResultException::new);
        if(user!= null) {
        	if(user.getRoles()!= null) {
        		roleRepo.deleteByUserId(id);	
        	}
            userRepo.deleteById(id);
        }
    }

    @Transactional
    @Override
    public void deleteAll(List<Integer> ids) {
        userRepo.deleteAllByIdInBatch(ids);
    }

    @Override
    public UserDTO get(Integer id) {
        return userRepo.findById(id).map(user -> convert(user)).orElseThrow(NoResultException::new);
    }
    
    @Override
	public UserDTO getUserByUsername(String username) {
		UserDTO userDTO = userRepo.findByUsername(username).map(user -> convert(user)).orElseThrow(NoResultException::new);
		return userDTO;
	}
    
    @Override
    public UserDTO findByUsername(String username) {
        Optional<User> userOptional = userRepo.findByUsername(username);
        return userOptional.map(this::convert).orElse(null);
    }

    @Override
    public ResponseDTO<List<UserDTO>> find(SearchDTO searchDTO) {
        List<Sort.Order> orders = Optional.ofNullable(searchDTO.getOrders()).orElseGet(Collections::emptyList).stream()
                .map(order -> {
                    if (order.getOrder().equals(SearchDTO.ASC))
                        return Sort.Order.asc(order.getProperty());

                    return Sort.Order.desc(order.getProperty());
                }).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(orders));

        Page<User> page = userRepo.find(searchDTO.getValue(), pageable);

        ResponseDTO<List<UserDTO>> responseDTO = new ModelMapper().map(page, ResponseDTO.class);
        responseDTO.setData(page.get().map(user -> convert(user)).collect(Collectors.toList()));
        return responseDTO;
    }

    private UserDTO convert(User user) {
        return new ModelMapper().map(user, UserDTO.class);
    }
}