import React from "react"

import { 
    Item,
    ItemUniName,
    ItemUniCount
 } from "../../style"

const UniversityItem = ({uniName, uniPujjig, categorySearch}) => (
    <Item onClick={categorySearch}>
        <ItemUniName>{uniName}</ItemUniName>
        <ItemUniCount>리뷰 {uniPujjig}개</ItemUniCount>
    </Item> 
);

export default UniversityItem;