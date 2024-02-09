package uz.cosinus.thinkstore.service.transactionService;

import uz.cosinus.thinkstore.dto.createDto.TransactionCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.TransactionResponseDto;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    TransactionResponseDto createTransaction(TransactionCreateDto transactionCreateDto);
    List<TransactionResponseDto> getAllTransactions(int page, int size);
    TransactionResponseDto getTransactionById(UUID transactionId);

    List<TransactionResponseDto> transactionsOfUser(UUID uuid);
}
