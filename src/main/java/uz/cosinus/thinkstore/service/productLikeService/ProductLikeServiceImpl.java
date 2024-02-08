package uz.cosinus.thinkstore.service.productLikeService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.cosinus.thinkstore.dto.createDto.ProductLikeCreateDto;
import uz.cosinus.thinkstore.entity.ProductEntity;
import uz.cosinus.thinkstore.entity.ProductLikeEntity;
import uz.cosinus.thinkstore.entity.UserEntity;
import uz.cosinus.thinkstore.repository.ProductLikeRepository;
import uz.cosinus.thinkstore.service.productService.ProductService;
import uz.cosinus.thinkstore.service.userService.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductLikeServiceImpl implements ProductLikeService{
    private final ProductLikeRepository productLikeRepository;
    private final ProductService productService;
    private final UserService userService;

    @Override
    public String create(ProductLikeCreateDto dto) {
        if (productLikeRepository.existsAllByProductIdAndUserId(dto.getProduct(), dto.getUser())){
            productLikeRepository.deleteAllByProductIdAndUserId(dto.getProduct(), dto.getUser());
            return "DisLike";
        }else {
            ProductLikeEntity entity = parse(dto);
            productLikeRepository.save(entity);
            return "Like";
        }
    }


    @Override
    public Long getALlLike(UUID productId) {
        return productLikeRepository.countAllByProductId(productId);
    }

    private ProductLikeEntity parse(ProductLikeCreateDto dto){
        UserEntity user = userService.findById(dto.getUser());
        ProductEntity product = productService.findById(dto.getProduct());
        return new ProductLikeEntity(user, product);
    }


}
