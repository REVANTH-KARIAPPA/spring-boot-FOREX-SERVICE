package com.forexservice.service.impl;

import java.util.*;
import java.util.stream.Collectors;


import com.forexservice.Repository.AccountRepository;
import com.forexservice.Repository.RoleRepository;
import com.forexservice.Repository.UserRepository;
import com.forexservice.exception.UserNameException;
import com.forexservice.exception.UserNotFoundException;
import com.forexservice.model.*;
import com.forexservice.service.RoleService;
import com.forexservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private RoleService roleService;
    @Autowired
    private AccountRepository accountRepository;



    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    //MY methord for authentication
    public UserInfo getUserByName(String name) throws UsernameNotFoundException{

        User user = userRepository.findByUsername(name);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        long id = user.getId();
        String s=String.valueOf(id);

        Set <Role> p=user.getRoles();
        List<String>roles = new ArrayList<>();

        for (Role x : p) {
            roles.add(x.getName());
        }
        UserInfo userInfo=new UserInfo(s,user.getUsername(),user.getEmail(),roles);

        return userInfo;
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public User findOne(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(UserDto user) {
       Optional<User>  u= Optional.ofNullable(userRepository.findByUsername(user.getUsername()));
        if(!u.isPresent()) {
            User nUser = user.getUserFromDto();
            nUser.setPassword(bcryptEncoder.encode(user.getPassword()));

            Role role = roleService.findByName("USER");
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(role);


            nUser.setRoles(roleSet);

            return userRepository.save(nUser);
        }
        else {
            throw new UserNameException("UserName is Taken");
        }
    }


    // my services
    public List<User> getAllUsers(){
        List <User> users =new ArrayList<>(userRepository.findAll());

        return users.stream().filter(u->{Set<Role> roles=u.getRoles();
          Role r= roleRepository.findRoleByName("USER");
          boolean flag=false;
            for (Role re:roles
                 ) {
                if(re==r)
                    flag=true;

            }
            return flag;
        }).collect(Collectors.toList());
    }

    public User getUserById(long id){
        Optional<User> users=userRepository.findById(id);
        if(users.isPresent()){

            return userRepository.findById(id).get();
        }
        else{
            throw new UserNotFoundException("Get Operation failed \n No User Found with id : "+id);
        }
    }
    public void deleteUser(long id)
    {
        Optional<User> users=userRepository.findById(id);
        if(id==1L){
            throw new UserNotFoundException(" Delete  Operation failed  \n You can not delete admin : "+id);
        }
        if(users.isPresent()){
            User u=userRepository.findById(id).get();
            u.removeRoles(u.getRoles());
            Optional<Account> account1= Optional.ofNullable(u.getAccount());
            if(account1.isPresent()) {
                Account account2 = u.getAccount();
                accountRepository.deleteById(account2.getAccountId());
            }
            userRepository.deleteById(id);
        }
        else{
            throw new UserNotFoundException(" Delete  Operation failed  \n No User Found with id : "+id);
        }
    }
    public void createUser(User user){
        userRepository.save(user);
    }
}
