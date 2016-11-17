package es.edm.dao;

import es.edm.domain.entity.ConfigurationEntity;
import es.edm.domain.middle.LoginStatus;

public interface IOthersDao {

	ConfigurationEntity getConfiguration(LoginStatus loggedUser);
	int getUserChain(String userName);
}