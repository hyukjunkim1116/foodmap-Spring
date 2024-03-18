package foodmap.V2.domain;

import foodmap.V2.domain.UserInfo;
import foodmap.V2.domain.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CustomUserDetails extends UserInfo implements UserDetails, OAuth2User {

    private final String username;

    private final String password;
    Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> oAuth2Attributes;

    public CustomUserDetails(UserInfo byUsername) {
//        this.username = byUsername.getUsername();
        this.username = byUsername.getEmail();
        this.password= byUsername.getPassword();
        List<GrantedAuthority> auths = new ArrayList<>();

        for(UserRole role : byUsername.getRoles()){

            auths.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
        }
        this.authorities = auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    // OAuth2 Client 필수 메서드 재정의
    @Override
    public Map<String, Object> getAttributes() { return oAuth2Attributes; }
    @Override
    public String getName() { return username; }
}
