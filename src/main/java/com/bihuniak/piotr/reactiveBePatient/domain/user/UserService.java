package com.bihuniak.piotr.reactiveBePatient.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.stream.Collectors.toList;


@Service
@Transactional
public class UserService {//implements UserDetailsService {

//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = getUserByUsername(username);
//        return new SecurityUserDetails(user);
//    }
//
//    public void changePassword(ChangePasswordRequest changePasswordRequest){
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        User user = getUserByUsername(username);
//        if(!user.getPassword().equals(changePasswordRequest.getOldPassword())){
//            throw new RuntimeException();
//        }
//        user.setPassword(changePasswordRequest.getNewPassword());
//    }
//
//    public void changeUsername(ChangeUsernameRequest changeUsernameRequest){
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        User user = getUserByUsername(username);
//        if(!user.getPassword().equals(changeUsernameRequest.getPassword())){
//            throw new RuntimeException();
//        }
//        user.setUsername(changeUsernameRequest.getNewUsername());
//    }
//
//    public UserView getMe(SecurityUserDetails userDetails){
//        UserView userView = new UserView();
//        userView.setId(userDetails.getId());
//        userView.setRoles( userDetails.getAuthorities()
//                .stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(toList())
//        );
//        return userView;
//    }
//
//    private User getUserByUsername(String username){
//        return userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("UserName "+username+" not found"));
//    }
}
