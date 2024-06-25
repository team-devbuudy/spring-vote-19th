package ceos.springvote.jwt.domain;

import ceos.springvote.constant.Part;
import ceos.springvote.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.ArrayList;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final Member member;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority(){
                return member.getRole().getText();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getName();
    }


    public String getLoginId() {return member.getLoginId();}

    public Part getPart() {return member.getPart();}

    public String getEmail() {return member.getEmail();}

    public Long getId() {return member.getId();}


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
}
