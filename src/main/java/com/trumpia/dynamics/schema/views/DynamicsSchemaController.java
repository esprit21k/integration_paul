package com.trumpia.dynamics.schema.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trumpia.dynamics.model.DynamicsAccountEntity;
import com.trumpia.dynamics.schema.data.DynamicsSchemaRepository;
import com.trumpia.dynamics.schema.model.DynamicsSchemaEntity;

@Controller
public class DynamicsSchemaController {
	

	
	@RequestMapping(path = "/oauth/dynamics/schema")
	public String getDynamicsSchema(String resourceURL) {
		
		
		return null;
	}

}