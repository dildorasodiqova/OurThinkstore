package uz.cosinus.thinkstore.service.transactionService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.cosinus.thinkstore.dto.createDto.TransactionCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.TransactionResponseDto;
import uz.cosinus.thinkstore.entity.OrderEntity;
import uz.cosinus.thinkstore.entity.TransactionEntity;
import uz.cosinus.thinkstore.enums.PaymentType;
import uz.cosinus.thinkstore.enums.TransactionStatus;
import uz.cosinus.thinkstore.exception.BadRequestException;
import uz.cosinus.thinkstore.exception.DataNotFoundException;
import uz.cosinus.thinkstore.repository.OrderRepository;
import uz.cosinus.thinkstore.repository.TransactionRepository;
import uz.cosinus.thinkstore.service.orderService.OrderService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static uz.cosinus.thinkstore.enums.TransactionStatus.CANCELED;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final OrderRepository orderRepository;

    public TransactionResponseDto transaction(TransactionCreateDto transactionCreateDto, UUID currentUser) {
        TransactionEntity transactionEntity = mapToEntity(transactionCreateDto, currentUser);
        transactionRepository.save(transactionEntity);
        return mapToDto(transactionEntity);
    }

    public List<TransactionResponseDto> getAll(int page, int size) {
        Page<TransactionEntity> all = transactionRepository.findAllByIsActiveTrue(PageRequest.of(page, size));
        return mapToDtoList(all.stream().toList());
    }

    public TransactionResponseDto getById(UUID transactionId) {
        TransactionEntity transactionEntity = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new DataNotFoundException("Transaction not found with id: " + transactionId));
        return mapToDto(transactionEntity);
    }

    @Override
    public List<TransactionResponseDto> transactionsOfUser(UUID userId) {
        List<TransactionEntity> all = transactionRepository.transactionsOfUser(userId);
        return mapToDtoList(all);
    }

    public TransactionResponseDto updateStatus(UUID transactionId, TransactionStatus status){
        TransactionEntity transactionEntity = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new DataNotFoundException("Transaction not found with id: " + transactionId));
        transactionEntity.setStatus(status);
        transactionRepository.save(transactionEntity);
        return mapToDto(transactionEntity);
    }
    public TransactionResponseDto cancelTransaction(UUID transactionId){
        TransactionEntity transactionEntity = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new DataNotFoundException("Transaction not found with id: " + transactionId));
        transactionEntity.setStatus(CANCELED);
        transactionRepository.save(transactionEntity);
        return mapToDto(transactionEntity);
    }



    private TransactionEntity mapToEntity(TransactionCreateDto transactionCreateDto, UUID currentUser) {
        TransactionEntity transactionEntity = new TransactionEntity();

        OrderEntity order = orderRepository.findById(transactionCreateDto.getOrderId()).orElseThrow(()-> new DataNotFoundException("Order not found !"));
        if (!Objects.equals(order.getUser().getId(), currentUser)){
            throw new BadRequestException("You cannot transaction");
        }
        transactionEntity.setOrderId(transactionCreateDto.getOrderId());
        transactionEntity.setPrice(transactionCreateDto.getPrice());
        transactionEntity.setPaymentType(transactionCreateDto.getPaymentType());
        transactionEntity.setStatus(TransactionStatus.CREATED); // shuyerda created deyishim togrimi
        return transactionEntity;
    }

    private TransactionResponseDto mapToDto(TransactionEntity transactionEntity) {
        TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
        transactionResponseDto.setId(transactionEntity.getId());
        transactionResponseDto.setOrderId(transactionEntity.getOrderId());
        transactionResponseDto.setPrice(transactionEntity.getPrice());
        transactionResponseDto.setPaymentType(transactionEntity.getPaymentType());
        transactionResponseDto.setStatus(transactionEntity.getStatus());
        transactionResponseDto.setCanceledDate(transactionEntity.getCanceledDate());
        transactionResponseDto.setPayedDate(transactionEntity.getPayedDate());
        transactionResponseDto.setCreatedDate(transactionEntity.getCreatedDate().toLocalDateTime());
        return transactionResponseDto;
    }

    private List<TransactionResponseDto> mapToDtoList(List<TransactionEntity> transactionEntities) {
        return transactionEntities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
