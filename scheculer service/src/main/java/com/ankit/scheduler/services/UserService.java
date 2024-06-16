package com.ankit.scheduler.services;

import com.ankit.scheduler.Entity.User;
import com.ankit.scheduler.models.AuthResponseDTO;
import com.ankit.scheduler.models.LoginDTO;
import com.ankit.scheduler.models.RegisterDTO;
import com.ankit.scheduler.repositories.UserRepository;
import com.ankit.scheduler.security.JwtTokenUtil;
import com.ankit.scheduler.security.UserContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public User createNewUser(User user) {
        user.setRoleId(this.roleService.getAdminRole().getId());
        return this.userRepository.save(user);
    }

    public User createNewUser(RegisterDTO dto) {
        dto.setPassword(this.passwordEncoder.encode(dto.getPassword()));
        return this.createNewUser(new User(dto));
    }

    public User getUserById(Long id) {
        return this.userRepository.findById(id).orElseThrow();
    }

    public User getCurrentUser() {
        return UserContextHolder.getUserDetails().getUser();
    }

    public User getByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow();
    }

    public List<User> getAllUserByName(String name) {
        return this.userRepository.findAllByNameContaining(name);
    }

    public AuthResponseDTO login(LoginDTO dto) throws AuthenticationException {
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = this.jwtTokenUtil.generateToken(authentication);
        return new AuthResponseDTO(token);
    }
}
