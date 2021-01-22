package com.egovorushkin.logiweb;

import com.egovorushkin.logiweb.dao.api.CargoDao;
import com.egovorushkin.logiweb.services.api.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class CargoServiceTest {

    @Autowired
    private CargoService cargoService;

    @MockBean
    private CargoDao cargoDao;

}
