package com.nobblecrafts.challenge.devsecopssr.config.test.interceptor;

import com.nobblecrafts.challenge.devsecopssr.config.test.annotation.WithOAuth2User;
import com.nobblecrafts.challenge.devsecopssr.config.test.web.client.InterceptableTestRestTemplate;
import com.nobblecrafts.challenge.devsecopssr.dataaccess.account.entity.AccountEntity;
import com.nobblecrafts.challenge.devsecopssr.domain.AuthenticationHelper;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.LoginRequest;
import com.nobblecrafts.challenge.devsecopssr.security.jwt.JwtTokenProvider;
import com.nobblecrafts.challenge.devsecopssr.config.test.context.DatabaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import static com.nobblecrafts.challenge.devsecopssr.util.EntitySupplier.anAccount;
import static com.nobblecrafts.challenge.devsecopssr.util.EntitySupplier.anUserActivity;

@Slf4j
public class OAuth2UserTestExecutionListener extends AbstractTestExecutionListener {

    private String cookieName;
    private DatabaseContext context;
    private PasswordEncoder encoder;
    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationHelper authenticationHelper;
    private InterceptableTestRestTemplate template;

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        initializeDependencies(testContext);

        if (testContext.getTestMethod().isAnnotationPresent(WithOAuth2User.class)) {
            WithOAuth2User annotation = testContext.getTestMethod().getAnnotation(WithOAuth2User.class);

            String username = annotation.username();
            String password = annotation.password();

            try {
                context.clear().suppose(anAccount(username, encoder.encode(password)))
                        .thenSuppose(context -> anUserActivity(context.retrieve(AccountEntity.class).get(0)))
                        .existsOnDatabase();
            } catch (Exception e) {
                log.error("Error Persisting User: {}", e.getMessage());
            }

            String token = jwtTokenProvider.generateToken(authenticationHelper
                    .authenticate(new LoginRequest(username, password)));

            log.debug("Generating token for OAuth2 user: {}", token);

            template.addInterceptor(new OAuth2UserClientHttpRequestInterceptor(cookieName, token));
        }
    }

    private void initializeDependencies(TestContext testContext) {
        var applicationContext = testContext.getApplicationContext();
        this.cookieName = applicationContext.getEnvironment().getProperty("spring.test.listener.oauth2user.cookie-name");
        this.context = applicationContext.getBean(DatabaseContext.class);
        this.encoder = applicationContext.getBean(PasswordEncoder.class);
        this.jwtTokenProvider = applicationContext.getBean(JwtTokenProvider.class);
        this.authenticationHelper = applicationContext.getBean(AuthenticationHelper.class);
        this.template = applicationContext.getBean(InterceptableTestRestTemplate.class);
    }

}
