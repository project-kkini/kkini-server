package com.server.ggini.global.security.provider;

import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.domain.member.service.MemberService;
import com.server.ggini.global.error.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
public class EmailPasswordAuthenticationProvider implements AuthenticationProvider {

    private final MemberService memberService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        final String memberEmail = token.getName();
        final String memberPassword = (String) token.getCredentials();

        validateEmail(memberEmail);
        final Member member = getMemberByEmail(memberEmail);
        if (!member.isMatchingPassword(memberPassword)) {
            throw new BadCredentialsException(ErrorCode.BAD_CREDENTIALS.getMessage());
        }

        return UsernamePasswordAuthenticationToken.authenticated(
                member,
                memberPassword,
                List.of(new SimpleGrantedAuthority(member.getRole().getValue())
        ));
    }

    private static void validateEmail(String memberEmail) {
        if (!EmailValidator.getInstance().isValid(memberEmail)) {
            throw new BadCredentialsException(ErrorCode.BAD_CREDENTIALS.getMessage());
        }
    }

    private Member getMemberByEmail(String memberEmail) {
        try {
            return memberService.getMemberByEmail(memberEmail);
        } catch (Exception e) {
            throw new BadCredentialsException(ErrorCode.BAD_CREDENTIALS.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
