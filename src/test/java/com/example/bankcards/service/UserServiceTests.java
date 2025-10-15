package com.example.bankcards.service;

import com.example.bankcards.constant.OperationStatus;
import com.example.bankcards.dto.filter.UserSearchFilter;
import com.example.bankcards.dto.request.UserRequest;
import com.example.bankcards.dto.response.Response;
import com.example.bankcards.dto.response.UserResponse;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.ObjectNotFoundException;
import com.example.bankcards.mapper.UserMapper;
import com.example.bankcards.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

import static com.example.bankcards.TestData.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserServiceImpl subject;

    @Mock
    private DbService dbService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void createUserSuccessTest() {
        User user = mock(User.class);
        Set<Role> roleSet = mock(Set.class);
        UserResponse.UserInfo userInfo =
                successUserResponse(CREATE_USER_REQUEST.requestId().toString()).getUserInfoList().getFirst();

        when(userMapper.toUser(any())).thenReturn(user);
        when(passwordEncoder.encode(any())).thenReturn("");
        when(dbService.findRolesByNames(any())).thenReturn(roleSet);
        when(dbService.saveUser(any())).thenReturn(user);
        when(userMapper.toUserInfo(any())).thenReturn(userInfo);

        UserResponse userResponse = subject.createUser(CREATE_USER_REQUEST);
        assertEquals(OperationStatus.COMPLETED, userResponse.getOperationStatus());
        assertEquals(userResponse.getResponseId(), CREATE_USER_REQUEST.getRequestId().toString());
        UserResponse.UserInfo responseUserInfo = userResponse.getUserInfoList().getFirst();
        assertThat(userInfo)
                .usingRecursiveComparison()
                .isEqualTo(responseUserInfo);

        verify(userMapper, times(1)).toUser(any());
        verify(userMapper, times(1)).toUserInfo(any());
        verify(dbService, times(1)).findRolesByNames(any());
        verify(dbService, times(1)).saveUser(any());
        verify(passwordEncoder, times(1)).encode(any());
    }

    @Test
    void createUserFailureNotFoundTest() {
        UserRequest request = mock(UserRequest.class);
        User user = mock(User.class);

        when(userMapper.toUser(any())).thenReturn(user);
        when(passwordEncoder.encode(any())).thenReturn("");
        when(dbService.findRolesByNames(any()))
                .thenThrow(new ObjectNotFoundException("Роли не найдены"));

        String message = assertThrows(ObjectNotFoundException.class, () -> subject.createUser(request))
                .getMessage();
        assertEquals("Роли не найдены", message);

        verify(userMapper, times(1)).toUser(any());
        verify(userMapper, times(0)).toUserInfo(any());
        verify(dbService, times(1)).findRolesByNames(any());
        verify(dbService, times(0)).saveUser(any());
        verify(passwordEncoder, times(1)).encode(any());
    }

    @Test
    void updateUserSuccessTest() {
        User user = mock(User.class);
        UserResponse.UserInfo cardInfo =
                successUserResponse(UPDATE_USER_REQUEST.requestId().toString()).getUserInfoList().getFirst();

        when(dbService.findUserByIdLockUser(any())).thenReturn(user);
        when(userMapper.toUserInfo(any())).thenReturn(cardInfo);

        UserResponse userResponse = subject.updateUser(UPDATE_USER_REQUEST);
        assertEquals(OperationStatus.COMPLETED, userResponse.getOperationStatus());
        assertEquals(userResponse.getResponseId(), UPDATE_USER_REQUEST.getRequestId().toString());
        UserResponse.UserInfo responseUserInfo = userResponse.getUserInfoList().getFirst();
        assertThat(cardInfo)
                .usingRecursiveComparison()
                .isEqualTo(responseUserInfo);

        verify(dbService, times(1)).findUserByIdLockUser(any());
        verify(userMapper, times(1)).toUserInfo(any());
    }

    @Test
    void updateUserFailureNotFoundTest() {
        UserRequest request = mock(UserRequest.class);

        when(dbService.findUserByIdLockUser(any()))
                .thenThrow(new ObjectNotFoundException("Пользователь не найден"));

        String message = assertThrows(ObjectNotFoundException.class, () -> subject.updateUser(request))
                .getMessage();
        assertEquals("Пользователь не найден", message);

        verify(dbService, times(1)).findUserByIdLockUser(any());
        verify(userMapper, times(0)).toUserInfo(any());
    }

    @Test
    void deleteUserSuccessTest() {
        when(dbService.existsUserById(any())).thenReturn(false);

        Response userResponse = subject.deleteUser(DELETE_USER_REQUEST);
        assertEquals(OperationStatus.COMPLETED, userResponse.getOperationStatus());
        assertEquals(userResponse.getResponseId(), DELETE_USER_REQUEST.getRequestId().toString());

        verify(dbService, times(1)).deleteUserById(any());
        verify(dbService, times(1)).existsUserById(any());
    }

    @Test
    void deleteUserFailureTest() {
        UserRequest request = mock(UserRequest.class);

        when(dbService.existsUserById(any())).thenReturn(true);

        String message = assertThrows(IllegalStateException.class, () -> subject.deleteUser(request))
                .getMessage();
        assertEquals("Неудачная попытка удаления пользователя", message);

        verify(dbService, times(1)).deleteUserById(any());
        verify(dbService, times(1)).existsUserById(any());
    }

    @Test
    void searchUserSuccessTest() {
        UserSearchFilter filter = mock(UserSearchFilter.class);
        Page<User> page = mock(Page.class);
        List<UserResponse.UserInfo> userInfoList =
                successUserResponse(SEARCH_USER_REQUEST.requestId().toString()).getUserInfoList();

        when(userMapper.toUserSearchFilter(any())).thenReturn(filter);
        when(dbService.searchUser(any(), anyInt(), anyInt(), any(), any())).thenReturn(page);
        when(userMapper.toUserInfoList(any())).thenReturn(userInfoList);

        UserResponse userResponse = subject.searchUser(SEARCH_USER_REQUEST, 0, 10, Sort.Direction.ASC, "id");
        assertEquals(OperationStatus.COMPLETED, userResponse.getOperationStatus());
        assertEquals(userResponse.getResponseId(), SEARCH_USER_REQUEST.getRequestId().toString());
        UserResponse.UserInfo responseUserInfo = userResponse.getUserInfoList().getFirst();
        assertThat(userInfoList.getFirst())
                .usingRecursiveComparison()
                .isEqualTo(responseUserInfo);

        verify(userMapper, times(1)).toUserSearchFilter(any());
        verify(dbService, times(1)).searchUser(any(), anyInt(), anyInt(), any(), any());
        verify(userMapper, times(1)).toUserInfoList(any());
    }
}
