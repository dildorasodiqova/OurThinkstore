package uz.cosinus.thinkstore.service.transactionService;

import uz.cosinus.thinkstore.dto.createDto.TransactionCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.TransactionResponseDto;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    TransactionResponseDto transaction(TransactionCreateDto transactionCreateDto);
    List<TransactionResponseDto> getAll(int page, int size);
    TransactionResponseDto getById(UUID transactionId);

    List<TransactionResponseDto> transactionsOfUser(UUID uuid);
}
