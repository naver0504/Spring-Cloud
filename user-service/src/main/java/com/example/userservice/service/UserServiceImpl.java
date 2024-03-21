package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        final UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));
        userRepository.save(userEntity);

        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(String userId) {

        final UserEntity userEntity = userRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Not Found User"));
        final UserDto userDto = modelMapper.map(userEntity, UserDto.class);
        final List<ResponseOrder> orders = new ArrayList<>();
        userDto.setOrders(orders);

        return userDto;
    }



    @Override
    public List<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        final UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserEntity userEntity = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));

        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(), true, true, true, true, Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
    }


}
