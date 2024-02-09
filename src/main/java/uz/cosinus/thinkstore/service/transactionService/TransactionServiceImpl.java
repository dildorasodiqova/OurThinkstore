package uz.cosinus.thinkstore.service.transactionService;

import com.google.zxing.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.cosinus.thinkstore.dto.createDto.TransactionCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.TransactionResponseDto;
import uz.cosinus.thinkstore.entity.TransactionEntity;
import uz.cosinus.thinkstore.exception.DataNotFoundException;
import uz.cosinus.thinkstore.repository.TransactionRepository;
import uz.cosinus.thinkstore.service.orderService.OrderService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final OrderService orderService;

    public TransactionResponseDto createTransaction(TransactionCreateDto transactionCreateDto) {
        TransactionEntity transactionEntity = mapToEntity(transactionCreateDto);
         transactionRepository.save(transactionEntity);
         return parse(transactionEntity);
    }

    public List<TransactionResponseDto> getAllTransactions(int page, int size) {
        Page<TransactionEntity> all = transactionRepository.findAllByIsActiveTrue(PageRequest.of(page, size));
        return mapToDtoList(all.stream().toList());
    }

    public TransactionResponseDto getTransactionById(UUID transactionId) {
        TransactionEntity transactionEntity = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new DataNotFoundException("Transaction not found with id: " + transactionId));
        return mapToDto(transactionEntity);
    }

    // Other methods such as updateTransaction, deleteTransaction, etc.

    private TransactionEntity mapToEntity(TransactionCreateDto transactionCreateDto) {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setOrder(orderService.getOrderById(transactionCreateDto.getOrderId()));
        transactionEntity.setPrice(transactionCreateDto.getPrice());
        transactionEntity.setPaymentType(PaymentType.valueOf(transactionCreateDto.getPaymentType()));
        transactionEntity.setStatus(TransactionStatus.PENDING); // Set default status
        return transactionEntity;
    }

    private TransactionResponseDto mapToDto(TransactionEntity transactionEntity) {
        TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
        transactionResponseDto.setId(transactionEntity.getId());
        transactionResponseDto.setOrderId(transactionEntity.getOrder().getId());
        transactionResponseDto.setPrice(transactionEntity.getPrice());
        transactionResponseDto.setPaymentType(transactionEntity.getPaymentType());
        transactionResponseDto.setStatus(transactionEntity.getStatus());
        transactionResponseDto.setCanceledDate(transactionEntity.getCanceledDate());
        transactionResponseDto.setPayedDate(transactionEntity.getPayedDate());
        transactionResponseDto.setCreatedDate(transactionEntity.getCreatedDate());
        return transactionResponseDto;
    }

    private List<TransactionResponseDto> mapToDtoList(List<TransactionEntity> transactionEntities) {
        return transactionEntities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
