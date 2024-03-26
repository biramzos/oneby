package com.web.oneby.modules.users.Specifications;

import com.web.oneby.commons.Utils.StringUtil;
import com.web.oneby.modules.users.Models.User;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;

public class UserSpecification {

    public static Specification<User> getSpecification(Map<String, Object> filter){
        return ((root, query, criteriaBuilder) -> {
            Predicate predication = criteriaBuilder.conjunction();
            for (String key: filter.keySet()) {
                Object value = filter.get(key);
                if (key.equals("name") && StringUtil.isNotEmpty((String) value)) {
                    predication = criteriaBuilder.or(
                            criteriaBuilder.like(
                                    root.get("nameKK"), "%" + (String) value + "%"
                            ),
                            criteriaBuilder.like(
                                    root.get("nameRU"), "%" + (String) value + "%"
                            ),
                            criteriaBuilder.like(
                                    root.get("nameEN"), "%" + (String) value + "%"
                            ),
                            criteriaBuilder.like(
                                    root.get("lastnameKK"), "%" + (String) value + "%"
                            ),
                            criteriaBuilder.like(
                                    root.get("lastnameRU"), "%" + (String) value + "%"
                            ),
                            criteriaBuilder.like(
                                    root.get("lastnameEN"), "%" + (String) value + "%"
                            ),
                            criteriaBuilder.like(
                                    root.get("username"), "%" + (String) value + "%"
                            ),
                            criteriaBuilder.like(
                                    root.get("email"), "%" + (String) value + "%"
                            )
                    );
                }
                if (key.equals("roles") && !((List<String>) value).isEmpty()) {
                    List<String> roles = ((List<String>) value);
                    Path<List<String>> rolesPath = root.join("roles");
                    Predicate rolePredicate = rolesPath.in(roles);
                    predication = criteriaBuilder.and(predication, rolePredicate);
                }
            }
            return predication;
        });
    }

}
