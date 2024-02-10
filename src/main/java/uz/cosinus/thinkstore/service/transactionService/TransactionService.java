package uz.cosinus.thinkstore.service.transactionService;

import uz.cosinus.thinkstore.dto.createDto.TransactionCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.TransactionResponseDto;
import uz.cosinus.thinkstore.enums.TransactionStatus;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    TransactionResponseDto transaction(TransactionCreateDto transactionCreateDto);
    List<TransactionResponseDto> getAll(int page, int size);
    TransactionResponseDto getById(UUID transactionId);

    List<TransactionResponseDto> transactionsOfUser(UUID uuid);


    TransactionResponseDto updateStatus(UUID transactionId, TransactionStatus status);

    TransactionResponseDto cancelTransaction(UUID transactionId);

}
