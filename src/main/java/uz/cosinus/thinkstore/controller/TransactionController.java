package uz.cosinus.thinkstore.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.cosinus.thinkstore.dto.createDto.TransactionCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.TransactionResponseDto;
import uz.cosinus.thinkstore.entity.TransactionEntity;
import uz.cosinus.thinkstore.service.transactionService.TransactionService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Tag(name = "Transaction")
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public ResponseEntity<TransactionResponseDto> createTransaction(@RequestBody TransactionCreateDto transactionCreateDto) {
        return new ResponseEntity<>(transactionService.createTransaction(transactionCreateDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<TransactionResponseDto>> getAllTransactions(
            @RequestParam(value = "page", defaultValue = "0")
            int page,
            @RequestParam(value = "size", defaultValue = "5")
            int size
    ) {
        List<TransactionResponseDto> transactions = transactionService.getAllTransactions(page, size);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResponseDto> getTransactionById(@PathVariable UUID transactionId) {
        TransactionResponseDto transaction = transactionService.getTransactionById(transactionId);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
}
