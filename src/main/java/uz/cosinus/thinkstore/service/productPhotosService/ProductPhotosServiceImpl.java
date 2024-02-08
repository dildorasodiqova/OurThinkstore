package uz.cosinus.thinkstore.service.productPhotosService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.cosinus.thinkstore.entity.ProductPhotosEntity;
import uz.cosinus.thinkstore.repository.ProductPhotosRepository;

import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class ProductPhotosServiceImpl implements ProductPhotosService{
    private final ProductPhotosRepository productPhotosRepository;
    @Override
    public List<ProductPhotosEntity> getByProductId(UUID productId) {
        return productPhotosRepository.getByProduct_Id(productId);

    }
    @Override
    public ProductPhotosEntity save(ProductPhotosEntity productPhotos) {
        return productPhotosRepository.save(productPhotos);
    }

}
