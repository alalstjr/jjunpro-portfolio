import React from 'react';

import { Admin, Resource } from 'react-admin';
import { UserList } from './user/userList';
import { PostList, PostEdit, PostCreate } from './post';
import Dashboard from './dashboard';
import authProvider from './authProvider';

import jsonServerProvider from 'ra-data-json-server';
const dataProvider = jsonServerProvider('http://jsonplaceholder.typicode.com');

export const AdminComponent = () => (
    <Admin dashboard={Dashboard} authProvider={authProvider} dataProvider={dataProvider}>
        <Resource name="posts" list={PostList} edit={PostEdit} create={PostCreate} />
        <Resource name="users" list={UserList} />
    </Admin>
);