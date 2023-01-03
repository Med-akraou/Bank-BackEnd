package med.sig.bank.mappers;


import med.sig.bank.dtos.SavingAccountDTO;
import med.sig.bank.entities.SavingAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",imports = {CustomerMapper.class})
public interface AccountMapper {
    @Mapping(source = "customer",target = "customerDTO")
    SavingAccountDTO toSavingAccountDTO(SavingAccount savingAccount);
}
