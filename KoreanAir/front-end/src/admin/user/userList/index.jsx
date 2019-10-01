import React from 'react';
import { List, Datagrid, TextField, EmailField, UrlField, ReferenceField } from 'react-admin';

export const UserList = props => (
    <List {...props}>
        <Datagrid rowClick="edit">
            <TextField source="id" />
            {/* <TextField source="user_id" />
            <TextField source="username" />
            <TextField source="user_role" />
            <TextField source="created_date" />
            <TextField source="modified_date" /> */}
            {/* <EmailField source="email" />
            <TextField source="address.street" />
            <TextField source="phone" />
            <UrlField source="website" />
            <TextField source="company.name" /> */}
        </Datagrid>
    </List>
);