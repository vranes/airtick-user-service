package com.raf.airtickuserservice.mapper;

import com.raf.airtickuserservice.domain.CreditCard;
import com.raf.airtickuserservice.dto.CreditCardCreateDto;
import com.raf.airtickuserservice.dto.CreditCardDto;
import com.raf.airtickuserservice.exception.NotFoundException;
import com.raf.airtickuserservice.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {

    private UserRepository userRepository;

    public CardMapper (UserRepository userRepository) { this.userRepository = userRepository; }

    public CreditCardDto CreditCardToCreditCardDto(CreditCard creditCard){
        CreditCardDto creditCardDto = new CreditCardDto();
        creditCardDto.setFirstName(creditCard.getFirstName());
        creditCardDto.setLastName(creditCard.getLastName());
        creditCardDto.setId(creditCard.getId());
        return creditCardDto;
    }

    public CreditCard CreditCardCreateDtoToCreditCard(CreditCardCreateDto creditCardCreateDto) {
        CreditCard creditCard = new CreditCard();
        creditCard.setFirstName(creditCardCreateDto.getFirstName());
        creditCard.setLastName(creditCardCreateDto.getLastName());
        creditCard.setId(creditCardCreateDto.getId());
        creditCard.setPin(creditCardCreateDto.getPin());
        creditCard.setUser(userRepository.findById(creditCardCreateDto.getUserId()).orElseThrow(() -> new NotFoundException(String.format("Product with id: %d not found.", creditCardCreateDto.getUserId()))));

        return creditCard;
    }
}
