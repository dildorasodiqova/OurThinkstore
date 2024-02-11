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
import uz.cosinus.thinkstore.enums.TransactionStatus;
import uz.cosinus.thinkstore.service.transactionService.TransactionService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Tag(name = "Transaction")
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;



    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<TransactionResponseDto>> getAllTransactions(
            @RequestParam(value = "page", defaultValue = "0")
            int page,
            @RequestParam(value = "size", defaultValue = "5")
            int size
    ) {
        List<TransactionResponseDto> transactions = transactionService.getAll(page, size);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getById/{transactionId}")
    public ResponseEntity<TransactionResponseDto> getById(@PathVariable UUID transactionId) {
        return new ResponseEntity<>(transactionService.getById(transactionId), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/transactionsOfUser")
    public ResponseEntity<List<TransactionResponseDto>> transactionsOfUser(Principal principal){
        return ResponseEntity.ok(transactionService.transactionsOfUser(UUID.fromString(principal.getName())));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/cancel/{transactionId}")
    public ResponseEntity<TransactionResponseDto> cancel(@PathVariable UUID transactionId) {
        return new ResponseEntity<>(transactionService.cancelTransaction(transactionId), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PutMapping("/updateStatus/{transactionId}")
    public ResponseEntity<TransactionResponseDto> updateStatus(@PathVariable UUID transactionId, TransactionStatus status){
        return ResponseEntity.ok(transactionService.updateStatus(transactionId, status));
    }




}
