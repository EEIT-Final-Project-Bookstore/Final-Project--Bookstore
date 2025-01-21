package bookstore.repository;

import java.util.List;

import org.json.JSONObject;

import customers.domain.CustomerBean;

public interface CustomerDAO {
	public abstract Long count(JSONObject param);
	public abstract List<CustomerBean> find(JSONObject param);
}
