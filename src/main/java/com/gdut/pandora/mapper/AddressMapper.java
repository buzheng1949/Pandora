package com.gdut.pandora.mapper;

import com.gdut.pandora.domain.Address;
import com.gdut.pandora.domain.query.AddressQuery;
import com.gdut.pandora.domain.result.AddressDTO;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
@Mapper
public interface AddressMapper {
    int delete(Integer id);

    int insert(Address record);

    List<Address> list(AddressQuery addressQuery);

    int update(AddressQuery addressQuery);
}