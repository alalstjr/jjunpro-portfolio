import React from 'react';
import { Filter, ReferenceInput, SelectInput, TextInput } from 'react-admin';

export const AccountFilter = (props) => (
    <Filter {...props}>
        <TextInput label="Search" source="q" alwaysOn />
        <ReferenceInput label="User" source="userId" reference="users" allowEmpty>
            <SelectInput optionText="name" />
        </ReferenceInput>
    </Filter>
);