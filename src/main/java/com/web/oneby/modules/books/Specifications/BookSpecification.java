package com.web.oneby.modules.books.Specifications;

import com.web.oneby.commons.Utils.StringUtil;
import com.web.oneby.modules.books.Models.Book;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
public class BookSpecification {

    public static Specification<Book> getSpecification(Map<String, Object> filter){
        return ((root, query, criteriaBuilder) -> {
            Predicate predication = criteriaBuilder.conjunction();
            for (String key: filter.keySet()) {
                Object value = filter.get(key);
                if (key.equals("name") && StringUtil.isNotEmpty((String) value)) {
                    predication = criteriaBuilder.or(
                        criteriaBuilder.like(root.get("titleKK"), "%" + (String) value + "%"),
                        criteriaBuilder.like(root.get("titleRU"), "%" + (String) value + "%"),
                        criteriaBuilder.like(root.get("titleEN"), "%" + (String) value + "%"),
                        criteriaBuilder.like(root.get("descriptionKK"), "%" + (String) value + "%"),
                        criteriaBuilder.like(root.get("descriptionRU"), "%" + (String) value + "%"),
                        criteriaBuilder.like(root.get("descriptionEN"), "%" + (String) value + "%"),
                        criteriaBuilder.like(root.get("authorKK"), "%" + (String) value + "%"),
                        criteriaBuilder.like(root.get("authorRU"), "%" + (String) value + "%"),
                        criteriaBuilder.like(root.get("authorEN"), "%" + (String) value + "%")
                    );
                }
                if (key.equals("genres") && !((List<String>) value).isEmpty()) {
                    List<String> genres = ((List<String>) value);
                    Path<List<String>> genresPath = root.join("genres");
                    Predicate genrePredicate = genresPath.in(genres);
                    predication = criteriaBuilder.and(predication, genrePredicate);
                }
            }
            return predication;
        });
    }

}
