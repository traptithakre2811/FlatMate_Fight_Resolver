package com.flatmate.service.serviceImpl;

import com.flatmate.dto.LoginReqDto;
import com.flatmate.dto.LoginResDto;
import com.flatmate.dto.UserRequestDto;
import com.flatmate.dto.UserResponseDto;
import com.flatmate.entity.Flat;
import com.flatmate.entity.User;
import com.flatmate.exceptionhandler.customeexception.EmptyInputException;
import com.flatmate.exceptionhandler.customeexception.NotFoundException;
import com.flatmate.exceptionhandler.customeexception.WrongInputException;
import com.flatmate.jwt.JwtTokenUtil;
import com.flatmate.repository.FlatRepo;
import com.flatmate.repository.UserRepo;
import com.flatmate.response.CommonResponse;
import com.flatmate.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FlatRepo flatRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public ResponseEntity<?> registerFlatmate(UserRequestDto userRequestDto) {

        if (userRequestDto.getEmail().isEmpty()) {
            throw new EmptyInputException(400, "email can not be empty or null");
        }
        if (userRequestDto.getPassword().isEmpty()) {
            throw new EmptyInputException(400, "password can not be empty or null");
        }
        Optional<User> byEmail = userRepo.findByEmail(userRequestDto.getEmail());
        if (byEmail.isPresent()) {
            throw new WrongInputException(400, "email can not be duplicate");
        }
        Optional<Flat> flat = flatRepo.findByFlatCode(userRequestDto.getFlatCode());
        if (!flat.isPresent()) {
            throw new NotFoundException(404, "flat not found");
        }
        User user = modelMapper.map(userRequestDto, User.class);
        user.setFlat(flat.get());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        User userRegister = userRepo.save(user);
        UserResponseDto userResponseDto = modelMapper.map(userRegister, UserResponseDto.class);
        return ResponseEntity.ok(new CommonResponse("User Register Successfully", 200, userResponseDto));

    }

    @Override
    public ResponseEntity<?> login(LoginReqDto loginReqDto) {
        User user = userRepo.findByEmail(loginReqDto.getEmail()).orElseThrow(() -> new WrongInputException(400, "email is wrong"));
        if(!passwordEncoder.matches(loginReqDto.getPassword(),user.getPassword()))
        {
            throw new WrongInputException(400,"password is wrong");
        }
        Map<String, Object> map=new HashMap<>();
        map.put("userId",user.getId());
        map.put("flatCode",user.getFlat().getFlatCode());
        String token = jwtTokenUtil.generateToken(map, loginReqDto.getEmail());
        LoginResDto loginResDto=new LoginResDto();
        loginResDto.setEmail(loginResDto.getEmail());
        loginResDto.setToken(token);
        return ResponseEntity.ok(new CommonResponse("login succussfull",200,loginResDto));
    }


}
