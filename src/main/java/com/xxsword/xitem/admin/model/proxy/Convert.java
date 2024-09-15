package com.xxsword.xitem.admin.model.proxy;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Convert {
    Convert INSTANCE = Mappers.getMapper(Convert.class);

    NetLinkModel toPaperVO(JSONDBCommNetLink jsondbCommNetLink);

}
