package uz.cosinus.thinkstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.cosinus.thinkstore.entity.CategoryEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {
    Optional<CategoryEntity> getCategoryEntitiesById(UUID id);
    Optional<CategoryEntity> getByName(String name);
    Page<CategoryEntity> findAllByIsActiveTrue(Pageable pageable);
    @Query(value = "select c from category c where c.parent.id is null")
    List<CategoryEntity> getFirstCategory();
    Page<CategoryEntity> findAllByNameContainingIgnoreCaseAndIsActiveTrueOrderByCreatedDateDesc(String word, Pageable pageable);
    List<CategoryEntity> getCategoriesByParent_Id(UUID parentId);
}
