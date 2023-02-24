package ru.clevertec.task.dao.impl;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class ProductDAOImplTest {
    @Captor
    private ArgumentCaptor<Integer> intCaptor;
    @Mock
    private JdbcTemplate template;
    @InjectMocks
    private ProductDAOImpl productDAO;

}