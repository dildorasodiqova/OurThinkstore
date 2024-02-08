package com.example.ourThinkstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.cosinus.thinkstore.entity.CategoryEntity;
import uz.cosinus.thinkstore.entity.ProductEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    Page<ProductEntity> findAllByCategoryId(UUID sellerId, UUID categoryId, PageRequest pageRequest);


    //    @Query(value = """
//                SELECT p FROM product p
//                                  INNER JOIN category c ON c.id = p.category.id
//                WHERE c.name LIKE '%' || :keyword || '%'
//                   OR p.name LIKE '%' || :keyword || '%'
//                   """
//            )
    @Query(value = """
            SELECT p FROM product p where p.category.name ilike '%' || :keyword || '%' or p.name ilike '%' || :keyword || '%'
            """
    )
    Page<ProductEntity> searchAllByNameOrCategoryName(@Param("keyword") String keyword, Pageable pageable);

    Optional<ProductEntity> findByNameIgnoreCase(String name);

    List<ProductEntity> searchAllByCategoryOrNameContainingIgnoreCase(CategoryEntity category, String name);


    List<ProductEntity> findAllByCategoryId(UUID categoryId);

    Page<ProductEntity> findAllByIsActiveTrue(PageRequest pageRequest);

}
