package uz.cosinus.thinkstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.cosinus.thinkstore.entity.ProductPhotosEntity;

import java.util.List;
import java.util.UUID;


public interface ProductPhotosRepository extends JpaRepository<ProductPhotosEntity, UUID> {
    List<ProductPhotosEntity> getByProduct_Id (UUID productId);
    @Query("select p.photo.id from productPhotos p where p.product.id = ?1")
    List<UUID> getPhotosOfProduct(UUID id);
}
