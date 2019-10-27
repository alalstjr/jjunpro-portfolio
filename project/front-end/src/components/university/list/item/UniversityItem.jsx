import React from "react"

import { 
    Item,
    ItemUniName,
    ItemUniCount
 } from "../../style"

const UniversityItem = ({uniName, uniPujjig, categorySearch}) => (
    <Item onClick = {categorySearch}>
        <ItemUniName>{uniName}</ItemUniName>
        <ItemUniCount>{uniPujjig}명 푹찍</ItemUniCount>
    </Item> 
);

export default UniversityItem;