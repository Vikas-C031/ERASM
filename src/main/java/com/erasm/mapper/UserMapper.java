package com.erasm.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.erasm.dto.request.UserRequestDTO;
import com.erasm.dto.response.UserResponseDTO;
import com.erasm.entity.Employee;
import com.erasm.entity.User;

@Component
public class UserMapper {
	
	public User toEntity(UserRequestDTO dto, Employee employee) {

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        //Employee already fetched by Service
        user.setEmployee(employee);
        //enabled is controlled by business logic
        //service will decide whether to enable the account.
        //so we intentionally don't set it here.

        return user;
    }
	public UserResponseDTO toResponseDTO(User user) {

        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setEnabled(user.getEnabled());
        dto.setEmployeeCode(user.getEmployee().getEmployeeCode());
        dto.setEmployeeName(
                user.getEmployee().getFirstName()
                        + " " + user.getEmployee().getLastName());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdateAt());

        return dto;
    }
	public List<UserResponseDTO> toResponseDTOList(List<User> users) {

        return users.stream()
                .map(this::toResponseDTO)
                .toList();
    }


}
