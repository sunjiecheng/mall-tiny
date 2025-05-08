package com.macro.mall.tiny.modules.ums.controller;

import com.macro.mall.tiny.modules.ums.dto.UmsAdminParam;
import com.macro.mall.tiny.modules.ums.model.UmsAdmin;
import com.macro.mall.tiny.modules.ums.service.UmsAdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UmsAdminControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UmsAdminService adminService;

    @InjectMocks
    private UmsAdminController adminController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    public void register_SuccessfulRegistration_ReturnsSuccess() throws Exception {
        UmsAdminParam adminParam = new UmsAdminParam();
        adminParam.setUsername("testUser");
        adminParam.setPassword("testPassword");

        UmsAdmin registeredAdmin = new UmsAdmin();
        registeredAdmin.setUsername("testUser");

        when(adminService.register(any(UmsAdminParam.class))).thenReturn(registeredAdmin);

        mockMvc.perform(post("/admin/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testUser\",\"password\":\"testPassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.username").value("testUser"));

        verify(adminService, times(1)).register(any(UmsAdminParam.class));
    }

    @Test
    public void register_FailedRegistration_ReturnsFailure() throws Exception {
        UmsAdminParam adminParam = new UmsAdminParam();
        adminParam.setUsername("testUser");
        adminParam.setPassword("testPassword");

        when(adminService.register(any(UmsAdminParam.class))).thenReturn(null);

        mockMvc.perform(post("/admin/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testUser\",\"password\":\"testPassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500));

        verify(adminService, times(1)).register(any(UmsAdminParam.class));
    }
}
