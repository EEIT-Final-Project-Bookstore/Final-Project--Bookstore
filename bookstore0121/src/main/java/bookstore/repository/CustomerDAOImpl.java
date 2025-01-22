package customers.repository;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import customers.domain.CustomerBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class CustomerDAOImpl implements CustomerDAO {
	@PersistenceContext
	private EntityManager entityManager = null;

	public EntityManager getSession() {
		return entityManager;
	}
	
	@Override
	public Long count(JSONObject param) {
		Long customerId = param.isNull("customerId") ? null : param.getLong("customerId");
		String username = param.isNull("username") ? null : param.getString("username");
		String customerName = param.isNull("customerName") ? null : param.getString("customerName");
		byte[] password = param.isNull("password") ? null : param.getString("password").getBytes();
		String email = param.isNull("email") ? null : param.getString("email");
		String phoneNumber = param.isNull("phoneNumber") ? null : param.getString("phoneNumber");
		String mobileNumber = param.isNull("mobileNumber") ? null : param.getString("mobileNumber");

		CriteriaBuilder criteriaBuilder = this.getSession().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<CustomerBean> table = criteriaQuery.from(CustomerBean.class);

		criteriaQuery = criteriaQuery.select(criteriaBuilder.count(table));

		List<Predicate> predicates = new ArrayList<>();

		if (customerId != null) {
			predicates.add(criteriaBuilder.equal(table.get("customerId"), customerId));
		}

		if (username != null && username.length() != 0) {
			predicates.add(criteriaBuilder.like(table.get("username"), "%" + username + "%"));
		}

		if (customerName != null && customerName.length() != 0) {
			predicates.add(criteriaBuilder.like(table.get("customerName"), "%" + customerName + "%"));
		}

		if (password != null && password.length != 0) {
			predicates.add(criteriaBuilder.like(table.get("password"), "%" + password + "%"));
		}

		if (email != null && email.length()!=0) {
			predicates.add(criteriaBuilder.equal(table.get("email"), email));
		}

		if (phoneNumber != null) {
			predicates.add(criteriaBuilder.equal(table.get("phoneNumber"), phoneNumber));
		}

		if (mobileNumber != null) {
			predicates.add(criteriaBuilder.equal(table.get("mobileNumber"), mobileNumber));
		}


		if (!predicates.isEmpty()) {
			criteriaQuery = criteriaQuery.where(predicates.toArray(new Predicate[0]));
		}

		TypedQuery<Long> typedQuery = this.getSession().createQuery(criteriaQuery);
		Long result = typedQuery.getSingleResult();
		return result != null ? result : 0L;
	}

	@Override
	public List<CustomerBean> find(JSONObject param) {
		Long customerId = param.isNull("customerId") ? null : param.getLong("customerId");
		String username = param.isNull("username") ? null : param.getString("username");
		String customerName = param.isNull("customerName") ? null : param.getString("customerName");
		byte[] password = param.isNull("password") ? null : param.getString("password").getBytes();
		String email = param.isNull("email") ? null : param.getString("email");
		String phoneNumber = param.isNull("phoneNumber") ? null : param.getString("phoneNumber");
		String mobileNumber = param.isNull("mobileNumber") ? null : param.getString("mobileNumber");
	
		Integer start = param.isNull("start") ? null : param.getInt("start");
		Integer rows = param.isNull("rows") ? 5 : param.getInt("rows");
		String sort = param.isNull("sort") ? null : param.getString("sort");
		boolean dir = param.isNull("dir") ? false : param.getBoolean("dir");

		CriteriaBuilder criteriaBuilder = this.getSession().getCriteriaBuilder();
		CriteriaQuery<CustomerBean> criteriaQuery = criteriaBuilder.createQuery(CustomerBean.class);
		Root<CustomerBean> table = criteriaQuery.from(CustomerBean.class);

		List<Predicate> predicates = new ArrayList<>();

		if (customerId != null) {
			predicates.add(criteriaBuilder.equal(table.get("customerId"), customerId));
		}

		if (username != null && username.length() != 0) {
			predicates.add(criteriaBuilder.like(table.get("username"), "%" + username + "%"));
		}

		if (customerName != null && customerName.length() != 0) {
			predicates.add(criteriaBuilder.like(table.get("customerName"), "%" + customerName + "%"));
		}

		if (password != null && password.length != 0) {
			predicates.add(criteriaBuilder.like(table.get("password"), "%" + password + "%"));
		}

		if (email != null && email.length() != 0) {
			predicates.add(criteriaBuilder.like(table.get("email"), "%" + email + "%"));
		}

		if (phoneNumber != null) {
			predicates.add(criteriaBuilder.equal(table.get("phoneNumber"), phoneNumber));
		}

		if (mobileNumber != null) {
			predicates.add(criteriaBuilder.equal(table.get("mobileNumber"), mobileNumber));
		}

		if (!predicates.isEmpty()) {
			criteriaQuery = criteriaQuery.where(predicates.toArray(new Predicate[0]));
		}

		if (sort != null && sort.length() != 0) {
			if (dir) {
				criteriaQuery = criteriaQuery.orderBy(criteriaBuilder.desc(table.get(sort)));
			} else {
				criteriaQuery = criteriaQuery.orderBy(criteriaBuilder.asc(table.get(sort)));
			}
		}

		TypedQuery<CustomerBean> typedQuery = this.getSession().createQuery(criteriaQuery).setMaxResults(rows);
		if (start != null) {
			typedQuery = typedQuery.setFirstResult(start);
		}

		List<CustomerBean> result = typedQuery.getResultList();
		return result != null && !result.isEmpty() ? result : null;
	}

}
