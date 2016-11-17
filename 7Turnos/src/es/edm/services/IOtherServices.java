package es.edm.services;

import es.edm.domain.entity.ConfigurationEntity;
import es.edm.domain.middle.LoginCredentials;
import es.edm.domain.middle.LoginStatus;

public interface IOtherServices {

	ConfigurationEntity getConfiguration();
	LoginStatus getLoggedUser();
	LoginStatus login(LoginCredentials credentials);
}
