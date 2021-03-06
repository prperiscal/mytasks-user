package com.general.purpose.user.repository;

import static com.google.common.collect.Sets.newHashSet;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import com.general.purpose.user.CommonTestVars;
import com.general.purpose.user.ServiceApplication;
import com.general.purpose.user.model.User;
import com.general.purpose.user.model.UserGroup;
import com.prperiscal.spring.data.compose.DataComposeResource;
import com.prperiscal.spring.data.compose.SpringDataCompose;
import lombok.Setter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles(value = CommonTestVars.PROFILE)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceApplication.class)
@SpringDataCompose
public class UserGroupRepositoryTest {

    @Setter(onMethod = @__({@Autowired}))
    private UserGroupRepository userGroupRepository;

    @Test
    @DataComposeResource("BaseUserUserGroup.json")
    public void findByTenantIdAndIdTest() {
        UUID tenant = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID userGroupId = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");

        UserGroup userGroup = userGroupRepository.findByTenantIdAndId(tenant, userGroupId);

        assertThat(userGroup.getName()).isEqualTo("group1");
        assertThat(userGroup.getUsers().stream().map(User::getName)).containsExactlyInAnyOrderElementsOf(
                newHashSet("Pablo", "Pablo1"));
    }

    @Test
    @DataComposeResource("BaseUserUserGroup.json")
    public void deleteByTenantIdAndIdTest() {
        UUID tenant = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID userGroupId = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");

        Long result = userGroupRepository.deleteByTenantIdAndId(tenant, userGroupId);
        assertThat(result).isEqualTo(1);
    }
}
