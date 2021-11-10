package be.dietervanlangenaker.petstore.repositories;

import be.dietervanlangenaker.petstore.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
